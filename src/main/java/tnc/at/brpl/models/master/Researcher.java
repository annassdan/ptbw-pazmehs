
package tnc.at.brpl.models.master;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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
@Table(name = Brpl.UNIQUE  + Brpl.MASTER + Brpl.CONTENT.RESEARCHER)
public class Researcher  extends EntityModel<Researcher, String>  {

    @ApiModelProperty("Nama Depan")
    @Column(name = "nama_depan" + XMARK)
    private String namaDepan;

    @ApiModelProperty("Nama Belakang")
    @Column(name = "nama_belakang" + XMARK)
    private String namaBelakang;


    @ApiModelProperty("Jabatan")
    @Column(name = "jabatan" + XMARK)
    private String jabatan;

    @ApiModelProperty("Email")
    @Column(name = "email" + XMARK)
    private String email;

    @ApiModelProperty("Kode Sumber Daya / Kelti")
    @Column(name = "uuid_sumber_daya" + XMARK)
    private String uuidSumberDaya;

    @ApiModelProperty("Status Aktif sebagai Peneliti")
    @Column(name = "status" + XMARK)
    private boolean status;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;



}
