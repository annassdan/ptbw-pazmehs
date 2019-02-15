package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.entity.EntityModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class BiologyOnSizeSampleDetail3rdPartyDTO extends EntityModel<BiologyOnSizeSampleDetail3rdPartyDTO, String> {

    @ApiModelProperty("Nama Spesies")
    private String namaSpesies;

    @ApiModelProperty("Sample Volume (Kg)")
    private double sampleVolume;

    @ApiModelProperty("Sample Individu (Ekor)")
    private double sampleIndividu;

    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
    private String tipePanjang;

}
