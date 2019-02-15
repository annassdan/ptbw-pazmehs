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
public class LandingCatchDetail3rdPartyDTO extends EntityModel<LandingCatchDetail3rdPartyDTO, String> {

    @ApiModelProperty("Spesies")
    private String namaSpesies;

    @ApiModelProperty("Tangkapan Volume (Kg)")
    private double tangkapanVolume;

    @ApiModelProperty("Tangkapan Individu (Ekor)")
    private double tangkapanIndividu;

}
