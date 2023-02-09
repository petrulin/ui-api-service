package com.otus.uiapiservice.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
/*
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/ui-api-service/api/v1/**").hasRole("USER")
               // .requestMatchers("/ui-api-service/api/v1/**").authenticated()
               // .requestMatchers("/ui-api-service/api/v1/**").permitAll()
                .requestMatchers("/actuator/**").permitAll();
        return http.build();
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter()
            throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("publickey.txt");
        String publicKey = IOUtils.toString(resource.getInputStream());
        converter.setVerifierKey(publicKey);

        return converter;
    }

}*/

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/ui-api-service/api/v1/external/**").authenticated()
                .antMatchers("/ui-api-service/api/v1/auth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
        throws Exception {
        resources.resourceId("MAIN_SERVER");
    }

/*    @Bean
    public JwtAccessTokenConverter accessTokenConverter()
        throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("publickey.txt");
        String publicKey = IOUtils.toString(resource.getInputStream());
        converter.setVerifierKey(publicKey);

        return converter;
    }*/
}

