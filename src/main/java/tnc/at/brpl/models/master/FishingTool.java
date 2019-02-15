package tnc.at.brpl.models.master;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE  + Brpl.MASTER + Brpl.CONTENT.FISHING_TOOL)
public class FishingTool extends EntityModel<FishingTool, String> implements Brpl {

    @ApiModelProperty("Nama Alat tangkap dalam bahasa Indonesia")
    @Column(name = "nama_alat_tangkap_id" + XMARK)
    private String namaAlatTangkapID;


    @ApiModelProperty("Nama Alat tangkap dalam bahasa Inggris")
    @Column(name = "nama_alat_tangkap_en" + XMARK)
    private String  namaAlatTangkapEN;


    @ApiModelProperty("Deskripsi Alat tangkap")
    @Column(name = "deskripsi_alat_tangkap" + XMARK)
    private String deskripsiAlatTangkap;

}
