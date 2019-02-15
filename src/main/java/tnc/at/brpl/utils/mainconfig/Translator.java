package tnc.at.brpl.utils.mainconfig;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnc.at.brpl.utils.mainconfig.models.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public class Translator {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private DecimalFormat decimalFormat = new DecimalFormat("0");
    private List<String> errors = new ArrayList<>();

    public Translator() {
    }

    public List<String> getErrors() {
        return errors;
    }

    /**
     *
     * @param config
     * @param excelSheet
     * @return
     */
    public String translate(List<FieldModel> config, Sheet excelSheet) {
        return translate(config, excelSheet, null, 0, 0);
    }


    /**
     *
     * @param config
     * @param excelSheet
     * @param looping
     * @return
     */
    public String translate(List<FieldModel> config, Sheet excelSheet, FieldLooping looping) {
        return translate(config, excelSheet, looping, 0, 0);
    }


    private String fieldProcessing(StringBuilder resultJsonAsstring,
                                 List<FieldModel> config,
                                 int i,
                                 boolean hasValue,
                                 Sheet excelSheet,
                                 FieldLooping looping,
                                 int l,
                                 int inc, int inc2, int row, int column) {


        String empties = "";
        for (FieldModel fieldModel : config) {
            i++;
            hasValue = true;

            if ((fieldModel.isHasDirectValue() || fieldModel.isDirectValueGroup() || fieldModel.isHasChoiceValue())
                    && fieldModel.getValueType() != FieldValueType.OBJECT){
                Object value = null;
                if(fieldModel.isDirectValueGroup()) {
                    value = getExcelCellGroupValue(fieldModel, excelSheet, fieldModel.getDirectGroupValuesAt());
                } else if (fieldModel.isHasChoiceValue()) {
                    value = getExcelCellChoiceValue(fieldModel.getChoiceValuesAt(), excelSheet);
                } else {

                    int r = fieldModel.getDirectValueAt().getRow();
                    r += (fieldModel.isForRowLoop() ?
                            (inc2 + checkWithRowSpaces(looping, row + 1)) : 0);

                    int c = fieldModel.getDirectValueAt().getColumn();
                    c += (fieldModel.isForColumnLoop() ?
                            (inc + checkWithColumnSpaces(looping, column + 1)) : 0);

                    try {
                        value = getExcelCellValue(
                                fieldModel,
                                excelSheet
                                        .getRow(r) // penambahan kenaikan perulangan baris
                                        .getCell(c)  // penambahan kenaikan perulangan kolom
                        );


                        // jika tipe data uuid dan harus menggunakan nilai referensi uuid bersangkutan
                        if (fieldModel.isUseAsUuid() && fieldModel.isUseAsUuid()) {

                            if (fieldModel.getValueType() == FieldValueType.STRING) {
                                String param = String.valueOf(value);



                            }
                        }
                    } catch (Exception e) {
                        if (fieldModel.getValueType() == FieldValueType.STRING ||
                                fieldModel.getValueType() == FieldValueType.DATE ||
                                fieldModel.getValueType() == FieldValueType.TIME) {
                            value = "";
                        } else if (fieldModel.getValueType() == FieldValueType.INTEGER) {
                            value = 0;
                        } else if (fieldModel.getValueType() == FieldValueType.DOUBLE) {
                            value = 0.0;
                        } else if (fieldModel.getValueType() == FieldValueType.BOOLEAN) {
                            value = false;
                        }
                    }


                    // kondisi jika cell yang di periksa menghasilkan nilai error
                    if (String.valueOf(value).isEmpty()) { //
                        if (fieldModel.getValueType() == FieldValueType.INTEGER) {
                            value = 0;
                        } else if (fieldModel.getValueType() == FieldValueType.DOUBLE) {
                            value = 0.0;
                        }
                    }
                    
                }


                if (fieldModel.isHasExtraDirectValue()) {
                } else { // standard pengambilan nilai
                }


                // cek jika string value adalah dash
                if (fieldModel.getValueType() == FieldValueType.STRING ||
                        fieldModel.getValueType() == FieldValueType.DATE ||
                        fieldModel.getValueType() == FieldValueType.TIME) {
                    String tempValue = String.valueOf(value).replace(" ", "");
                    if (String.valueOf(value).equals("-"))
                    value = "";
                }

                empties += String.valueOf(value); // identifier apakah jika terjadi perulangan baris/ kolom ini kosong atau tidak
                resultJsonAsstring.append("\"").append(fieldModel.getJsonFieldName()).append("\"").append(":");
                resultJsonAsstring.append(value);
            } else if (fieldModel.getValueType() == FieldValueType.OBJECT) { // untuk data type object

                int spaceRow = checkWithRowSpaces(looping, row + 1);
                resultJsonAsstring.append("\"").append(fieldModel.getJsonFieldName()).append("\"").append(":");
                resultJsonAsstring.append("[");
                resultJsonAsstring.append(
                        translate(fieldModel.getObjectDetails(), excelSheet, fieldModel.getObjectDetailsLoop(), inc, inc2 + spaceRow)
                );
                resultJsonAsstring.append("]");
            } else {
                hasValue = false;
            }

            if (i < l && hasValue){
                resultJsonAsstring.append(",");
            }
        }


        return empties;
    }

    /**
     *
     * @param config
     * @param excelSheet
     * @param looping
     * @param up
     * @param up2
     * @return
     */
    public String translate(List<FieldModel> config, Sheet excelSheet, FieldLooping
            looping, int up, int up2) {

        if (config == null)
            return "";


        StringBuilder builder = new StringBuilder();

        int rowLoop = (looping != null && looping.isRowThrough()) ? looping.getRowLoop() : 1;
        int columnLoop = (looping != null && looping.isColumnThrough()) ? looping.getColumnLoop() : 1;
        int rowInc = (looping != null && looping.isRowThrough()) ? looping.getRowInc() : 0;
        int columnInc = (looping != null && looping.isColumnThrough()) ? looping.getColumnInc() : 0;

        int inc = up;
        String empties = "";
        String beforeValue = "";
        for (int column = 0; column < columnLoop; column++) { // perulangan yang kekanan

            int inc2 = up2;
            for (int row = 0; row < rowLoop; row++) { // perulangan yang kebawah

                StringBuilder resultJsonAsstring = new StringBuilder();
                if (!empties.isEmpty()){
                    resultJsonAsstring.append(",");
                }

                empties = "";
                resultJsonAsstring.append("{");
                int l = config.size();
                int i = 0;

                boolean hasValue = false;

                empties = fieldProcessing(resultJsonAsstring, config, i, hasValue, excelSheet, looping, l, inc, inc2, row, column);


                // hapus nilai boolean
                empties = empties
                        .replace("\"", "")
                        .replace("true", "")
                        .replace("false", "")
                        .replace("0.0", "")
                        .replace("0", "");

                if (!empties.isEmpty()) {
                    resultJsonAsstring.append("}");
                    builder.append(resultJsonAsstring.toString());
                    beforeValue = empties;
                } else {
                    empties = beforeValue;
                }

                inc2 += rowInc;
            }

            inc += columnInc;
        }

        return builder.toString();
    }

    /**
     *
     * @param fieldChoices
     * @param sheet
     * @return
     */
    private Object getExcelCellChoiceValue(List<FieldChoice> fieldChoices, Sheet sheet) {

        for (FieldChoice choice : fieldChoices) {
            if ((boolean) getExcelCellValue(FieldValueType.BOOLEAN, sheet.
                    getRow(choice.getChoiceSelector().getRow())
                    .getCell(choice.getChoiceSelector().getColumn()))){
                return getExcelCellValue(FieldValueType.STRING, sheet.
                        getRow(choice.getChoiceValueAt().getRow())
                        .getCell(choice.getChoiceValueAt().getColumn()));
            }
        }

        return "\"\"";
    }


    /**
     *
     * @param fieldModel
     * @param excelSheet
     * @param models
     * @return
     */
    private Object getExcelCellGroupValue(FieldModel fieldModel, Sheet excelSheet,  List<CellModel> models) {
        Object v = "";

        boolean isDate = fieldModel.getValueType() == FieldValueType.DATE;
        boolean isClock = fieldModel.getValueType() == FieldValueType.TIME;


        int l = models.size();
        int i = 0;
        for (CellModel model : models){
            i++;
            Object o = getValueAndReview(
                    excelSheet
                            .getRow(model.getRow()
                            ) // penambahan kenaikan perulangan baris
                            .getCell(model.getColumn()
                            )  // penambahan kenaikan perulangan kolom
            );

            Object temp = String.valueOf(o).isEmpty() ? 0 : decimalFormat.format(o);
            v += String.valueOf(temp);

            if (i < l) {
                v += fieldModel.getGroupValueConnector();
            }


            if (isDate && isDateHasNull(temp)) {
                if (i == 1) { // tanggal
                    this.errors.add("Tanggal belum anda inputkan untuk data " + fieldModel.getTitle());
                } else if (i == 2) { // bulan
                    this.errors.add("Bulan belum anda inputkan untuk data " + fieldModel.getTitle());
                } else if (i == 3) { // tahun
                    this.errors.add("Tahun belum anda inputkan untuk data " + fieldModel.getTitle());
                }
            }
        }

        return "\"" + v + "\"";
    }


    private boolean isDateHasNull(Object data) {
        return String.valueOf(data).equals("0") || String.valueOf(data).equals("0.0") || String.valueOf(data).equals("0.00");
    }


    private Object getExcelCellValue(FieldModel fieldModel, Cell cell) {
        return getExcelCellValue(fieldModel.getValueType(), cell);
    }


    /**
     *
     * @param fieldType
     * @param cell
     * @return
     */
    private Object getExcelCellValue(FieldValueType fieldType, Cell cell) {
        if (cell == null)
            return "\"\"";

        Object value = getValueAndReview(cell);
        switch (fieldType) {
            case DATE:
                return "\"" + value + "\"";
            case DOUBLE:
                if (String.valueOf(value).isEmpty())
                    return 0.00;
                else return Double.parseDouble(String.valueOf(value));
            case STRING:
                return "\"" + resolveStringValue(value).trim() + "\"";
            case BOOLEAN:
                return String.valueOf(value).toLowerCase().equals("v");
            case INTEGER:
                if (String.valueOf(value).isEmpty())
                    return 0;
                else return decimalFormat.format(value);
            default:
                return "\"\"";
        }

    }


    /**
     *
     * @param cell
     * @return
     */
    private Object getValueAndReview(Cell cell) {

        switch (cell.getCellType()) {

            case 0 : return cell.getNumericCellValue();
            case 1 : return cell.getStringCellValue();
            case 2 :
                switch (cell.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_NUMERIC: return cell.getNumericCellValue();
                    case Cell.CELL_TYPE_STRING: return cell.getStringCellValue();
                    case Cell.CELL_TYPE_FORMULA: return cell.getCellFormula();
                    case Cell.CELL_TYPE_ERROR: return "";
                    default: return cell.getStringCellValue();
                }
            case 3 : return "";
            case 4 : return cell.getBooleanCellValue();
            case 5 : return cell.getErrorCellValue();
            default: return "";
        }

    }


    /**
     *
     * @param value
     * @return
     */
    private String resolveStringValue(Object value) {
        try {
            return String.valueOf(decimalFormat.format(value));
        } catch (Exception e){
            return String.valueOf(value);
        }
    }


    /**
     *
     * @param looping
     * @param at
     * @return
     */
    private int checkWithRowSpaces(FieldLooping looping, int at) {
        if (looping != null && looping.getFieldSpaces() != null ){
            int v = 0;
            for (FieldSpace space: looping.getFieldSpaces()) {
                v += (at >= space.getRowLoopSpaceIn()) ? space.getRowLoopSpace() : 0;
            }
            return v;
        } else {
            return 0;
        }
    }


    /**
     *
     * @param looping
     * @param at
     * @return
     */
    private int checkWithColumnSpaces(FieldLooping looping, int at) {

        if (looping != null && looping.getFieldSpaces() != null ){
            int v = 0;
            for (FieldSpace space: looping.getFieldSpaces()) {
                v += (at >= space.getColumnLoopSpaceIn()) ? space.getColumnLoopSpace() : 0;
            }
            return v;
        } else {
            return 0;
        }
    }




}
