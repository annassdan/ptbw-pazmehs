package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class BiologyOnSizeSampleDetail3rdPartyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("Nama Spesies")
    private String namaSpesies;

    @ApiModelProperty("Sample Volume (Kg)")
    private double sampleVolume;

    @ApiModelProperty("Sample Individu (Ekor)")
    private double sampleIndividu;

    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
    private String tipePanjang;

}
