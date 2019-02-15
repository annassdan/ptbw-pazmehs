package tnc.at.brpl.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tnc.at.brpl.utils.Brpl;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
//@Configuration
//@EnableWebMvc
@SuppressWarnings("unused")
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        super.addCorsMappings(registry);
        registry.addMapping("/oauth/token")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.POST.toString(),
                        HttpMethod.GET.toString(),
                        HttpMethod.PUT.toString(),
                        HttpMethod.DELETE.toString(),
                        HttpMethod.OPTIONS.toString(),
                        HttpMethod.HEAD.toString(),
                        HttpMethod.TRACE.toString(),
                        HttpMethod.PATCH.toString())
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true);


        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.POST.toString(),
                        HttpMethod.GET.toString(),
                        HttpMethod.PUT.toString(),
                        HttpMethod.DELETE.toString(),
                        HttpMethod.OPTIONS.toString(),
                        HttpMethod.HEAD.toString(),
                        HttpMethod.TRACE.toString(),
                        HttpMethod.PATCH.toString())
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true);
    }
}
