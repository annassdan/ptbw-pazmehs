package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@SuppressWarnings("unused")
public class BiologyOnSizeDetail3rdPartyDTO extends Main3rdPartyDTO implements Brpl {

//    @Id
//    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
//    @GeneratedValue(generator = "brpl_id")
//    @ApiModelProperty("ID")
//    private String id;

    @ApiModelProperty("Spesies")
    private String namaSpesies;

    @ApiModelProperty("Ukuran Panjang")
    private double panjang;

}
