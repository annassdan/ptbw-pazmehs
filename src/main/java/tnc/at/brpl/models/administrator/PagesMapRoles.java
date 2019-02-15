package tnc.at.brpl.models.administrator;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.SysUserRoleType;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.PAGES_MAP_ROLES)
public class PagesMapRoles extends EntityModel<PagesMapRoles, String> {

    @ApiModelProperty("Hak Akses yang harus digunakan untuk memasuki halaman ini")
    @Column(name = "tipe_akses_ke_halaman" + XMARK)
    private String tipeAksesKeHalaman;

    @ApiModelProperty("ID Unik dari Hak Akses")
    @Column(name = "hak_akses_id_unik" + XMARK)
    private String tipeAksesIdUnik;

}
