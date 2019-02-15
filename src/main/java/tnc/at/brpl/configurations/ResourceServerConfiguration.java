package tnc.at.brpl.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
@EnableResourceServer
@SuppressWarnings("unused")
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        logger.info("GENERATE TOKEN.....PROCESS 3");
        http.authorizeRequests()
                .antMatchers(
                        "/api/master/sumberdaya/",
                        "/api/master/sumberdaya/",
                        "/api/master/pegawai/",
                        "/api/master/alattangkap/",
                        "/api/master/enumerator/",
                        "/api/master/daerahpenangkapan/",
                        "/api/administrator/**",
                        "/api/sampling/**",
                        "/api/datamentah/**").permitAll()
                .antMatchers("/testlink", "/api/integrasi/**", "/api/master/spesies/"
                        ).authenticated();
    }


    @Bean
    public TokenStore tokenStore() {
//        logger.info("GENERATE TOKEN.....2");

        return new InMemoryTokenStore();

    }
}
