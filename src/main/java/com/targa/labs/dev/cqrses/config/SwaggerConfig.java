package com.targa.labs.dev.cqrses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.targa.labs.dev.cqrses"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {

        Contact contact = new Contact();
        contact.email("lnibrass@gmail.com");
        contact.name("Nebrass Lamouchi");
        contact.url("https://blog.nebrass.fr");

        return new ApiInfo(
                "CQRS & ES Sample App based on Spring Boot and Axon",
                "App to demonstrate CQRS & ES based on Spring Boot and Axon",
                "0.0.1-SNAPSHOT",
                "Terms of Service",
                contact.toString(),
                "",
                "");
    }
}