package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class OperationalCatchDetails3rdPartyDTO extends EntityModel<OperationalCatchDetails3rdPartyDTO, String> {



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
