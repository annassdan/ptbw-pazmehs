package tnc.at.brpl.utils.api.response;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public enum ResponseType {

    /**
     * Type response bila melakukan proses pengambilan data dari tabel di database
     */
    READ,

    /**
     * Type response bila melakukan proses save, update ataupun delete ke tabel di database
     */
    WRITE,


    /**
     * Type response bila melakukan proses pengambilan satu buah data  dari tabel di database
     */
    READ_ONE,


    OTHER
}
