
package tnc.at.brpl.models.main;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;

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
@Builder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.CATCH_DETAIL)
public class OperationalCatchDetails extends EntityModel<OperationalCatchDetails, String> {

/*    *//**
     * Data Operasional
     *//*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dataOperasionalDetailTangkapan")
    //@JoinColumn(name = "operational_id" + XMARK)
    @JsonIgnore
    private Operational dataOperasional;*/

    @ApiModelProperty("Kode Spesies")
    @Column(name = "uuid_spesies" + XMARK)
    private String uuidSpesies;


    @ApiModelProperty("Kode FAO Spesies")
    @Column(name = "kode_fao" + XMARK)
    private String kodeFao;


    @ApiModelProperty("Total Berat (Kg)")
    @Column(name = "total_berat_kg" + XMARK)
    private double totalBeratKg;

    @ApiModelProperty("Total Jumlah (Ekor)")
    @Column(name = "total_berat_ekor" + XMARK)
    private double totalBeratEkor;

    @ApiModelProperty("Dalam Kondisi Segar")
    @Column(name = "segar" + XMARK)
    private boolean segar;

    @ApiModelProperty("Dalam Kodisi Beku")
    @Column(name = "beku" + XMARK)
    private boolean beku;

    @ApiModelProperty("Dalam kondisi terasinkan")
    @Column(name = "asin" + XMARK)
    private boolean asin;


    @ApiModelProperty("Dalam Kodisi Loin")
    @Column(name = "loin" + XMARK)
    private boolean loin;

    @ApiModelProperty("Dalam kondisi rebus")
    @Column(name = "rebus" + XMARK)
    private boolean rebus;


    /*@ApiModelProperty("Tampa Penanganan")
    @Column(name = "tanpa_penanganan" + XMARK)
    private boolean tanpaPenanganan;*/


}
