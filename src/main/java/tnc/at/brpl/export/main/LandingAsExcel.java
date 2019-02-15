package tnc.at.brpl.export.main;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.LandingCatchDetail;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.utils.export.excel.BrplExcelAbstractView;
import tnc.at.brpl.utils.export.excel.ExcelCell;
import tnc.at.brpl.utils.export.excel.ExcelFileNameGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class LandingAsExcel extends BrplExcelAbstractView {

    private String[][] headerTitle = new String[][]{
            {
                    "No",

                    "Organisasi/Proyek", "Pencatat", "Lokasi Pendaratan", "WPP", "Waktu Pendaratan", "Sumber Daya",

                    "Nama Kapal", "Penangkap", "Penampung", "Jumlah Kapal Penangkap", "Nama Alat Tangkap", "Rumpon", "Cahaya", "Daerah Penangkapan", "Effort (Upaya)", "", "", "",
                    "Total Hasil Tangkapan", "",

                    "Nama Spesies", "Hasil Tangkapan", ""
            },

            {
                    "",

                    "", "", "", "", "", "",

                    "", "", "", "", "", "", "", "", "Jumlah Hari per Trip", "Jumlah Hari Menangkap", "Jumlah Setting", "Jumlah Mata Pancing", "Volume (kg)", "Individu (ekor)",

                    "", "Volume (kg)", "Individu (ekor)"
            }
    };


    private int[] headerTitleWitdh = new int[] {
            50,

            147, 167, 174, 75, 132, 158,

            160, 80, 80, 121, 160, 64, 64, 128, 107, 95, 76, 92, 78, 78,

            183, 78, 78
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




    /**
     * @param map
     * @param workbook
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) {

        this.workbook = workbook; // init global workbook


        // generate filename
        String fileName = ExcelFileNameGenerator.generateFileName(StorageService.Target.PENDARATAN);

        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

        Sheet sheet = workbook.createSheet("Pendaratan");
        sheet.createFreezePane(0,2);

        IntStream.range(0, headerTitleWitdh.length).forEach(i -> sheet.setColumnWidth(i, headerTitleWitdh[i] * onePixel)); // set column header width

        mergeCells(sheet);  // set merge cell

        CellStyle standardStyle = createStyle();
        CellStyle leftAlignStyle = createStyle();
        CellStyle bottomBorderStyle = createStyle();
        CellStyle rightBorderStyle = createStyle();
        CellStyle bottomBorderAndLeftAlignStyle = createStyle();
        CellStyle bottomBorderAndRightBorderStyle = createStyle();

        if (!map.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<Landing> landings = (List<Landing>) map.get("data");

            // buat style untuk excel
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();

            int startHeader = 0;
            int numberOfRowHeader = 2;
            // Set Header Pendaratan
            IntStream.range(0, this.headerTitle.length).forEach(index -> {

                Row rowHeader = sheet.createRow(startHeader + index);
                String[] titles = this.headerTitle[index];

                IntStream.range(0, titles.length).forEach(indexTitle ->
                        ExcelCell.builder().row(rowHeader).cellIndex(indexTitle).allBorder(true).
                        value(titles[indexTitle]).color(IndexedColors.GREY_25_PERCENT.getIndex()).
                        style(headerStyle).font(headerFont).bold(true).build().generate());

            });

            // Set nilai loping data yang ada pada data pendaratan
            int startCell = 1;

            CellStyle cellStyle = createStyle();
            IntStream.range(0, landings.size()).forEach(index -> {
                Landing landing = landings.get(index);

                // increment untuk perdata landing
                int increment = 0;
                if (index > 0) {
                    for (int i = 0; i < index; i++) {
                        int shipsTotal = landings.get(i).getDataRincianPendaratan().size();
                        int shipsTotalCatch = 0;
                        for (LandingDetail landingDetail : landings.get(i).getDataRincianPendaratan()) {
                            shipsTotalCatch += landingDetail.getDataRincianTangkapanPendaratan().size();
                        }

                        increment += (shipsTotal < shipsTotalCatch) ? shipsTotalCatch : shipsTotal;
                    }
                }
                // ================================== increment untuk perdata landing

                // cetak data pendaratan
                final int rowIndex = increment + numberOfRowHeader;
                Row row = sheet.createRow(rowIndex);

                ExcelCell.builder().row(row).style(standardStyle).cellIndex(0).value(index + 1).build().generate(); // Nomor

//                buildGeneralInformation(landing, row, cellStyle, startCell);  // informasi umum

                ExcelCell.builder().row(row).style(leftAlignStyle).cellIndex(startCell).value(conventionValue(landing.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(leftAlignStyle).cellIndex(startCell + 1).value(conventionValue(landing.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(standardStyle).cellIndex(startCell + 2).value(conventionValue(landing.getNamaLokasiPendaratan())).build().generate();
                ExcelCell.builder().row(row).style(standardStyle).cellIndex(startCell + 3).value(conventionValue(landing.getWpp())).build().generate();
                ExcelCell.builder().row(row).style(standardStyle).cellIndex(startCell + 4).value(conventionValue(landing.getTanggalPendaratan())).build().generate();
                ExcelCell.builder().row(row).style(leftAlignStyle).cellIndex(startCell + 5).value(conventionValue(landing.getUuidSumberDaya())).align(HorizontalAlignment.LEFT).build().generate();
                // ===================== end of cetak data pendaratan

                // data detail pendaratan
                final int finalIncrement = increment;
                final int detailDataLength = landing.getDataRincianPendaratan().size();
                IntStream.range(0, detailDataLength).forEach(indexDetail -> {
                    LandingDetail landingDetail = landing.getDataRincianPendaratan().get(indexDetail);
                    boolean lastDetail = indexDetail == detailDataLength - 1; // jika merupakan data detail pendartan yang terakhir


                    // increment untuk perdata landing detail
                    int incrementDetails = 0;
                    if (indexDetail > 0) {
                        for (int i = 0; i < indexDetail; i++) {
                            int catchTotal = landing.getDataRincianPendaratan().get(i).getDataRincianTangkapanPendaratan().size();
                            incrementDetails += catchTotal;
                        }
                    }
                    // ================================ end of increment untuk perdata landing detail

                    final int rowDetailIndex = incrementDetails + finalIncrement + numberOfRowHeader;
                    Row rowDetail = (rowIndex == rowDetailIndex) ? row : sheet.createRow(rowDetailIndex);


                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(0).value(index + 1).build().generate(); // Nomor
                    // dari header landing
                    ExcelCell.builder().row(rowDetail).style(leftAlignStyle).cellIndex(startCell).value(conventionValue(landing.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowDetail).style(leftAlignStyle).cellIndex(startCell + 1).value(conventionValue(landing.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 2).value(conventionValue(landing.getNamaLokasiPendaratan())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 3).value(conventionValue(landing.getWpp())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 4).value(conventionValue(landing.getTanggalPendaratan())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(leftAlignStyle).cellIndex(startCell + 5).value(conventionValue(landing.getUuidSumberDaya())).align(HorizontalAlignment.LEFT).build().generate();
                    ///

//                    buildLandingInfo(rowDetail, landingDetail, cellStyle, startCell); // info pendaratan

                    ExcelCell.builder().row(rowDetail).style(leftAlignStyle).cellIndex(startCell + 6).value(conventionValue(landingDetail.getNamaKapal())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 7).value(conventionValue(landingDetail.isPenangkap())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 8).value(conventionValue(landingDetail.isPenampung())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 9).value(conventionValue(landingDetail.getJumlahKapalPenangkap())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(leftAlignStyle).cellIndex(startCell + 10).value(conventionValue(landingDetail.getUuidAlatTangkap())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 11).value(conventionValue(landingDetail.isRumpon())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 12).value(conventionValue(landingDetail.isCahaya())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 13).value(conventionValue(landingDetail.getDaerahPenangkapan())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 14).value(conventionValue(landingDetail.getJumlahHariPerTrip())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 15).value(conventionValue(landingDetail.getJumlahHariMenangkap())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 16).value(conventionValue(landingDetail.getJumlahSetting())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 17).value(conventionValue(landingDetail.getJumlahMataPancing())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 18).value(conventionValue(landingDetail.getTotalTangkapanVolume())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(standardStyle).cellIndex(startCell + 19).value(conventionValue(landingDetail.getTotalTangkapanIndividu())).build().generate();

                    // Data Catch Tangkapan perkapal
                    final int finalIncrementDetails = incrementDetails;
                    final int catchDataLength = landingDetail.getDataRincianTangkapanPendaratan().size();
                    IntStream.range(0, catchDataLength).forEach(indexDetailCatch -> {



                        boolean last = (indexDetailCatch == catchDataLength - 1) && lastDetail;
                        LandingCatchDetail landingCatchDetail = landingDetail.getDataRincianTangkapanPendaratan().get(indexDetailCatch);

                        final int rowCatchIndex = indexDetailCatch + finalIncrementDetails + finalIncrement + numberOfRowHeader;
                        Row rowCatch = (rowDetailIndex == rowCatchIndex) ? rowDetail : sheet.createRow(rowCatchIndex);

                        // set garis bawah untuk akhir tiap data detail pendaratan
                        if (last) {
                            for (int i = 0; i < (startCell + 20); i++) {
                                ExcelCell.builder().row(rowCatch).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();
                            }
                        }

                        CellStyle tempSpesiesStyle = (last) ? bottomBorderAndLeftAlignStyle : leftAlignStyle;


                        // dari rincian pendaratan

                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(0).value(index + 1).build().generate(); // Nomor
                        // dari header landing
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(leftAlignStyle).cellIndex(startCell).value(conventionValue(landing.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(leftAlignStyle).cellIndex(startCell + 1).value(conventionValue(landing.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 2).value(conventionValue(landing.getNamaLokasiPendaratan())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 3).value(conventionValue(landing.getWpp())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 4).value(conventionValue(landing.getTanggalPendaratan())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(leftAlignStyle).cellIndex(startCell + 5).value(conventionValue(landing.getUuidSumberDaya())).align(HorizontalAlignment.LEFT).build().generate();
//                        ///
//
////                    buildLandingInfo(rowDetail, landingDetail, cellStyle, startCell); // info pendaratan
//
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(leftAlignStyle).cellIndex(startCell + 6).value(conventionValue(landingDetail.getNamaKapal())).align(HorizontalAlignment.LEFT).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 7).value(conventionValue(landingDetail.isPenangkap())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 8).value(conventionValue(landingDetail.isPenampung())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 9).value(conventionValue(landingDetail.getJumlahKapalPenangkap())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(leftAlignStyle).cellIndex(startCell + 10).value(conventionValue(landingDetail.getUuidAlatTangkap())).align(HorizontalAlignment.LEFT).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 11).value(conventionValue(landingDetail.isRumpon())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 12).value(conventionValue(landingDetail.isCahaya())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 13).value(conventionValue(landingDetail.getDaerahPenangkapan())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 14).value(conventionValue(landingDetail.getJumlahHariPerTrip())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 15).value(conventionValue(landingDetail.getJumlahHariMenangkap())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 16).value(conventionValue(landingDetail.getJumlahSetting())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 17).value(conventionValue(landingDetail.getJumlahMataPancing())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 18).value(conventionValue(landingDetail.getTotalTangkapanVolume())).build().generate();
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).style(standardStyle).cellIndex(startCell + 19).value(conventionValue(landingDetail.getTotalTangkapanIndividu())).build().generate();


                        //


                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempSpesiesStyle).cellIndex(startCell + 20).value(conventionValue(landingCatchDetail.getUuidSpesies())).align(HorizontalAlignment.LEFT).build().generate();
                        CellStyle tempTangkapanVolumeStyle = (last) ? bottomBorderStyle : standardStyle;
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .style(tempTangkapanVolumeStyle).cellIndex(startCell + 21).value(conventionValue(landingCatchDetail.getTangkapanVolume())).build().generate();
                        CellStyle tempTgpnIndividuStyle = (last) ? bottomBorderAndRightBorderStyle : rightBorderStyle;
                        ExcelCell.builder().row(rowCatch).bottomBorder(last)
                                .rightBorder(true).style(tempTgpnIndividuStyle).cellIndex(startCell + 22).value(conventionValue(landingCatchDetail.getTangkapanIndividu())).build().generate();
                    });
                    // ============================= end of Data Catch Tangkapan perkapal

                });
                // ====================== end of data detail pendaratan


            });
            // =================================================== end of Set nilai loping data yang ada pada data pendaratan


        }
    }


    /**
     * @param sheet
     */
    public void mergeCells(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 13, 13));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 14, 14));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 18)); // effort
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 20)); // total hasil tangkapan
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 21, 21));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 22, 23)); // hasil tangkapan
    }


}