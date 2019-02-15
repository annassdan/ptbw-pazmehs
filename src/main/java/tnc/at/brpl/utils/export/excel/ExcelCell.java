package tnc.at.brpl.utils.export.excel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import tnc.at.brpl.utils.Brpl;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelCell {

    @Builder.Default
    private int whiteSpace = 0; // jumlah spasi yang akan diberi diawal pada nilai STRING saja

    private Row row;

    private int cellIndex;

    @Builder.Default
    private Object value = null;

    private CellStyle style;

    @Builder.Default
    private boolean wrap = true;

    @Builder.Default
    private HorizontalAlignment align = HorizontalAlignment.CENTER;

    @Builder.Default
    private VerticalAlignment valign = VerticalAlignment.CENTER;

    private boolean bold;

    private Font font;

    private CellType type;

    @Builder.Default
    private BorderStyle border = BorderStyle.THIN;

    @Builder.Default
    private boolean allBorder = false;

    @Builder.Default
    private boolean leftBorder = false;

    @Builder.Default
    private boolean rightBorder = false;

    @Builder.Default
    private boolean topBorder = false;

    @Builder.Default
    private boolean bottomBorder = false;

    @Builder.Default
    private FillPatternType filPattern = FillPatternType.SOLID_FOREGROUND;

    @Builder.Default
    private short color = -1;

    @Builder.Default
    private short fontColor = IndexedColors.BLACK.getIndex();

    @Builder.Default
    private short borderColor = IndexedColors.BLACK.getIndex();



    /**
     *
     */
    public void generate() {
        Cell cell;


        if (getType() != null) {
            cell = getRow().createCell(getCellIndex(), getType());
        } else {
            cell = getRow().createCell(getCellIndex());
        }

        if (getStyle() != null) {
            if (getFont() != null) {
                getFont().setBold(isBold());
                getStyle().setFont(getFont());
            }

            if (isAllBorder()) {
                getStyle().setBorderLeft(getBorder());
                getStyle().setLeftBorderColor(getBorderColor());

                getStyle().setBorderRight(getBorder());
                getStyle().setRightBorderColor(getBorderColor());

                getStyle().setBorderTop(getBorder());
                getStyle().setTopBorderColor(getBorderColor());

                getStyle().setBorderBottom(getBorder());
                getStyle().setBottomBorderColor(getBorderColor());
            } else {
                if (isLeftBorder()) {
                    getStyle().setBorderLeft(getBorder());
                    getStyle().setLeftBorderColor(getBorderColor());
                }
                if (isRightBorder()) {
                    getStyle().setBorderRight(getBorder());
                    getStyle().setRightBorderColor(getBorderColor());
                }
                if (isTopBorder()) {
                    getStyle().setBorderTop(getBorder());
                    getStyle().setTopBorderColor(getBorderColor());
                }
                if (isBottomBorder()) {
                    getStyle().setBorderBottom(getBorder());
                    getStyle().setBottomBorderColor(getBorderColor());
                }
            }

            getStyle().setAlignment(getAlign());
            getStyle().setVerticalAlignment(getValign());
            getStyle().setWrapText(isWrap());

            if (getColor() != -1) {
                getStyle().setFillPattern(getFilPattern());
                getStyle().setFillForegroundColor(getColor());
            }




            cell.setCellStyle(getStyle());

        }


        positioningValue(cell); // set value

    }


    /**
     * @param cell
     */
    private void positioningValue(Cell cell) {
        if (getValue() == null) {
        } else if (getValue() instanceof String) {
            String temp  = (String) getValue();
            if (getWhiteSpace() > 0) {
                String s = "";
                for (int i = 0; i < getWhiteSpace(); i++)
                    s += " ";
                temp = s + temp;
            }
            cell.setCellValue(temp);
        } else if (getValue() instanceof Double) {
            cell.setCellValue((double) getValue());
        } else if (getValue() instanceof Integer) {
            cell.setCellValue((int) getValue());
        } else if (getValue() instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat(Brpl.DATE_PATTERN);
            String formattedDate = formatter.format(getValue());
            cell.setCellValue(formattedDate);
        } else {
            cell.setCellValue((String.valueOf(getValue()).equals("true") ? "Ya" : "Tidak"));
        }
    }
}
