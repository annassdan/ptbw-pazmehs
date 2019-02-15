package tnc.at.brpl.export.main;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.OperationalCatchDetails;
import tnc.at.brpl.models.main.OperationalOnFishingToolSpecification;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.export.excel.BrplExcelAbstractView;
import tnc.at.brpl.utils.export.excel.ExcelCell;
import tnc.at.brpl.utils.export.excel.ExcelFileNameGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class OperationalAsExcel extends BrplExcelAbstractView {

    private String[][] headerTitle = new String[][]{
            {
                    "No",

                    "Organisasi/Proyek", "Pencatat", "Lokasi Pendaratan", "WPP", "Waktu Sampling", "Jam", "Sumber Daya",

                    "Nama Kapal", "Tanda Selar", "Nama Pemilik Kapal", "Nama Nakhoda", "Tanggal Berangkat", "Tanggal Kembali", "Pelabuhan Asal", "Jumlah ABK",

                    "Panjang Kapal", "Bobot Kapal", "Mesin Utama", "Mesin Bantu", "Material Kapal", "Daya Cahaya", "Jumlah Palka", "Jumlah Boks", "Kapasitas\n" +
                    "Palka/Boks/Keranjang", "Kapal Bantu", "Ukuran Kapal Bantu", "Freezer", "Kapasitas Freezer", "GPS", "Jenis GPS", "Kapal Andon", "Asal Kapal Andon",

                    "Nama Alat Tangkap",
                    "Spesifikasi Alat Tangkap", "", "", // untuk header spesifikasi  alat tangkap
                    "Material/Bahan", "Jumlah Alat Dioperasikan", "Daerah Penangkapan", "Jumlah Setting", "Jumlah Balok Es",
                    "Kedalaman Perairan (m)", "",  // untuk header kedalaman perairan
                    "Jumlah Hari per Trip", "Jumlah Hari Menangkap", "Jenis Rumpon",
                    "Jumlah Rumpon ", "", // untuk header jumlah rumpon
                    "Waktu Memancing", "Lama Perendaman", "Komentar",


                    "Sumber Informasi",
                    "Total Hasil Tangkapan Untuk Dimakan", "", // header untuk diamkan
                    "Total Hasil Tangkapan", "", // header total hasil tangkapan
                    "Nama Spesies", "Kode FAO",
                    "Jumlah", "", // header jumlah tangkapan per spesies
                    "Cara Penyimpanan/ Penanganan", "", "", "", "" // header cara penanganan
            },

            {
                    "", // No

                    "", "", "", "", "", "", "",

                    "", "", "", "", "", "", "", "",

                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",

                    "",
                    "Spesifikasi", "Nilai", "Satuan", // untuk header spesifikasi  alat tangkap
                    "", "", "", "", "",
                    "Mulai", "Hingga", // untuk header Kedalaman perairan
                    "", "", "",
                    "Dikunjungi", "Berhasil", // untuk header jumlah rumpon
                    "", "", "",

                    "",
                    "Volume (Kg)", "Individu (Ekor)", // header untuk diamkan
                    "Volume (Kg)", "Individu (Ekor)", // header total hasil tangkapan
                    "", "",
                    "Volume (Kg)", "Individu (Ekor)", // header jumlah tangkapan per spesies
                    "Segar", "Beku", "Asin", "Loin", "Rebus" // header cara penanganan
            },

    };

    private int[] headerTitleWitdh = new int[] {
            50,

            147, 167, 174, 75, 132, 64, 158,

            160, 95, 178, 178, 132, 132, 164, 67,

            87, 72, 86, 77, 159, 87, 80, 79, 146, 83, 101, 80, 100, 80, 120, 81, 199,

            200,
            200, 107, 87, // untuk header spesifikasialat tangkap
            147, 100, 163, 92, 100,
            86, 86,   // header kedalaman
            115, 103, 155,
            115, 115,   // header jumlah rumpon
            170, 111, 215,


            205,
            124, 124, // header untuk dimakan
            113, 113, // header total hasil tangkapan
            234, 87,
            96, 98, // header jumlah tangkapan per spesies
            78, 78, 78, 78, 78// header cara penanganan


    };


    /**
     * Menghilangkan String kosong yang akan ditampilkan pada data excel
     * @param v
     * @return
     */
    private Object conventionValue(Object v) {
        return (v instanceof String)
                ? (((String) v).isEmpty() ? "-" : v)
                : v;
    }


    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) {

        this.workbook = workbook; // init global workbook

        // generate filename
        String fileName = ExcelFileNameGenerator.generateFileName(StorageService.Target.OPERASIONAL);

        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

        Sheet sheet = workbook.createSheet("Operasional");
        sheet.createFreezePane(0,2);

        IntStream.range(0, headerTitleWitdh.length).forEach(i -> sheet.setColumnWidth(i, headerTitleWitdh[i] * onePixel)); // set column header width

        mergeCells(sheet);  // set merge cell

        CellStyle standardStyle = createStyle();
        CellStyle leftAlignStyle = createStyle();
        CellStyle leftAlignWithWhiteSpacesStyle = createStyle();
        CellStyle bottomBorderStyle = createStyle();
        CellStyle rightBorderStyle = createStyle();
        CellStyle bottomBorderAndLeftAlignStyle = createStyle();
        CellStyle bottomBorderAndLeftAlignWithWhiteSpacesStyle = createStyle();
        CellStyle bottomBorderAndRightBorderStyle = createStyle();


        if (!map.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<Operational> operationals = (List<Operational>) map.get("data");

            // buat style untuk excel
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();

            // koleksi row excel yang telah terbuat pada file ini
            List<Row> rowCollection = new ArrayList<>();

            int startHeader = 0;
            int numberOfRowHeader = 2;

            // Set Header Operasional
            IntStream.range(0, this.headerTitle.length).forEach(index -> {

                Row rowHeader = sheet.createRow(startHeader + index);
                String[] titles = this.headerTitle[index];

                rowCollection.add(rowHeader); // collecting row header

                IntStream.range(0, titles.length).forEach(indexTitle ->
                        ExcelCell.builder().row(rowHeader).cellIndex(indexTitle).allBorder(true).
                                value(titles[indexTitle]).color(IndexedColors.GREY_25_PERCENT.getIndex()).
                                style(headerStyle).font(headerFont).bold(true).build().generate());

            });


            // Set nilai loping data yang ada pada data operasional
            int startCell = 1;
            IntStream.range(0, operationals.size()).forEach(index -> {
                Operational operational = operationals.get(index);

                // increment untuk perdata operasional
                int increment = 0;
                if (index > 0) {
                    for (int i = 0; i < index; i++) {
                        int spesificationTotal = operationals.get(i).getDataSpesifikasiAlatTangkap().size();
                        int catchTotal = operationals.get(i).getDataOperasionalDetailTangkapan().size();

                        increment += (spesificationTotal <= catchTotal) ? catchTotal : spesificationTotal;
                    }
                }
                // ================================== increment untuk perdata operasional


                final int rowIndex = increment + numberOfRowHeader;
                Row row = sheet.createRow(rowIndex);

                rowCollection.add(row); // collecting data row

                ExcelCell.builder().row(row).style(standardStyle).cellIndex(0).value(index + 1).build().generate(); // Nomor

                CellStyle tempLeftAlignStyle = leftAlignStyle;
                CellStyle tempStandardStyle = standardStyle;

                // informasi umum
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell).value(conventionValue(operational.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 1).value(conventionValue(operational.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 2).value(conventionValue(operational.getNamaLokasiPendaratan())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 3).value(conventionValue(operational.getWpp())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 4).value(conventionValue(operational.getTanggalSampling())).build().generate();
                SimpleDateFormat formatter = new SimpleDateFormat(Brpl.TIME_PATTERN);
                String formattedDate = formatter.format(operational.getJamSampling());
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 5).value(conventionValue(formattedDate)).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 6).value(conventionValue(operational.getUuidSumberDaya())).build().generate();

                // Informasi Kapal
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 7).value(conventionValue(operational.getNamaKapal())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 8).value(conventionValue(operational.getTandaSelar())).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 9).value(conventionValue(operational.getNamaPemilikKapal())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 10).value(conventionValue(operational.getNamaKapten())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 11).value(conventionValue(operational.getTanggalBerangkat())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 12).value(conventionValue(operational.getTanggalKembali())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 13).value(conventionValue(operational.getPelabuhanAsal())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 14).value(conventionValue(operational.getJumlahAbk())).build().generate();

                // Rincian Kapal
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 15).value(conventionValue(operational.getPanjangKapal())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 16).value(conventionValue(operational.getBobotKapal())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 17).value(conventionValue(operational.getMesinUtama())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 18).value(conventionValue(operational.getMesinBantu())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 19).value(conventionValue(operational.getMaterialKapal())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 20).value(conventionValue(operational.getDayaCahaya())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 21).value(conventionValue(operational.getJumlahPalka())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 22).value(conventionValue(operational.getJumlahBoks())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 23).value(conventionValue(operational.getKapasitasPalkaBoks())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 24).value(conventionValue(operational.isKapalBantu())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 25).value(conventionValue(operational.getUkuranKapalBantu())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 26).value(conventionValue(operational.isFreezer())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 27).value(conventionValue(operational.getKapasitasFreezer())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 28).value(conventionValue(operational.isGps())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 29).value(conventionValue(operational.getJenisGps())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 30).value(conventionValue(operational.isKapalAndon())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 31).value(conventionValue(operational.getAsalKapalAndon())).build().generate();

                // Rincian Alat tangkap
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 32).value(operational.getUuidAlatTangkap()).align(HorizontalAlignment.LEFT).build().generate();

                final int finalIncrement = increment;

                // data spesifikasi alat tangkap 3 kolom
                final int specLength = operational.getDataSpesifikasiAlatTangkap().size();
                IntStream.range(0, specLength).forEach(indexSpec -> {

                    final int rowSpecIndex = indexSpec + finalIncrement + numberOfRowHeader;
                    OperationalOnFishingToolSpecification specification = operational.getDataSpesifikasiAlatTangkap().get(indexSpec);

                    Row rowSpec = (rowIndex == rowSpecIndex) ? row : sheet.createRow(rowSpecIndex);

                    if (rowIndex != rowSpecIndex) { // collecting row spec
                        rowCollection.add(rowSpec);
                    }

                    boolean lastRowOnSpec = (operational.getDataSpesifikasiAlatTangkap().size() >= operational.getDataOperasionalDetailTangkapan().size())
                            && (indexSpec == specLength - 1);

                    // set garis bawah untuk akhir tiap data
                    if (lastRowOnSpec) {
                        for (int i = 0; i < (startCell + 33); i++)
                            ExcelCell.builder().row(rowSpec).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();
                        for (int i = (startCell + 36); i < (startCell + 64); i++)
                            ExcelCell.builder().row(rowSpec).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();
                    }


                    ExcelCell.builder().row(rowSpec).style((lastRowOnSpec) ? bottomBorderAndLeftAlignStyle : leftAlignStyle).bottomBorder(lastRowOnSpec).cellIndex(startCell + 33).value(conventionValue(specification.getSpesifikasi())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowSpec).style((lastRowOnSpec) ? bottomBorderStyle : standardStyle).bottomBorder(lastRowOnSpec).cellIndex(startCell + 34).value(conventionValue(specification.getNilaiSpesifikasi())).build().generate();
                    ExcelCell.builder().row(rowSpec).style((lastRowOnSpec) ? bottomBorderStyle : standardStyle).bottomBorder(lastRowOnSpec).cellIndex(startCell + 35).value(conventionValue(specification.getSatuanSpesifikasi())).build().generate();

                    if (lastRowOnSpec) {
                        ExcelCell.builder().row(rowSpec).cellIndex(startCell + 64).style(bottomBorderAndRightBorderStyle).rightBorder(true).bottomBorder(true).build().generate();
                    }
                });



                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 36).value(conventionValue(operational.getMaterial())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 37).value(conventionValue(operational.getJumlahAlatTangkapYangDioperasikan())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 38).value(conventionValue(operational.getDaerahPenangkapan())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 39).value(conventionValue(operational.getJumlahSetting())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 40).value(conventionValue(operational.getJumlahBalokEs())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 41).value(conventionValue(operational.getKedalamanAirMulai())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 42).value(conventionValue(operational.getKedalamanAirHingga())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 43).value(conventionValue(operational.getJumlahHariPerTrip())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 44).value(conventionValue(operational.getJumlahHariMenangkap())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 45).value(conventionValue(operational.getJenisRumpon())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 46).value(conventionValue(operational.getJumlahRumponDikunjungi())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 47).value(conventionValue(operational.getJumlahRumponBerhasil())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 48).value(conventionValue(operational.getWaktuMemancing())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 49).value(conventionValue(operational.getLamaPerendaman())).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 50).value(conventionValue(operational.getKomentar())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 51).value(conventionValue(operational.getSumberInformasi())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 52).value(conventionValue(operational.getJumlahTangkapanUntukDimakanDilautVolume())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 53).value(conventionValue(operational.getJumlahTangkapanUntukDimakanDilautIndividu())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 54).value(conventionValue(operational.getJumlahTangkapanVolume())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 55).value(conventionValue(operational.getJumlahTangkapanIndividu())).build().generate();

                final int catchDetailLength = operational.getDataOperasionalDetailTangkapan().size();
                IntStream.range(0, catchDetailLength).forEach(indexCatchDetail -> {
                    OperationalCatchDetails catchDetails = operational.getDataOperasionalDetailTangkapan().get(indexCatchDetail);

                    final int rowCatchIndex = indexCatchDetail + finalIncrement + numberOfRowHeader;
                    Row rowCatch;
                    if (rowIndex != rowCatchIndex) {
                        if ((rowCatchIndex) < rowCollection.size() ){
                            rowCatch = rowCollection.get(rowCatchIndex);
                        } else {
                            rowCatch = sheet.createRow(rowCatchIndex);
                            rowCollection.add(rowCatch);
                        }
                    } else {
                        rowCatch = row;
                    }

                    boolean lastRowOnCatch = (operational.getDataOperasionalDetailTangkapan().size() > operational.getDataSpesifikasiAlatTangkap().size())
                            && (indexCatchDetail == catchDetailLength - 1);

                    // set garis bawah untuk akhir tiap data
                    if (lastRowOnCatch) {
                        for (int i = 0; i < (startCell + 56); i++) {
                            ExcelCell.builder().row(rowCatch).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();
                        }
                    }


                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderAndLeftAlignWithWhiteSpacesStyle : leftAlignWithWhiteSpacesStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 56).whiteSpace(3).value(conventionValue(catchDetails.getUuidSpesies())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 57).value(conventionValue(catchDetails.getKodeFao())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 58).value(conventionValue(catchDetails.getTotalBeratKg())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 59).value(conventionValue(catchDetails.getTotalBeratEkor())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 60).value(conventionValue(catchDetails.isSegar())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 61).value(conventionValue(catchDetails.isBeku())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 62).value(conventionValue(catchDetails.isAsin())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderStyle : standardStyle)
                            .bottomBorder(lastRowOnCatch).cellIndex(startCell + 63).value(conventionValue(catchDetails.isLoin())).build().generate();
                    ExcelCell.builder().row(rowCatch)
                            .style((lastRowOnCatch) ? bottomBorderAndRightBorderStyle : rightBorderStyle)
                            .bottomBorder(lastRowOnCatch).rightBorder(true).cellIndex(startCell + 64).value(conventionValue(catchDetails.isRebus())).build().generate();
                });




            });



        }
    }


    /**
     *
     * @param sheet
     */
    public void mergeCells(Sheet sheet) {
        for (int i = 0; i <= 33; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 34, 36)); // spesifikasi alat tangkap

        for (int i = 37; i <= 41; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 42, 43)); // kedalaman perairan

        for (int i = 44; i <= 46; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 47, 48)); // jumlah rumpon

        for (int i = 49; i <= 52; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 53, 54));  // hasil tangkapan untuk dimakan
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 55, 56));  // hasil tangkapan

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 57, 57));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 58, 58));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 59, 60));  // Jumlah
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 61, 65));  // Cara penanganan
    }
}