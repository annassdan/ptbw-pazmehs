package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class OperationalOnFishingToolSpecification3rdPartyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;

//    @ApiModelProperty("Kode Alat Tangkap")
//    @Column(name = "uuid_alat_tangkap" + XMARK)
//    private String namaAlatTangkap;

    @ApiModelProperty("Nama Spesifikasi ")
    private String spesifikasi;

    @ApiModelProperty("Nilai Spesifikasi ")
    private String nilaiSpesifikasi;

    @ApiModelProperty("Satuan Spesifikasi ")
    private String satuanSpesifikasi;

}
