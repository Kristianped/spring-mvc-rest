package no.kristianped.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        Contact contact = new Contact("Kristian Pedersen", "https://github.com/Kristianped/", "kristianped@gmail.com");

        return new ApiInfo(
                "Spring Framework REST testing with Swagger",
                "Spring Framework testing",
                "1.0",
                "Terms of service",
                contact,
                "MIT License",
                "https://opensource.org/licenses/MIT",
                new ArrayList<>()
        );
    }
}
