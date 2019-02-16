package tnc.at.brpl.models.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.models.main.BiologyOnSizeDetail;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class BiologyOnSize3rdPartyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;

    // header

    @ApiModelProperty("Nama Enumerator/Pencatat/Peneliti/Partner")
    private String namaPencatat;

    @ApiModelProperty("Nama Sumber Daya")
    private String namaSumberDaya;

    @ApiModelProperty("Nama tempat Pendaratan")
    private String namaLokasiSampling;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalSampling;

    @ApiModelProperty("Nama Kapal")
    private String namaKapal;

    @ApiModelProperty("Daerah penangkapan/memancing berdasarkan grid yang ditetapkan oleh BRPL")
    private String daerahPenangkapan;

    @ApiModelProperty("Nama Alat Tangkap")
    private String namaAlatTangkap;

    @ApiModelProperty("Apakah Penampung?")
    private boolean penampung;

    @ApiModelProperty("Apakah Penangkap?")
    private boolean penangkap;

    @ApiModelProperty("Total Tangkapan (Kg)")
    private double totalTangkapanVolume;

    @ApiModelProperty("Total Tangkapan (Ekor)")
    private int totalTangkapanIndividu;

    @ApiModelProperty("Jumlah total yang disampling per Individu (Ekor)")
    private int totalSampelIndividu;

    @ApiModelProperty("Jumlah total yang disampling per volume (Kg)")
    private double totalSampelVolume;

    /**
     * Data detail sample yang diambil
     */
    @ApiModelProperty("Data Rincian Jumlah Sampel yang digunakan - (Dengan referensi kode Biologi Ukuran)")
    private List<BiologyOnSizeSampleDetail3rdPartyDTO> dataSampleDetail = new ArrayList<>();

    /**
     * Detail ukuran ikan dari sample
     */
    @ApiModelProperty("Data Detail Biologi Ukuran - (Dengan referensi kode Biologi Ukuran)")
    private List<BiologyOnSizeDetail3rdPartyDTO> dataUkuranDetail = new ArrayList<>();


    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private DocumentStatus statusDokumen;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;

}
