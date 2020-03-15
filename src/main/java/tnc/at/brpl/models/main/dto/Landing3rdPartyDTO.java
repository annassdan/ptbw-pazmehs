package tnc.at.brpl.models.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SuppressWarnings("unused")
public class Landing3rdPartyDTO extends Main3rdPartyDTO implements Brpl {

//    @Id
//    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
//    @GeneratedValue(generator = "brpl_id")
//    @ApiModelProperty("ID")
//    private String id;

    @ApiModelProperty("Tanggal Pendaratan")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalPendaratan;

    @ApiModelProperty("Nama tempat Pendaratan")
    private String namaLokasiPendaratan;

    @ApiModelProperty("Sumberdaya")
    private String namaSumberDaya;

    @ApiModelProperty("Nama Enumerator/Pencatat/Peneliti/Partner")
    private String namaPencatat;

    /** ============= */

    /**
     * Data Rincian Pendaratan
     */
    @ApiModelProperty("Data Operasional - (Dengan referensi Kode Pendaratan)")
    private List<LandingDetail3rdPartyDTO> dataRincianPendaratan = new ArrayList<>();

    /**
     * Data Operasional
     */
    @ApiModelProperty("Data Operasional - (Dengan referensi Kode Pendaratan)")
    private List<Operational3rdPartyDTO> dataOperasional = new ArrayList<>();

    /**
     * Data Biology Ukuran
     */
    @ApiModelProperty("Data Sampling Biology Ukuran - (Dengan referensi kode Pendaratan)")
    private List<BiologyOnSize3rdPartyDTO> dataUkuran = new ArrayList<>();

    /**
     * Data Biology Reproduksi
     */
    @ApiModelProperty("Data Sampling Biology Reproduksi - (Dengan referensi kode Pendaratan)")
    private List<BiologyOnReproduction3rdPartyDTO> dataReproduksi = new ArrayList<>();


    @ApiModelProperty("WPP")
    private String wpp;

    @ApiModelProperty("Status Dokumen")
//    private DocumentStatus statusDokumen;
    private ThirdPartyDocumentStatus statusDokumen;


}
