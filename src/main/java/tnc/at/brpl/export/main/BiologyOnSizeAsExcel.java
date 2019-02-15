package tnc.at.brpl.export.main;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.main.*;
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

public class BiologyOnSizeAsExcel extends BrplExcelAbstractView {

    private String[][] headerTitle = new String[][]{
            {
                    "No",

                    "Organisasi/Proyek", "Pencatat", "Lokasi Sampling", "Waktu Sampling", "Sumber Daya", "WPP", "Nama Kapal", "Nama Alat Tangkap",
                    "Penangkap", "Penampung", "Daerah Penangkapan",
                    "Total Hasil Tangkapan", "",
                    "Jumlah Sampel", "",

                    "Nama Spesies",
                    "Jumlah", "",
                    "Tipe Panjang",

                    "Spesies", "Panjang (cm)"
            },
            {
                    "",

                    "", "", "", "", "", "", "", "",
                    "", "", "",
                    "Volume (Kg)", "Individu (Ekor)", // total  hasil tangkapan
                    "Volume (Kg)", "Individu (Ekor)", // jumlah sampel

                    "",
                    "Vol (Kg)", "Ind (Ekor)", // detail jumlah sampel epr spesies
                    "",

                    "", ""
            }
    };

    private int[] headerTitleWitdh = new int[]
            {
                    50,

                    147, 167, 174, 132, 142, 75, 160, 200,
                    80, 80, 128,
                    113, 113,
                    110, 110,

                    234,
                    86, 86,
                    70,

                    234, 83
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
        String fileName = ExcelFileNameGenerator.generateFileName(StorageService.Target.BIOLOGI_UKURAN);

        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

        Sheet sheet = workbook.createSheet("Biologi Ukuran");
        sheet.createFreezePane(0,2);

        IntStream.range(0, headerTitleWitdh.length).forEach(i -> sheet.setColumnWidth(i, headerTitleWitdh[i] * onePixel)); // set column header width

        mergeCells(sheet);

        CellStyle standardStyle = createStyle();
        CellStyle leftAlignStyle = createStyle();
        CellStyle leftAlignWithWhiteSpacesStyle = createStyle();
        CellStyle bottomBorderStyle = createStyle();
        CellStyle rightBorderStyle = createStyle();
        CellStyle bottomBorderAndLeftAlignWithWhiteSpacesStyle = createStyle();
        CellStyle bottomBorderAndRightBorderStyle = createStyle();

        if (!map.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<BiologyOnSize> sizes = (List<BiologyOnSize>) map.get("data");

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


            int startCell = 1;
            IntStream.range(0, sizes.size()).forEach(index -> {
                BiologyOnSize size = sizes.get(index);

                // increment untuk perdata operasional
                int increment = 0;
                if (index > 0) {
                    for (int i = 0; i < index; i++) {
                        int sample = sizes.get(i).getDataSampleDetail().size();
                        int details = sizes.get(i).getDataUkuranDetail().size();

                        increment += (sample <= details) ? details : sample;
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
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell).value(conventionValue(size.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 1).value(conventionValue(size.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 2).value(conventionValue(size.getNamaLokasiSampling())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 3).value(conventionValue(size.getTanggalSampling())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 4).value(conventionValue(size.getUuidSumberDaya())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 5).value(conventionValue(size.getWpp())).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 6).value(conventionValue(size.getNamaKapal())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 7).value(conventionValue(size.getUuidAlatTangkap())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 8).value(conventionValue(size.isPenangkap())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 9).value(conventionValue(size.isPenampung())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 10).value(conventionValue(size.getDaerahPenangkapan())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 11).value(conventionValue(size.getTotalTangkapanVolume())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 12).value(conventionValue(size.getTotalTangkapanIndividu())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 13).value(conventionValue(size.getTotalSampelVolume())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 14).value(conventionValue(size.getTotalSampelIndividu())).build().generate();


                final int finalIncrement = increment;

                // data spesifikasi alat tangkap 3 kolom
                final int sampleLength = size.getDataSampleDetail().size();
                IntStream.range(0, sampleLength).forEach(indexSample -> {

                    final int rowSampleIndex = indexSample + finalIncrement + numberOfRowHeader;
                    BiologyOnSizeSampleDetail sampleDetail = size.getDataSampleDetail().get(indexSample);

                    Row rowSample = (rowIndex == rowSampleIndex) ? row : sheet.createRow(rowSampleIndex);

                    if (rowIndex != rowSampleIndex) { // collecting row spec
                        rowCollection.add(rowSample);
                    }

                    boolean lastRowOnSample = (size.getDataSampleDetail().size() >= size.getDataUkuranDetail().size())
                            && (indexSample == sampleLength - 1);

                    // set garis bawah untuk akhir tiap data
                    if (lastRowOnSample) {
                        for (int i = 0; i <= (startCell + 14); i++)
                            ExcelCell.builder().row(rowSample).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();

                    }

                    ExcelCell.builder().row(rowSample)
                            .style((lastRowOnSample) ? bottomBorderAndLeftAlignWithWhiteSpacesStyle : leftAlignWithWhiteSpacesStyle)
                            .bottomBorder(lastRowOnSample).cellIndex(startCell + 15).whiteSpace(3).value(conventionValue(sampleDetail.getUuidSpesies())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowSample).style((lastRowOnSample) ? bottomBorderStyle : standardStyle).bottomBorder(lastRowOnSample).cellIndex(startCell + 16).value(conventionValue(sampleDetail.getSampleVolume())).build().generate();
                    ExcelCell.builder().row(rowSample).style((lastRowOnSample) ? bottomBorderStyle : standardStyle).bottomBorder(lastRowOnSample).cellIndex(startCell + 17).value(conventionValue(sampleDetail.getSampleIndividu())).build().generate();
                    ExcelCell.builder().row(rowSample).style((lastRowOnSample) ? bottomBorderStyle : standardStyle).bottomBorder(lastRowOnSample).cellIndex(startCell + 18).value(conventionValue(sampleDetail.getTipePanjang())).build().generate();

                    if (lastRowOnSample) {
                        ExcelCell.builder().row(rowSample).cellIndex(startCell + 19).style(bottomBorderStyle).bottomBorder(true).build().generate();
                        ExcelCell.builder().row(rowSample).cellIndex(startCell + 20).style(bottomBorderAndRightBorderStyle).rightBorder(true).bottomBorder(true).build().generate();
                    }
                });


                final int detailLength = size.getDataUkuranDetail().size();
                IntStream.range(0, detailLength).forEach(indexDetail -> {
                    BiologyOnSizeDetail detail = size.getDataUkuranDetail().get(indexDetail);

                    final int rowDetailIndex = indexDetail + finalIncrement + numberOfRowHeader;
                    Row rowDetail;

                    if (rowIndex != rowDetailIndex) {
                        if ((rowDetailIndex) < rowCollection.size() ){
                            rowDetail = rowCollection.get(rowDetailIndex);
                        } else {
                            rowDetail = sheet.createRow(rowDetailIndex);
                            rowCollection.add(rowDetail);
                        }
                    } else {
                        rowDetail = row;
                    }

                    boolean lastRowOnDetails = (size.getDataUkuranDetail().size() > size.getDataSampleDetail().size())
                            && (indexDetail == detailLength - 1);

                    // set garis bawah untuk akhir tiap data
                    if (lastRowOnDetails) {
                        for (int i = 0; i <= (startCell + 18); i++) {
                            ExcelCell.builder().row(rowDetail).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();
                        }
                    }

                    ExcelCell.builder().row(rowDetail)
                            .style((lastRowOnDetails) ? bottomBorderAndLeftAlignWithWhiteSpacesStyle : leftAlignWithWhiteSpacesStyle)
                            .bottomBorder(lastRowOnDetails).cellIndex(startCell + 19).whiteSpace(3).value(conventionValue(detail.getUuidSpesies())).align(HorizontalAlignment.LEFT).build().generate();
                    ExcelCell.builder().row(rowDetail)
                            .style((lastRowOnDetails) ? bottomBorderAndRightBorderStyle : rightBorderStyle)
                            .bottomBorder(lastRowOnDetails).cellIndex(startCell + 20).rightBorder(true).value(conventionValue(detail.getPanjang())).build().generate();

                });




            });


        }
    }


    public void mergeCells(Sheet sheet) {
        for (int i = 0; i <= 11; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 13)); // Total hasil tangkapan
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 15)); // jumlah sampel

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 16, 16));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18)); // jumlah
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 19, 19));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 20, 20));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 21, 21));





    }
}
