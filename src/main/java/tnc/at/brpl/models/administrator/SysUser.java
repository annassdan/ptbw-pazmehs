package tnc.at.brpl.models.administrator;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Builder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.SYSUSER)
public class SysUser extends EntityModel<SysUser, String> implements Brpl {

    @ApiModelProperty("Kode referensi dari uuid Enumerator atau Peneliti")
    @Column(name = "uuid_referensi" + XMARK)
    private String uuidReferensi;

    @ApiModelProperty("Nama Awal")
    @Column(name = "nama_depan" + XMARK)
    private String namaDepan;

    @ApiModelProperty("Nama Akhir")
    @Column(name = "nama_belakang" + XMARK)
    private String namaBelakang;

    @ApiModelProperty("Login ID yang digunakan (username)")
    @Column(name = "pengguna" + XMARK, unique = true)
    private String pengguna;

    @ApiModelProperty("Password yang digunakan")
    @Column(name = "katasandi" + XMARK)
    private String katasandi;


    @ApiModelProperty("Organisasi/ Proyek")
    @Column(name = "organisasi" + XMARK)
    private String organisasi;

    @ApiModelProperty("Sumber Daya")
    @Column(name = "sumber_daya" + XMARK)
    private String sumberDaya;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;


    @ApiModelProperty("WPP")
    @Column(name = "email" + XMARK)
    @ColumnDefault("''")
    private String email;

    @ApiModelProperty("WPP")
    @Column(name = "hp" + XMARK)
    @ColumnDefault("''")
    private String hp;


    @ApiModelProperty("Status Aktif User")
    @Column(name = "aktif" + XMARK)
    @ColumnDefault("'t'")
    private boolean isActive;



    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Hak Akses yang dimiliki oleh pengguna")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_sysuser" + XMARK)
    private List<Roles> dataHakAkses = new ArrayList<>();



    @ApiModelProperty("Pengaturan Hak Akses yang dimiliki oleh pengguna ke akses Halaman")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_sysuser" + XMARK)
    private List<Accesses> dataAksesKeHalaman = new ArrayList<>();

}
