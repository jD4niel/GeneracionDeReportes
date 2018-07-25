/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jdaniel
 */
public class Excel {

    public static void main(String[] args) {
        crearExcel();
    }

    private static void crearExcel() {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("NombreDeLaHoja");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("valor de la celda");
        row.createCell(1).setCellValue(5);
        row.createCell(2).setCellValue(true);

        Cell celda = row.createCell(3); //para agregarle mas atributos a la celda
        celda.setCellFormula(String.format("1+1", ""));

        Row rowUno = sheet.createRow(1);
        rowUno.createCell(0).setCellValue(8);
        rowUno.createCell(1).setCellValue(5);

        Cell celdados = row.createCell(2); //otra celda
        celdados.setCellFormula(String.format("A%d+B%d", 2, 2));

        try {
            FileOutputStream fileout = new FileOutputStream("Excel.xlsx");
            book.write(fileout);
            fileout.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
