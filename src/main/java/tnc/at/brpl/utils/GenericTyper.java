package tnc.at.brpl.utils;

import lombok.Builder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Builder
@SuppressWarnings("unused")
public class GenericTyper<T> {



    /**
     * Merepresentasikan nama class dari generic type yang ada pada sebuah Class
     *
     * @param aClass Superclass
     * @return String
     */
    public String getTypeClassName(Class<?> aClass){
        return generateTypeClassName(aClass, 0);
    }


    /**
     * Merepresentasikan nama class dari generic type yang ada pada sebuah Class
     *
     * @param aClass Superclass
     * @param index Index urutan Type
     * @return String
     */
    public String getTypeClassName(Class<?> aClass, int index){
        return generateTypeClassName(aClass, index);
    }


    /**
     * Mengambil nama class dari generic type yang ada pada sebuah Class
     *
     * @param aClass Superclass
     * @param index Index ururtan Type
     * @return String
     */
    private String generateTypeClassName(Class<?> aClass, int index){
        Type clazz = aClass.getClass().getGenericSuperclass();
        Type className = ((ParameterizedType) clazz).getActualTypeArguments()[index];
        return className.getTypeName();
    }




    /**
     * Mendapatkan instance dari Genric Type Class
     *
     * @param clas superclass
     * @param index urutan Generic type pada superclass
     * @return T (new instance)
     */
    @SuppressWarnings("unchecked")
    public T getTypeInstance(Class<?> clas, int index) {
        try {
            return (T) ((Class) ((ParameterizedType) clas.getGenericSuperclass()).getActualTypeArguments()[index]).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
