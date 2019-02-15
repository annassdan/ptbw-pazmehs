package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.LengthType;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;

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
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.BIOLOGY_ON_SIZE_DETAIL)
public class BiologyOnSizeDetail extends EntityModel<BiologyOnSizeDetail, String>
    implements Brpl {

    @ApiModelProperty("Kode Spesies")
    @Column(name = "uuid_spesies" + XMARK)
    private String uuidSpesies;

    @ApiModelProperty("Ukuran Panjang")
    @Column(name = "panjang" + XMARK)
    private double panjang;

//    @Enumerated(EnumType.STRING)
//    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
//    @Column(name = "tipe_panjang" + XMARK)
//    private String tipePanjang;

}
