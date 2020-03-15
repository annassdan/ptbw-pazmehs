package tnc.at.brpl.models.administrator;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.PAGES_MAP)
@SuppressWarnings("unused")
public class PagesMap extends EntityModel<PagesMap, String> {

    @ApiModelProperty("Nama Halaman")
    @Column(name = "nama_halaman" + XMARK)
    private String namaHalaman;

    @ApiModelProperty("Link dari halaman yang dideklarasikan")
    @Column(name = "url_halaman" + XMARK)
    private String urlHalaman;

    @ApiModelProperty("Deskripsi atau fungsi halaman")
    @Column(name = "deskripsi_halaman" + XMARK)
    private String deskripsiHalaman;

    /** Nanti dihilangkan **/
//    @ApiModelProperty("Hak Akses yang harus digunakan untuk memasuki halaman ini")
//    @Column(name = "akses_ke_halaman" + XMARK)
//    private SysUserRoleType aksesKeHalaman;


//    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Hak Akses yang bisa digunakan untuk mengakses halaman ini")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pages_map" + XMARK)
    @Builder.Default
    private List<PagesMapRoles> dataHakAkses = new ArrayList<>();


    @ApiModelProperty("Ketersediaan jenis Pembatasan pada fungsi hak akses")
    @OneToOne(cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pages_map" + XMARK)
//    private List<PagesMapAccess> dataKetersediaanPembatasan = new ArrayList<>();
    private PagesMapAccess dataKetersediaanPembatasan; // = new ArrayList<>();

}
