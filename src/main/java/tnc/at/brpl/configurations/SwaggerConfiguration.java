package tnc.at.brpl.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
@EnableSwagger2
@SuppressWarnings("unused")
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Balai Riset Penelitian Laut System",
                "Sebagai Services dari proses Sampling data yang ada di Balai Riset Penelitian Perikanan Laut",
                "1.0",
                "Syarat & Ketentuan Berlaku",
                new Contact("Balai Riset Perikanan Laut", "http://bppl.kkp.go.id/", "-"),
                "Apache License 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                null
        );
    }

}
