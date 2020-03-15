
package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.OPERATIONAL_ON_FISHING_TOOL_SPEC)
public class OperationalOnFishingToolSpecification extends EntityModel<OperationalOnFishingToolSpecification, String> {

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

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
