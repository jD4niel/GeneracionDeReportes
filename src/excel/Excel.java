/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jdaniel
 */
public class Excel {

    public static void main(String[] args) throws IOException {
        modificarExcel();
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
    private static void modificarExcel() throws IOException{
       
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File("plantillaExcel.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            XSSFRow b4 =sheet.getRow(3);
            if(b4==null){b4=sheet.createRow(3);}
            XSSFCell celda = b4.createCell(1);
            if(celda==null){celda=b4.createCell(1);}
            celda.setCellValue("B4");
            
            
            XSSFRow b5 =sheet.getRow(4);
            if(b5==null){b5=sheet.createRow(4);}
            XSSFCell celda2 = b5.createCell(1);
            if(celda2==null){celda2=b5.createCell(1);}
            celda2.setCellValue("B5");  

            XSSFRow b6 =sheet.getRow(5);
            if(b6==null){b6=sheet.createRow(5);}
            XSSFCell celda3 = b6.createCell(1);
            if(celda3==null){celda3=b6.createCell(1);}
            celda3.setCellValue("B6");
            

            XSSFRow b7 =sheet.getRow(6);
            if(b7==null){b7=sheet.createRow(6);}
            XSSFCell celda4 = b7.createCell(1);
            if(celda4==null){celda4=b7.createCell(1);}
            celda4.setCellValue("B7");

            XSSFRow b8 =sheet.getRow(7);
            if(b8==null){b8=sheet.createRow(7);}
            XSSFCell celda5 = b8.createCell(1);
            if(celda5==null){celda5=b8.createCell(1);}
            celda5.setCellValue("B8");
            

            XSSFRow b9 =sheet.getRow(8);
            if(b9==null){b9=sheet.createRow(8);}
            XSSFCell celda6 = b9.createCell(1);
            if(celda6==null){celda6=b9.createCell(1);}
            celda6.setCellValue("B9");


            XSSFRow b10 =sheet.getRow(9);
            if(b10==null){b10=sheet.createRow(9);}
            XSSFCell celda7 = b10.createCell(1);
            if(celda7==null){celda7=b10.createCell(1);}
            celda7.setCellValue("B10");

            XSSFRow b11 =sheet.getRow(10);
            if(b11==null){b11=sheet.createRow(10);}
            XSSFCell celda8 = b11.createCell(1);
            if(celda8==null){celda8=b11.createCell(1);}
            celda8.setCellValue("B11");


            XSSFRow b12 =sheet.getRow(11);
            if(b12==null){b12=sheet.createRow(11);}
            XSSFCell celda9 = b12.createCell(1);
            if(celda9==null){celda9=b12.createCell(1);}
            celda9.setCellValue("B12");
            
            
            
            
            file.close();
            FileOutputStream output = new FileOutputStream("daniel.xlsx");
            wb.write(output);
            output.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
