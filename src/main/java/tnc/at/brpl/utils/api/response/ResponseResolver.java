package tnc.at.brpl.utils.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tnc.at.brpl.utils.Brpl;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Getter
@Setter
@Builder
@SuppressWarnings("unused")
public class ResponseResolver<T> implements Brpl, ResolverListener<T> {





    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.OK;

    @Builder.Default
    private ResponseType type = ResponseType.WRITE;

    @Builder.Default
    private long timestamp = new Date().getTime();

    @Builder.Default
    private int status = HttpStatus.OK.value();

//    @Builder.Default
//    private String error = RESPONSE.ERROR;

    @Builder.Default
    private Object error = RESPONSE.ERROR;

    @Builder.Default
    private DataErrorType errorType = DataErrorType.NULL;

    @Builder.Default
    private String message = RESPONSE.SUCCESS;

    private T body;

    private String extraParameterResponse;


    /**
     * Merepresentasikan response models dari setiap operasi yang terjadi pada System BRPL
     *
     * @return ResponseResolver
     */
    @Override
    public ResponseEntity<ResponseResolver<?>> map(){
        return generate(this.type);
    }


    /**
     * Merepresentasikan response models dari setiap operasi yang terjadi pada System BRPL
     *
     * @param type Type operasi yang dilakukan pada service untuk menghasilkan sebuah response
     * @return ResponseResolver
     */
    @Override
    public ResponseEntity<ResponseResolver<?>> map(ResponseType type){
        return generate(type);
    }


    @JsonIgnore
    @Override
    public T getData() {
        return null;
    }


    @JsonIgnore
    @Override
    public List<T> getDatas() {
        return null;
    }


    /**
     * Merepresentasikan fungsi yang memodelkan response dari setiap operasi yang terjadi pada System BRPL
     *
     * @param type Type operasi yang dilakukan pada service untuk menghasilkan sebuah response
     * @return ResponseResolver
     */
    private ResponseEntity<ResponseResolver<?>> generate(ResponseType type){
        Logger logger = LoggerFactory.getLogger(this.getClass().getName());
        // .. add default header
        /*
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Access-Control-Allow-Credentials", "true");
        httpHeaders.add("Access-Control-Allow-Methods", "POST");
        httpHeaders.add("Cache-Control", "no-cache");
        //httpHeaders*/

        if (this.status != this.httpStatus.value()){
            this.status = (this.httpStatus != HttpStatus.OK) ? this.httpStatus.value() : this.status;
        }



        try {
            if (type == null){
                throw new Exception("Please Specified Response Type...");
            } else {
                if (this.body != null && type == ResponseType.WRITE) {
                    int y = 0;
                    try {
                        y = ((ResponseModel<?>) body).getContent().size();
                    } catch (Exception s){}
                    if (y > 0){
                        return new ResponseEntity("Please set your Response Type to READ or READ_ONE", HttpStatus.CONFLICT);
//                        throw new Exception("Please set your Response Type to READ or READ_ONE");
                    }
                } else if (this.body != null && type == ResponseType.READ_ONE) {
                    setBody((T) ((ResponseModel<?>) body).getContent().get(0));
                }

                if(this.body == null && type != ResponseType.WRITE){
                    throw new Exception("Please Specified Response Content...");
                }

                return new ResponseEntity<>(this, httpStatus);
            }
        } catch (Exception e){
            return new ResponseEntity<>(this, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    /**
     * Add Response Header
     *
     * @param headerName
     * @param headerValue
     * @return
     */
    @JsonIgnore
    @Override
    public ResponseResolver addHeader(String headerName, String headerValue){
        try{
            //this.httpHeaders.get(headerName);
        } catch (Exception  e){
            //this.httpHeaders.add(headerName, headerValue);
        }
        return this;
    }



    public enum DataErrorType {

        NULL,

        /**
         * Jika errornya terjadi ketika memvalidasi data excel yang diupload
         */
        RAW_ERROR,

        /**
         * Error yang terjadi dari proses sistem bukan dari data yang diupload
         */
        DEFAULT_ERROR
    }

}
