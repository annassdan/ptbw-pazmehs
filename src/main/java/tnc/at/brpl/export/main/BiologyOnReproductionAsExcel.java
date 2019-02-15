package tnc.at.brpl.export.main;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnReproductionDetail;
import tnc.at.brpl.utils.export.excel.BrplExcelAbstractView;
import tnc.at.brpl.utils.export.excel.ExcelCell;
import tnc.at.brpl.utils.export.excel.ExcelFileNameGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class BiologyOnReproductionAsExcel extends BrplExcelAbstractView {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private String[][] headerTitle = new String[][]{
            {
                    "No",

                    "Organisasi/Proyek", "Pencatat", "Lokasi Sampling", "Tanggal Sampling", "Sumber Daya", "WPP", "Daerah Penangkapan",
                    "Nama Kapal", "Penangkap", "Penampung", "Alat Tangkap", "Spesies",

                    "No",
                    "Ukuran", "", // Ukuran sampel biologi
                    "W (gr)", "Sex", "TKG", "Wg (gr)"
            },
            {
                    "",

                    "", "", "", "", "", "", "",
                    "", "", "", "", "",

                    "",
                    "Pjg (cm)", "Tipe Pjg", // Ukuran sampel biologi
                    "", "", "", ""
            }
    };

    private int[] headerTitleWitdh = new int[]{
            50,

            147, 167, 174, 132, 142, 75, 128,
            160, 80, 80, 200, 234,

            50,
            85, 85,
            90, 100, 160, 90
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
        String fileName = ExcelFileNameGenerator.generateFileName(StorageService.Target.BIOLOGI_REPRODUKSI);


        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

        Sheet sheet = workbook.createSheet("Biologi Reproduksi");
        sheet.createFreezePane(0,2);

        IntStream.range(0, headerTitleWitdh.length).forEach(i -> sheet.setColumnWidth(i, headerTitleWitdh[i] * onePixel)); // set column header width

        mergeCells(sheet);

        CellStyle standardStyle = createStyle();
        CellStyle leftAlignStyle = createStyle();
        CellStyle bottomBorderStyle = createStyle();
        CellStyle rightBorderStyle = createStyle();
        CellStyle bottomBorderAndRightBorderStyle = createStyle();

        if (!map.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<BiologyOnReproduction> reproductions = (List<BiologyOnReproduction>) map.get("data");

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


            int startCell = 1;
            IntStream.range(0, reproductions.size()).forEach(index -> {
                BiologyOnReproduction reproduction = reproductions.get(index);

                // increment untuk perdata operasional
                int increment = 0;
                if (index > 0) {
                    for (int i = 0; i < index; i++) {
                        int details = reproductions.get(i).getDataDetailReproduksi().size();
                        increment += details;
                    }
                }
                // ================================== increment untuk perdata operasional


                final int rowIndex = increment + numberOfRowHeader;
                Row row = sheet.createRow(rowIndex);

                ExcelCell.builder().row(row).style(standardStyle).cellIndex(0).value(index + 1).build().generate(); // Nomor

                CellStyle tempLeftAlignStyle = leftAlignStyle;
                CellStyle tempStandardStyle = standardStyle;

                // informasi umum
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell).value(conventionValue(reproduction.getOrganisasi())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 1).value(conventionValue(reproduction.getUuidEnumerator())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 2).value(conventionValue(reproduction.getNamaLokasiSampling())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 3).value(conventionValue(reproduction.getTanggalSampling())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 4).value(conventionValue(reproduction.getUuidSumberDaya())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 5).value(conventionValue(reproduction.getWpp())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 6).value(conventionValue(reproduction.getDaerahPenangkapan())).build().generate();
                ExcelCell.builder().row(row).style(tempLeftAlignStyle).cellIndex(startCell + 7).value(conventionValue(reproduction.getNamaKapal())).align(HorizontalAlignment.LEFT).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 8).value(conventionValue(reproduction.isPenangkap())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 9).value(conventionValue(reproduction.isPenampung())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 10).value(conventionValue(reproduction.getUuidAlatTangkap())).build().generate();
                ExcelCell.builder().row(row).style(tempStandardStyle).cellIndex(startCell + 11).value(conventionValue(reproduction.getUuidSpesies())).build().generate();

                final int finalIncrement = increment;

                // data spesifikasi alat tangkap 3 kolom
                final int detailLength = reproduction.getDataDetailReproduksi().size();
                IntStream.range(0, detailLength).forEach(indexDetail -> {

                    final int rowDetailIndex = indexDetail + finalIncrement + numberOfRowHeader;
                    BiologyOnReproductionDetail detail = reproduction.getDataDetailReproduksi().get(indexDetail);

                    Row rowDetail = (rowIndex == rowDetailIndex) ? row : sheet.createRow(rowDetailIndex);

                    boolean lastRowOnDetail = (indexDetail == detailLength - 1);

                    // set garis bawah untuk akhir tiap data
                    if (lastRowOnDetail) {
                        for (int i = 0; i <= (startCell + 11); i++)
                            ExcelCell.builder().row(rowDetail).cellIndex(i).style(bottomBorderStyle).bottomBorder(true).build().generate();

                    }

                    CellStyle tempBottomStyleOrStandardStyle = (lastRowOnDetail) ? bottomBorderStyle : standardStyle;

                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 12).value(conventionValue(indexDetail + 1)).build().generate(); // penomoran
                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 13).value(conventionValue(detail.getPanjang())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 14).value(conventionValue(detail.getTipePanjang())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 15).value(conventionValue(detail.getBerat())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 16).value(conventionValue(detail.getJenisKelamin())).build().generate();
                    ExcelCell.builder().row(rowDetail).style(tempBottomStyleOrStandardStyle).bottomBorder(lastRowOnDetail).cellIndex(startCell + 17).value(conventionValue(detail.getTkg())).build().generate();
                    ExcelCell.builder().row(rowDetail).style((lastRowOnDetail) ? bottomBorderAndRightBorderStyle : rightBorderStyle).rightBorder(true).bottomBorder(lastRowOnDetail).cellIndex(startCell + 18).value(conventionValue(detail.getBeratIsiPerut())).build().generate();


                });




            });

        }

    }


    public void mergeCells(Sheet sheet) {
        for (int i = 0; i <= 13; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 15)); // Ukuran Detail

        for (int i = 16; i <= 19; i++)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
    }

}