

package tnc.at.brpl.utils.entity;


import tnc.at.brpl.utils.Brpl;

import java.util.Date;

/**
 * Merepresentasikan Primary Key yang akan digunakan oleh sebuah Tabel
 *
 * @param <ID> ipe Data dari Primary Key yang ada di Tabel dalam Database
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public interface EntityListener<ID> extends Brpl {

    String getUuid();
    void setUuid(String uuid);

    Date getDibuatPadaTanggal();
    void setDibuatPadaTanggal(Date date);


    Date getTerakhirDiubahPadaTanggal();
    void setTerakhirDiubahPadaTanggal(Date date);


    String getDibuatAtauTerakhirDiubahOleh();
    void setDibuatAtauTerakhirDiubahOleh(String oleh);

}
