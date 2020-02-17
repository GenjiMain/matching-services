package fr.jobslake.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "matching Application API",
                "matching services",
                "V1",
                "urn:tos",
                new Contact("matching service", "https://inventiv-it.fr", "admin@inventiv-it.fr"),
                "inventiv-it 3.0",
                "https://inventiv-it.fr/licenses/by-sa/3.0/",
                Collections.emptyList()
        );
    }
}
