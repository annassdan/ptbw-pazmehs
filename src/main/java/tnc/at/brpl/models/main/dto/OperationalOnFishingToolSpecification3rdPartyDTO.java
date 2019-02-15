package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class OperationalOnFishingToolSpecification3rdPartyDTO extends EntityModel<OperationalOnFishingToolSpecification3rdPartyDTO, String> {

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String namaAlatTangkap;

    @ApiModelProperty("Nama Spesifikasi ")
    @Column(name = "spesifikasi" + XMARK)
    private String spesifikasi;

    @ApiModelProperty("Nilai Spesifikasi ")
    @Column(name = "nilai_spesifikasi" + XMARK)
    private String nilaiSpesifikasi;

    @ApiModelProperty("Satuan Spesifikasi ")
    @Column(name = "satuan_spesifikasi" + XMARK)
    @ColumnDefault("''")
    private String satuanSpesifikasi;

}
