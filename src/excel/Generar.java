/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.*;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Dany
 */
public class Generar {
    public void generarExcel(String [][] entrada,String ruta){
        try {
            WorkbookSettings conf=new WorkbookSettings();
            conf.setEncoding("ISO-8859-1");
            WritableWorkbook workBook = Workbook.createWorkbook(new File(ruta),conf);
            WritableSheet sheet = workBook.createSheet("hoja1", 0);//hoja, posicion
            sheet.setColumnView(14, 15);
            
            
            WritableFont h = new WritableFont(WritableFont.ARIAL,11,WritableFont.BOLD);
            h.setColour(Colour.WHITE);
            
            WritableCellFormat hformat = new WritableCellFormat(h);
            
            hformat.setBackground(Colour.GREEN);
            hformat.setBorder(Border.ALL, BorderLineStyle.THIN);
            //hformat.setWrap(true);
            
            for (int i = 1; i < entrada.length; i++) {
                for (int j =1; j < entrada[i].length; j++) {
                    sheet.addCell(new jxl.write.Label(j, i, entrada[i][j],hformat));
                   
                }
            }
            workBook.write();
            workBook.close();
        } catch (IOException ex) {
            Logger.getLogger(Generar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Generar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
