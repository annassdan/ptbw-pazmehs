package tnc.at.brpl.utils.mainconfig.models;

import lombok.*;
/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Data
@AllArgsConstructor
@Builder
public class CellModel {

    /**
     * row cell keberapa
     */
    @Builder.Default
    int row = 0;


    /**
     * column cell keberapa
     */
    @Builder.Default
    int column = 0;

}
