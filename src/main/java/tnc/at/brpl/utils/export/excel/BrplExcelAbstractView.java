package tnc.at.brpl.utils.export.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class BrplExcelAbstractView extends AbstractXlsxView {

    public final int onePixel = 37;

    /**
     *
     */
    public Workbook workbook;


    /**
     *
     * @param map
     * @param workbook
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    }

    /**
     * Instance ctyle cell yang baru
     * @return
     */
    public CellStyle createStyle() {
        return this.workbook.createCellStyle();
    }


}
