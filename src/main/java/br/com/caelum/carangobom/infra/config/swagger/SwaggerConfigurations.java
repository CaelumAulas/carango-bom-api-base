package br.com.caelum.carangobom.infra.config.swagger;

import br.com.caelum.carangobom.infra.jpa.entity.UserJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.caelum.carangobom"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(UserJpa.class)
                .globalOperationParameters(
                    Collections.singletonList(
                        new ParameterBuilder()
                            .name("Authorization")
                            .description("Authorization Header")
                            .modelRef(new ModelRef("string"))
                            .parameterType("header")
                            .required(false)
                            .build()
                    )
                );
    }
}
