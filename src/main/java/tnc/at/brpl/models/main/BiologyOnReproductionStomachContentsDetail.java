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
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = Brpl.UNIQUE + Brpl.CONTENT.BIOLOGY_ON_REPRODUCTION_STOMACH_CONTENTS_DETAIL)
public class BiologyOnReproductionStomachContentsDetail extends EntityModel<BiologyOnReproductionStomachContentsDetail, String>
    implements Brpl {

    @ApiModelProperty("Jenis Isi Perut")
    @Column(name = "jenis_isi_perut" + XMARK)
    private String jenisIsiPerut;

    @ApiModelProperty("Jumlah Isi perut")
    @Column(name = "jumlah_isi_perut" + XMARK)
    private int jumlahIsiPerut;

    @ApiModelProperty("Berat Isi Perut")
    @Column(name = "berat_isi_perut" + XMARK)
    private double beratIsiPerut;

}
