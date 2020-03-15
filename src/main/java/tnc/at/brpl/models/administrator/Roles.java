package tnc.at.brpl.models.administrator;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.ROLES)
public class Roles extends EntityModel<Roles, String> {

    @ApiModelProperty("Tipe Hak Akses")
    @Column(name = "tipe_hak_akses" + XMARK)
    private String tipeHakAkses;
//
    @ApiModelProperty("ID Unik dari Hak Akses")
    @Column(name = "hak_akses_id_unik" + XMARK)
    private String hakAksesIdUnik;
//
    @ApiModelProperty("Identifikasi Hak Akses")
    @Column(name = "hak_akses" + XMARK)
    private String hakAkses;

//
//    @ApiModelProperty("Grup")
//    @Column(name = "grup" + XMARK)
//    private String grup;

}
