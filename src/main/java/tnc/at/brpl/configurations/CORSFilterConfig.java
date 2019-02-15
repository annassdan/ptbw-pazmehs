package tnc.at.brpl.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import tnc.at.brpl.utils.Brpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("unused")
public class CORSFilterConfig implements Filter, Brpl {

//    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Response Headers Settings [placed Here............]
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", CORS.ALL_ORIGINS);
        response.setHeader("Access-Control-Allow-Credentials", "true");

//        && Brpl.CORS.ORIGIN_3.equals(request.getHeader("Origin"))
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod()) ) {
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers",
                    "Authorization, Content-Type, Accept, Cache-Control, X-Requested-With");
            response.setHeader("Access-Control-Max-Age", String.valueOf(CORS.MAX_AGE_3));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    /**
     *
     */
    @Override
    public void destroy() {
    }
}
