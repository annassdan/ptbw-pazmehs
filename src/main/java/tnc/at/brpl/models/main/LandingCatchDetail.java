package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.LANDING_CATCH_DETAILS)
public class LandingCatchDetail extends EntityModel<LandingCatchDetail, String> {

    @ApiModelProperty("Kode Spesies")
    @Column(name = "uuid_spesies" + XMARK)
    private String uuidSpesies;

    @ApiModelProperty("Tangkapan Volume (Kg)")
    @Column(name = "tangkapanVolume" + XMARK)
    private double tangkapanVolume;

    @ApiModelProperty("Tangkapan Individu (Ekor)")
    @Column(name = "tangkapanIndividu" + XMARK)
    private double tangkapanIndividu;


}
