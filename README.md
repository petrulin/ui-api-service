**Github:**
https://github.com/petrulin/auth-service
https://github.com/petrulin/ui-api-service
https://github.com/petrulin/order-service
https://github.com/petrulin/store-service
https://github.com/petrulin/delivery-service
https://github.com/petrulin/pay-service
https://github.com/petrulin/billing-service
https://github.com/petrulin/notification-service

**Схема взаимодействия микросервисов:**
https://raw.githubusercontent.com/petrulin/ui-api-service/master/postman/Microservice%20scheme.png

**Postman collection:**
https://github.com/petrulin/ui-api-service/blob/master/postman/OTUS.postman_collection.json

**Установка БД:**
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install my-postgresql -f auth-service/manifest/values.yaml bitnami/postgresql --version 11.9.13

**Установка rabbitmq:**
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install rabbit bitnami/rabbitmq

**Auth-Service:**

Secret и ConfigMap (auth-service):
kubectl apply -f manifest/secret.yaml
kubectl apply -f manifest/configmap.yaml

Миграция через job:
kubectl create configmap auth-sql --from-file=auth-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install auth-service dev/ --values dev/values.yaml

**UI-Api-Service:**

Установка сервиса:
helm install ui-api-service dev/ --values dev/values.yaml

**Order-service**
Установка rabbitmq:
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install rabbit bitnami/rabbitmq

Миграция через job:
kubectl create configmap order-sql --from-file=order-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install order-service dev/ --values dev/values.yaml

**Store-service:**
Миграция через job:
kubectl create configmap store-sql --from-file=store-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install store-service dev/ --values dev/values.yaml

**Delivery-service:**
Миграция через job:
kubectl create configmap delivery-sql --from-file=delivery-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install delivery-service dev/ --values dev/values.yaml

**Pay-service:**
Миграция через job:
kubectl create configmap pay-sql --from-file=pay-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install pay-service dev/ --values dev/values.yaml

**Billing-service:**
Миграция через job:
kubectl create configmap billing-sql --from-file=billing-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install billing-service dev/ --values dev/values.yaml

**Notification-service:**
Миграция через job:
kubectl create configmap notification-sql --from-file=notification-sql=manifest/schema.sql
kubectl apply -f manifest/job.yaml

Установка сервиса:
helm install notification-service dev/ --values dev/values.yaml


**REST методы для UI:**

arch.homework/ui-api-service/api/v1/auth/registration (UI-Api-Service) регистрирует клиента и создаёт аккаунт в billing-service;
arch.homework/ui-api-service/api/v1/auth/token - получение токена
arch.homework/ui-api-service/api/v1/external/user/edit - редактирование личных данных клиента
arch.homework/ui-api-service/api/v1/external/user/find - поиск клиента

arch.homework/ui-api-service/api/v1/external/user/balance/add добавляет деньги, arch.homework/ui-api-service/api/v1/external/user/balance/withdraw - списывает;
arch.homework/ui-api-service/api/v1/external/user/balance/get - просмотр баланса;
arch.homework/ui-api-service/api/v1/external/user/notification - просмотре нотификаций для конкретного пользователя.
**Создание заказа (метод arch.homework/ui-api-service/api/v1/external/order):**
1. UI-Api-Service посылает сообщение newOrder;
2. Order-Service создаёт новый заказ в статусе "Created";
3. Order-Service посылает sale в очередь pay.queue;
4. Pay-Service посылает withdraw в billing.queue;
5. Billing-service посылает результат обратно в pay.queue;
6. Если Billing-service смог списать деньги, то Pay-Service создаёт sale транзакцию.  
   Если деньги не списаны, то транзакция не создаётся.
   Посылает сообщение в store.queue (в сообщении содержится статус успешности выполнения);
7. Store-Service резервирует товар и отправляет сообщение в delivery.queue (так же есть статус успешности);
8. Delivery-service резервирует курьера на определённую дату/время и посылает результат в order.queue;
   В случае если успешно отработали все 3 сервиса:
9. Order-service выставляет статус заказа "Completed" и сохраняет в outbox_notification сообщение "Заказ успешно создан" в одной транзакции. (использовал pattern Transactional outbox)
10. Планировщик читает outbox_notification и посылает сообщения в notification.queue
11. Notification-service записывает сообщение в таблицу (в дальнейшем можно реализовать отправку на email или messanger).
    Один заказ - только одно итоговое сообщение, сообщения в зависимости от результатов выполнения сервисов различны.
    Пример нотификаций: https://raw.githubusercontent.com/petrulin/ui-api-service/master/postman/email-table.png
    В случае ошибок в каком-либо из сервисов:
9. Delivery-service отменяет свой результат и посылает сообщение refund в pay.queue и cancelBookingFood в store-queue;
10. Pay-Service создаёт refund транзакцию (только если есть SALE транзакции, если нет то и возврата средств клиенту не будет) и посылает сообщение на refund в Billing-service;
    Пример транзакций: https://raw.githubusercontent.com/petrulin/ui-api-service/master/postman/payment-table.png
11. Billing-service возвращает деньги;
11. Store-Service возвращает зарезервированный товар в store;
12. Order-service выставляет статус заказа "Canceled" и отправляет нотификаю способом описанным выше.