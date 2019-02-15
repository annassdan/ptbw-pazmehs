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
public class BiologyOnReproductionDetail3rdPatyDTO extends EntityModel<BiologyOnReproductionDetail3rdPatyDTO, String> {

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
