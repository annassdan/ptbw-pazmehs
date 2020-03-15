package tnc.at.brpl.models.administrator;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.DataGroup;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;

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
@SuperBuilder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.SYSUSER_GROUP)
//@Embeddable
@SuppressWarnings("unused")
public class SysUserGroup extends EntityModel<SysUserGroup, String> implements Brpl {




    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Grup")
    @Column(name = "grup" + XMARK)
    private DataGroup grup;

    @ApiModelProperty("Pemilik Data")   
    @Column(name = "organisasi" + XMARK)
    private String organisasi;

    @ApiModelProperty("Deskripsi Pemilik Data")
    @Column(name = "deskripsi_organisasi" + XMARK)
    private String deskripsiOrganisasi;

}
