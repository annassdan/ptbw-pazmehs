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
public class BiologyOnSizeDetail3rdPartyDTO extends EntityModel<BiologyOnSizeDetail3rdPartyDTO, String> {

    @ApiModelProperty("Spesies")
    private String namSpesies;

    @ApiModelProperty("Ukuran Panjang")
    private double panjang;

}
