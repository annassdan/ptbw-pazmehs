

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
@Table(name = Brpl.UNIQUE  + Brpl.MASTER + Brpl.CONTENT.RESOURCE)
public class Resource extends EntityModel<Resource, String> {

    @ApiModelProperty("Nama Sumber Daya")
    @Column(name = "nama_sumber_daya" + XMARK)
    private String namaSumberDaya;

    @ApiModelProperty("Deskripsi tentang Sumber Daya")
    @Column(name = "deskripsi_sumber_daya" + XMARK)
    private String deskripsiSumberDaya;


}
