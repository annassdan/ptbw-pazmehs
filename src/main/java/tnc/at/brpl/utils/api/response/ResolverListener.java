package tnc.at.brpl.utils.api.response;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public interface ResolverListener<T> {


    /**
     * Merepresentasikan response models dari setiap operasi yang terjadi pada System BRPL
     *
     * @return ResponseResolver
     */
    ResponseEntity<ResponseResolver<?>> map();

    /**
     * Merepresentasikan response models dari setiap operasi yang terjadi pada System BRPL
     *
     * @param type Type operasi yang dilakukan pada service untuk menghasilkan sebuah response
     * @return ResponseResolver
     */
    ResponseEntity<ResponseResolver<?>> map(ResponseType type);


    /**
     *
     * @return T
     */
    T getData();


    /**
     *
     * @return List
     */
    List<T> getDatas();


    ResponseResolver addHeader(String headerName, String headerValue);

}
