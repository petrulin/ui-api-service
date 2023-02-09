########
# Dockerfile to build ui-api-service container image
#
########
FROM openjdk:17-slim

LABEL maintainer="Petrulin Alexander"

COPY target/ui-api-service-*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app.jar"]
