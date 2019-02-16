package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class OperationalCatchDetails3rdPartyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;


    @ApiModelProperty("Nama Spesies")
    private String namaSpesies;

    @ApiModelProperty("Kode FAO Spesies")
    private String kodeFao;

    @ApiModelProperty("Total Berat (Kg)")
    private int totalBeratKg;

    @ApiModelProperty("Total Jumlah (Ekor)")
    private int totalBeratEkor;

    @ApiModelProperty("Dalam Kondisi Segar")
    private boolean segar;

    @ApiModelProperty("Dalam Kodisi Beku")
    private boolean beku;

    @ApiModelProperty("Dalam kondisi sudah diasinkan")
    private boolean asin;

    @ApiModelProperty("Dalam Kodisi Loin")
    private boolean loin;

    @ApiModelProperty("Dalam kondisi sudah direbus")
    private boolean rebus;


}
