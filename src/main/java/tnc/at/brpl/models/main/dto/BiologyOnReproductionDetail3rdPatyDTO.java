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
public class BiologyOnReproductionDetail3rdPatyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("Ukuran Panjang")
    private double panjang;

    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
    private String tipePanjang;

    @ApiModelProperty("Jenis Kelamin")
//    @ColumnDefault("'UI'")
    private String jenisKelamin;


    @ApiModelProperty("Berat Ikan")
    private double berat;

    @ApiModelProperty("Tingkat Kematangan Gonad")
    private String tkg;

    @ApiModelProperty("Berat Isi Perut")
    private double beratIsiPerut;

}
