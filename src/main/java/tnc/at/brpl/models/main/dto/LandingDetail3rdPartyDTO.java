package tnc.at.brpl.models.main.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import tnc.at.brpl.utils.entity.EntityModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class LandingDetail3rdPartyDTO extends EntityModel<LandingDetail3rdPartyDTO, String> {

    @ApiModelProperty("Nama Kapal")
    private String namaKapal;

    @ApiModelProperty("Apakah Penampung?")
    private boolean penampung;

    @ApiModelProperty("Apakah Penangkap?")
    private boolean penangkap;

    @ApiModelProperty("Jumlah Kapal Penangkap")
    private int jumlahKapalPenangkap;

    @ApiModelProperty("Nama Alat Tangkap")
    private String namaAlatTangkap;

    @ApiModelProperty("Jumlah Kali Seting Alat Tangkap")
    private int jumlahSetting;

    @ApiModelProperty("Jumlah Mata Pancing")
    private int jumlahMataPancing;

    @ApiModelProperty("Rumpon ")
    private boolean rumpon;

    @ApiModelProperty("Cahaya")
    private boolean cahaya;

    @ApiModelProperty("Daerah penangkapan/memancing berdasarkan grid yang ditetapkan oleh BRPL")
    private String daerahPenangkapan;


    @ApiModelProperty("Jumlah Hari Per Trip")
    private int jumlahHariPerTrip;

    @ApiModelProperty("Jumlah Hari Menangkap")
    private int jumlahHariMenangkap;

    @ApiModelProperty("Total Tangkapan (Kg)")
    private double totalTangkapanVolume;

    @ApiModelProperty("Total Tangkapan (Ekor)")
    private int totalTangkapanIndividu;

    /**
     * Data Detail Tangkapan
     */
    @ApiModelProperty("Data Detail Tangkapan pada Pendaratan - (Dengan referensi kode Rincian Pendaratan)")
    private List<LandingCatchDetail3rdPartyDTO> dataRincianTangkapanPendaratan = new ArrayList<>();



}
