package br.com.amparo.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableWebMvc  // Add this annotation to enable WebMvc
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    private final String title = "Amparo Api";
    private final String description = "Backend do projeto Amparo";

    @Bean
    public Docket docketBean() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.amparo.backend"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(buildApiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title(title).description(description).build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
