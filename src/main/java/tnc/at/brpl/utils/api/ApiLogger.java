

package tnc.at.brpl.utils.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Merepresentasikan Logger di wilayah API Controller
 *
 * @param <E> Entitas Class dari Api yang terproses
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public class ApiLogger<E> {

    /** ################ LOGGER CREATER ##### */
    Logger logger;

    private String className;


    protected ApiLogger() {
        Type clazz = getClass().getGenericSuperclass();
        Type className = ((ParameterizedType) clazz).getActualTypeArguments()[0];
        this.className = className.getTypeName();
        this.logger = LoggerFactory.getLogger(this.className);
    }


    /**
     * Mendapatkan nama Entity Class pada API yang sedang diproses
     *
     * @return String
     */
    private String getClassName() {
        return className;
    }

    /**
     * Menngeset Nama Class dari Entity yang sedang diproses pada API
     *
     * @param className Nama class entity
     */
    private void setClassName(String className) {
        this.className = className;
    }



    /**
     * Membuat Logger Model
     *
     * @param message Pesan yang akan ditampilkan pada log aplikasi
     * @return String
     */
    protected String makeLog(String message){
        return  " SERVICE Process in CLASS ### " +
                getClassName() +
                " with message [' " +
                message +
                " ']";
    }


}
