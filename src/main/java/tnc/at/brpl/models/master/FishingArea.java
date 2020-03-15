
package tnc.at.brpl.models.master;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = Brpl.UNIQUE  + Brpl.MASTER + Brpl.CONTENT.FISHING_AREA)
public class FishingArea extends EntityModel<FishingArea, String> {

    @ApiModelProperty("Kode Tempat Penangkapan")
    @Column(name = "kode_daerah_penangkapan" + XMARK)
    private String kodeDaerahPenangkapan;

    @ApiModelProperty("Nama Tempat Penangkapan")
    @Column(name = "nama_daerah_penangkapan" + XMARK)
    private String namaDaerahPenangkapan;


    @ApiModelProperty("Latitude")
    @Column(name = "latitude" + XMARK)
    @ColumnDefault("'0'")
    private double latitude;


    @ApiModelProperty("Longitude")
    @Column(name = "longitude" + XMARK)
    @ColumnDefault("'0'")
    private double longitude;





}
