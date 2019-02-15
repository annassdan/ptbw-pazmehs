
package tnc.at.brpl.models.master;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.Date;

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
@Table(name = Brpl.UNIQUE + Brpl.MASTER + Brpl.CONTENT.ENUMERATOR)
public class Enumerator extends EntityModel<Enumerator, String> implements Brpl {


    @ApiModelProperty("Status Enumerator")
    @Column(name = "status" + XMARK)
    private boolean status;

    @ApiModelProperty("Nama Awal Enumerator")
    @Column(name = "nama_depan_enumerator" + XMARK)
    private String namaDepanEnumerator;

    @ApiModelProperty("Nama Akhir Enumerator")
    @Column(name = "nama_belakang_enumerator" + XMARK)
    private String namaBelakangEnumerator;

    @ApiModelProperty("Kontak Enumerator")
    @Column(name = "kontak_enumerator" + XMARK)
    private String kontakEnumerator;


    @ApiModelProperty("Tanggal Enumerator Terdaftar")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_pengaktifan_enumerator" + XMARK)
    private Date tanggalPengaktifanEnumerator;

    @ApiModelProperty("Email Terdaftar")
    @Column(name = "email_enumerator" + XMARK)
    private String emailEnumerator;

    @ApiModelProperty("Apakah juga merupakan Peneliti?")
    @Column(name = "peneliti" + XMARK)
    private boolean peneliti;



    @ApiModelProperty("Kode Sumber Daya")
    @Column(name = "uuid_sumber_daya" + XMARK)
    private String uuidSumberDaya;


    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;

}
