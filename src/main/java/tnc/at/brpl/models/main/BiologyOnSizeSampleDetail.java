package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.BIOLOGY_ON_SIZE_SAMPLE_DETAIL)
public class BiologyOnSizeSampleDetail extends EntityModel<BiologyOnSizeSampleDetail, String> {


    @ApiModelProperty("Kode Spesies")
    @Column(name = "uuid_spesies" + XMARK)
    private String uuidSpesies;

    @ApiModelProperty("Sample Volume (Kg)")
    @Column(name = "sample_volume" + XMARK)
    private double sampleVolume;

    @ApiModelProperty("Sample Individu (Ekor)")
    @Column(name = "sample_individu" + XMARK)
    private double sampleIndividu;

    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
    @Column(name = "tipe_panjang" + XMARK)
    private String tipePanjang;


}
