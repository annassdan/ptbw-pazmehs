package tnc.at.brpl.utils.mainconfig.models;

import lombok.*;

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
public class FieldLooping {


    /**
     * true jika looping yanag dilakukan adalah looping terhadap baris
     */
    boolean rowThrough;

    /**
     * true jika looping yanag dilakukan adalah looping terhadap kolom
     */
    boolean columnThrough;

    /**
     * Jumlah looping pada baris
     */
    @Builder.Default
    int rowLoop = 0;

    /**
     * Jumlah looping pada kolom
     */
    @Builder.Default
    int columnLoop = 0;

    /**
     * Kenaikan nilai looping baris
     */
    @Builder.Default
    int rowInc = 0;

    /**
     * Kenaikan nilai looping kolom
     */
    @Builder.Default
    int columnInc = 0;


    @Builder.Default
    List<FieldSpace> fieldSpaces = null;

}
