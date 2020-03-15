package tnc.at.brpl.models.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SuppressWarnings("unused")
public class BiologyOnReproduction3rdPartyDTO extends Main3rdPartyDTO implements Brpl {

//    @Id
//    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
//    @GeneratedValue(generator = "brpl_id")
//    @ApiModelProperty("ID")
//    private String id;

    // header
    @ApiModelProperty("Nama Sumber Daya")
    private String namaSumberDaya;

    @ApiModelProperty("Nama tempat Sampling")
    private String namaLokasiSampling;

    @ApiModelProperty("Nama Kapal")
    private String namaKapal;


    @ApiModelProperty("Nama Spesies")
    private String namaSpesies;

    @ApiModelProperty("Daerah penangkapan/memancing berdasarkan grid yang ditetapkan oleh BRPL")
    private String daerahPenangkapan;

    @ApiModelProperty("Apakah Penampung?")
    private boolean penampung;

    @ApiModelProperty("Apakah Penangkap?")
    private boolean penangkap;

    @ApiModelProperty("Nama Alat Tangkap")
    private String namaAlatTangkap;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalSampling;

    @ApiModelProperty("Nama Enumerator/Pencatat/Peneliti/Partner") //
    private String namaPencatat;

    //detail

    @ApiModelProperty("Data Detail Biologi Reproduksi - (Dengan referensi kode Biologi reproduksi)")
    private List<BiologyOnReproductionDetail3rdPatyDTO> dataDetailReproduksi = new ArrayList<>();

    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private ThirdPartyDocumentStatus statusDokumen;
//    private DocumentStatus statusDokumen;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;

}
