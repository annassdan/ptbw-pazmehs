package tnc.at.brpl.utils.mainconfig.models;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldModel {


    /**
     * Judul dari kolom yang diproses
     */
    String title;

    /**
     * Identifier kolom yang diproses pada data json
     */
    String jsonFieldName;

    /**
     * tipe nilai json yang dihasilkan
     */
    FieldValueType valueType;

    /**
     * true Jika ternyata judul yang diproses adalah sebuah primary key untuk sebuah data relasi,
     * maka sebelumnya digunakan untuk memvalidasi data relasi pada database
     */
    @Builder.Default
    boolean isUuidField = false;


    /**
     * Parameter untuk menggunakan uuid sebagai nilai pada kolom yang dimaksud
     */
    @Builder.Default
    boolean useAsUuid = false;

    /**
     * true jika ternyata nilai pada json yang dicari terdapat langsung pada cell excel,
     * bukan sebagai details dari json tersebut
     */
    @Builder.Default
    boolean hasDirectValue = true;


    /**
     * Koordinat cell nilai dari json identifier yang diproses
     */
    CellModel directValueAt;


    /**
     * true jika direct value nya adalah gabungan dari beberapa cell data
     */
    @Builder.Default
    boolean directValueGroup = false;

    /**
     * penghubung antara cell yang merupakan gruop dari sebuah direct value
     */
    String groupValueConnector;


    /**
     * Daftar cell tempat dimana value berada
     */
    List<CellModel> directGroupValuesAt;


    /**
     * Jika nilainya berupa pilhan-pilihan
     */
    @Builder.Default
    boolean hasChoiceValue = false;


    /**
     * Cell cell keberadaan pilihan nilai
     */
    List<FieldChoice> choiceValuesAt;


    /**
     * true jika field/kolom/json identifier ini tidak boleh kosong
     */
    @Builder.Default
    boolean required = false;


    /**
     * Custom daftar nilai apa saja yang boleh diterima/diproses
     * pada json identifier ini
     */
    @Builder.Default
    List<Object> acceptedDirectValues = null;

    /**
     * Merupakan nilai tambahan pada json yang diproses
     * Contoh
     * Hoby anda :
     * A. Berenang
     * B. Lari
     * C. Tidur
     * D. Lainnya (** inilah ekstra valuenya yang diinput oleh pengguna)
     */
    boolean hasExtraDirectValue;

    /**
     * Koordinat cell untuk nilai ekstra dari json identifier yang diproses
     */
    CellModel extraDirectValueAt;


    /**
     * diisi jika @valueType bernilai "OBJECT", maka details dari field ini harus diisi
     */
    @Builder.Default
    List<FieldModel> objectDetails = null;


    /**
     * diisi jika @valueType bernilai "OBJECT", dan @objectDetails mempunyai
     * perulangan nilai pada tiap cell excel yang berbeda
     */
    @Builder.Default
    FieldLooping objectDetailsLoop = null;


    /**
     * Status apakah title ini akan mengikuti looping  baris atau tidak
     */
    boolean forRowLoop;

    /**
     * Status apakah title ini akan mengikuti looping  kolom atau tidak
     */
    boolean forColumnLoop;


}
