package tnc.at.brpl.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import tnc.at.brpl.utils.Brpl;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

//        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//        .scopes("read", "write", "trust")

@Configuration
@EnableAuthorizationServer
@SuppressWarnings("unused")
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements Brpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private AuthenticationManager authManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        logger.info("GENERATE TOKEN.....PROCESS 1");
        security.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("GENERATE TOKENOAUTH");
//        .authorities("ROLE_SUPERUSER", "ROLE_VALIDATOR", "ROLE_CLIENT", "ROLE_GUEST")
        clients.inMemory().withClient("fayaqun" )
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .authorities("ROLE_ADMIN","ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(CORS.ONE_DAY)
                .secret("satria");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        logger.info("GENERATE TOKEN.....PROCESS 2");
        endpoints.tokenStore(tokenStore()).authenticationManager(authManager);
    }


    @Bean
    public TokenStore tokenStore() {

//        logger.info("GENERATE TOKEN.....1");
        return new InMemoryTokenStore();
    }

}

