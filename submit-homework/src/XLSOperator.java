import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by inksmallfrog on 10/2/15.
 */
//用于操作XLS
public class XLSOperator{
    private String fileName;

    XLSOperator(){
        fileName = "scoreChart.xlsx";
    }
    XLSOperator(String file_name){
        fileName = file_name;
    }

    void XLSExport(JTable table){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream out = new FileOutputStream(new File(fileName));
            XSSFSheet sheet = workbook.createSheet("Score");

            CellStyle style_head = workbook.createCellStyle();
            style_head.setAlignment(CellStyle.ALIGN_CENTER);

            CellStyle style_red = workbook.createCellStyle();
            Font font_red = workbook.createFont();
            font_red.setColor(IndexedColors.RED.getIndex());
            style_red.setFont(font_red);

            CellStyle style_gray = workbook.createCellStyle();
            Font font_gray = workbook.createFont();
            font_gray.setColor(IndexedColors.GREY_40_PERCENT.getIndex());
            font_gray.setItalic(true);
            style_gray.setFont(font_gray);

            for(int i = 0; i < table.getRowCount(); ++i){
                XSSFRow row = sheet.createRow(i);
                if(i == 0){
                    row.setRowStyle(style_head);
                }
                for(int j = 0; j < table.getColumnCount(); ++j){
                    XSSFCell cell = row.createCell(j);
                    Object data = table.getValueAt(i, j);
                    if(data != null) {
                        if(i != 0 && j == 9){
                            float score = Float.parseFloat(data.toString());
                            cell.setCellValue(score);
                            if(score < 60.0){
                                row.setRowStyle(style_red);
                            }
                        }
                        else{
                            cell.setCellValue(data.toString());
                        }
                    }
                    else if(j == 9 && i < table.getRowCount() - 2){
                        row.setRowStyle(style_gray);
                    }
                }
            }

            sheet.setColumnWidth(0, 4500);
            sheet.setColumnWidth(1, 4500);
            sheet.setColumnWidth(2, 3072);
            sheet.setColumnWidth(4, 4500);
            sheet.setColumnWidth(5, 4500);

            workbook.write(out);
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}