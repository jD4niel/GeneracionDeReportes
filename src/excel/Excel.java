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
import java.util.ArrayList;
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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 *
 * @author jdaniel
 */
public class Excel {

    public static void main(String[] args) throws IOException {
      
    }

    private static void crearExcel() {
       // Workbook book = new XSSFWorkbook();
       // Sheet sheet = book.createSheet("NombreDeLaHoja");

       // Row row = sheet.createRow(0);
       // row.createCell(0).setCellValue("valor de la celda");
       // row.createCell(1).setCellValue(5);
       // row.createCell(2).setCellValue(true);

       // Cell celda = row.createCell(3); //para agregarle mas atributos a la celda
       // celda.setCellFormula(String.format("1+1", ""));

       // Row rowUno = sheet.createRow(1);
       // rowUno.createCell(0).setCellValue(8);
       // rowUno.createCell(1).setCellValue(5);
//
       // Cell celdados = row.createCell(2); //otra celda
       // celdados.setCellFormula(String.format("A%d+B%d", 2, 2));

      //  try {
       //     FileOutputStream fileout = new FileOutputStream("Excel.xlsx");
      //      book.write(fileout);
      //      fileout.close();
       // } catch (FileNotFoundException ex) {
        //    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
       // } catch (IOException ex) {
      //      Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
      //  }
   // }
   // private static void modificarExcel() throws IOException{
       
    //    FileInputStream file = null;
    //    try {
     //       file = new FileInputStream(new File("plantillaExcel.xlsx"));
      //      XSSFWorkbook wb = new XSSFWorkbook(file);
      //      XSSFSheet sheet = wb.getSheetAt(0);
            
      //      XSSFRow b4 =sheet.getRow(3);
      //      if(b4==null){b4=sheet.createRow(3);}
      //      XSSFCell celda = b4.createCell(1);
       //     if(celda==null){celda=b4.createCell(1);}
       //     celda.setCellValue("B4");
            
            
       ///     XSSFRow b5 =sheet.getRow(4);
       //     if(b5==null){b5=sheet.createRow(4);}
       //     XSSFCell celda2 = b5.createCell(1);
       //     if(celda2==null){celda2=b5.createCell(1);}
       //     celda2.setCellValue("B5");  
//
       //     XSSFRow b6 =sheet.getRow(5);
       //     if(b6==null){b6=sheet.createRow(5);}
       //     XSSFCell celda3 = b6.createCell(1);
       //     if(celda3==null){celda3=b6.createCell(1);}
       //     celda3.setCellValue("B6");
       //     

         //   XSSFRow b7 =sheet.getRow(6);
        //    if(b7==null){b7=sheet.createRow(6);}
        //    XSSFCell celda4 = b7.createCell(1);
        //    if(celda4==null){celda4=b7.createCell(1);}
        ///    celda4.setCellValue("B7");

        //    XSSFRow b8 =sheet.getRow(7);
        //    if(b8==null){b8=sheet.createRow(7);}
        //    XSSFCell celda5 = b8.createCell(1);
        //    if(celda5==null){celda5=b8.createCell(1);}
        //    celda5.setCellValue("B8");
            

         //   XSSFRow b9 =sheet.getRow(8);
         //   if(b9==null){b9=sheet.createRow(8);}
         //   XSSFCell celda6 = b9.createCell(1);
         //   if(celda6==null){celda6=b9.createCell(1);}
         //   celda6.setCellValue("B9");


           // XSSFRow b10 =sheet.getRow(9);
          //  if(b10==null){b10=sheet.createRow(9);}
          //  XSSFCell celda7 = b10.createCell(1);
         //   if(celda7==null){celda7=b10.createCell(1);}
         //   celda7.setCellValue("B10");
//
         //   XSSFRow b11 =sheet.getRow(10);
         //   if(b11==null){b11=sheet.createRow(10);}
          //  XSSFCell celda8 = b11.createCell(1);
          //  if(celda8==null){celda8=b11.createCell(1);}
          //  celda8.setCellValue("B11");


          //  XSSFRow b12 =sheet.getRow(11);
          //  if(b12==null){b12=sheet.createRow(11);}
          //  XSSFCell celda9 = b12.createCell(1);
          //  if(celda9==null){celda9=b12.createCell(1);}
          //  celda9.setCellValue("B12");
            
            
            
          //  
           // file.close();
          //  FileOutputStream output = new FileOutputStream("daniel.xlsx");
           // wb.write(output);
          //  output.close();
            
            
       // } catch (FileNotFoundException ex) {
        //    Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
       // } 
        
    }
    
    public void modificarPlantillaUno(String[][] datos){
        int columnas = 0, filas = 0;
        int filaInicial = 11;
        try {
            FileInputStream file = new FileInputStream(new File("plantilla1.xlsx"));
            
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            XSSFCellStyle bodyStyle = wb.createCellStyle();
            bodyStyle.setWrapText(true);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            
            
            columnas = datos[0].length;
            for(int i = 0; i < datos.length; ++i){ 
                filas = i;
            }
            System.out.println((filas + 1)+ " - " + columnas);
            
            for(int y = 0; y <= filas; y++){
                XSSFRow fila = sheet.getRow(filaInicial);
                fila.setHeight((short)-1);
                if(fila == null) {
                    fila = sheet.createRow(filaInicial);
                    fila.setHeight((short)-1);
                }
                for(int x = 0; x <columnas; x++){
                    XSSFCell celda =fila.createCell(x);
                    celda.setCellStyle(bodyStyle);
                    if(celda == null) {
                        celda = fila.createCell(x);
                    }
                    celda.setCellValue(datos[y][x]);
                }
                filaInicial++;
            }

            file.close();
            
            FileOutputStream output = new FileOutputStream("nuevo2.xlsx");
            wb.write(output);
            output.close();
                    
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarPlantillaDos(ArrayList datos, String estado){
        String[] datosArr = new String[datos.size()];
        datosArr = (String[]) datos.toArray(datosArr);
        try {
            FileInputStream file = new FileInputStream(new File("Plantilla2.xlsx"));
            
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);
            
            XSSFCellStyle bodyStyle = wb.createCellStyle();
            
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setDataFormat((short) 7);
            
            XSSFCellStyle stringStyle = wb.createCellStyle();
            
            stringStyle.setBorderBottom(BorderStyle.THIN);
            stringStyle.setBorderTop(BorderStyle.THIN);
            stringStyle.setBorderLeft(BorderStyle.THIN);
            stringStyle.setBorderRight(BorderStyle.THIN);
            
            XSSFRow fila = sheet.getRow(19);
            
            XSSFCell celdaMunicipio = fila.createCell(0);
            celdaMunicipio.setCellValue(datosArr[0]);
            celdaMunicipio.setCellStyle(stringStyle);
            
            XSSFCell celdaLuminarias = fila.createCell(1);
            celdaLuminarias.setCellValue(datosArr[1]);
            celdaLuminarias.setCellStyle(stringStyle);
            
            XSSFCell celdaMeses = fila.createCell(9);
            celdaMeses.setCellValue(datosArr[9]);
            celdaMeses.setCellStyle(stringStyle);
           
            for(int i = 2; i < 9; i++){
                XSSFCell celda = fila.createCell(i);
                celda.setCellValue(Double.parseDouble(datosArr[i]));
                celda.setCellStyle(bodyStyle);
            }
            
            // Opcion Pago 1
            double op1PagoFinal, op1PagoInicial, op1MensualidadFinal;
            
            op1PagoFinal = Double.parseDouble(datosArr[8]);
            op1PagoInicial = op1PagoFinal * 0.5;
            op1MensualidadFinal = op1PagoFinal - (op1PagoInicial + (Double.parseDouble(datosArr[6]) * 4));
            XSSFRow filaOp1Total = sheet.getRow(43);
            XSSFCell celdaOp1CostoTotal = filaOp1Total.createCell(6);
            celdaOp1CostoTotal.setCellValue(op1PagoFinal);
            celdaOp1CostoTotal.setCellStyle(bodyStyle);
            
            XSSFRow filaOp1PagoInicial = sheet.getRow(46);
            XSSFCell celdaOp1PagoInicial = filaOp1PagoInicial.createCell(6);
            celdaOp1PagoInicial.setCellValue(op1PagoInicial);
            celdaOp1PagoInicial.setCellStyle(bodyStyle);
            
            for(int op1 = 47; op1 < 51; op1++){
                XSSFRow filaOp1PagoMensual = sheet.getRow(op1);
                XSSFCell celdaOp1PagoMensual = filaOp1PagoMensual.createCell(6);
                celdaOp1PagoMensual.setCellValue(Double.parseDouble(datosArr[6]));
                celdaOp1PagoMensual.setCellStyle(bodyStyle);
            }
            
            XSSFRow filaOp1PagoMensualFinal = sheet.getRow(51);
            XSSFCell celdaOp1PagoMensualFinal = filaOp1PagoMensualFinal.createCell(6);
            celdaOp1PagoMensualFinal.setCellValue(op1MensualidadFinal);
            celdaOp1PagoMensualFinal.setCellStyle(bodyStyle);
            
            // Opcion Pago 2
            
            double op2PagoFinal, op2PagoInicial, op2MensualidadFinal;
            
            op2PagoFinal = Double.parseDouble(datosArr[6]) + Double.parseDouble(datosArr[8]);
            op2PagoInicial = op2PagoFinal  * 0.3;
            op2MensualidadFinal = op2PagoFinal - (op2PagoInicial + (Double.parseDouble(datosArr[6]) * 6));
            
            XSSFRow filaOp2Total = sheet.getRow(54);
            XSSFCell celdaOp2CostoTotal = filaOp2Total.createCell(6);
            celdaOp2CostoTotal.setCellValue(Double.parseDouble(datosArr[8]));
            celdaOp2CostoTotal.setCellStyle(bodyStyle);
            
            XSSFRow filaOp2Financiamiento = sheet.getRow(55);
            XSSFCell celdaOp2Financiamiento = filaOp2Financiamiento.createCell(6);
            celdaOp2Financiamiento.setCellValue(Double.parseDouble(datosArr[6]));
            celdaOp2Financiamiento.setCellStyle(bodyStyle);
            
            XSSFRow filaOp2Final = sheet.getRow(56);
            XSSFCell celdaOp2Final = filaOp2Final.createCell(6);
            celdaOp2Final.setCellValue(op2PagoFinal);
            celdaOp2Final.setCellStyle(bodyStyle);
            
            XSSFRow filaOp2PagoInicial = sheet.getRow(58);
            XSSFCell celdaOp2PagoInicial = filaOp2PagoInicial.createCell(6);
            celdaOp2PagoInicial.setCellValue(op2PagoInicial);
            celdaOp2PagoInicial.setCellStyle(bodyStyle);
            
            for(int op2 = 59; op2 < 65; op2++){
                XSSFRow filaOp2PagoMensual = sheet.getRow(op2);
                XSSFCell celdaOp2PagoMensual = filaOp2PagoMensual.createCell(6);
                celdaOp2PagoMensual.setCellValue(Double.parseDouble(datosArr[6]));
                celdaOp2PagoMensual.setCellStyle(bodyStyle);
            }
            
            XSSFRow filaOp2PagoMensualFinal = sheet.getRow(65);
            XSSFCell celdaOp2PagoMensualFinal = filaOp2PagoMensualFinal.createCell(6);
            celdaOp2PagoMensualFinal.setCellValue(op2MensualidadFinal);
            celdaOp2PagoMensualFinal.setCellStyle(bodyStyle);
            
            // Opcion Pago 3
            
            double op3PagoFinal, op3MensualidadFinal;
            
            op3PagoFinal = (Double.parseDouble(datosArr[6]) * 3) + Double.parseDouble(datosArr[8]);
            op3MensualidadFinal = op3PagoFinal - (Double.parseDouble(datosArr[6]) * 11);
            
            XSSFRow filaOp3Total = sheet.getRow(9);
            XSSFCell celdaOp3CostoTotal = filaOp3Total.createCell(16);
            celdaOp3CostoTotal.setCellValue(Double.parseDouble(datosArr[8]));
            celdaOp3CostoTotal.setCellStyle(bodyStyle);
            
            XSSFRow filaOp3Financiamiento = sheet.getRow(10);
            XSSFCell celdaOp3Financiamiento = filaOp3Financiamiento.createCell(16);
            celdaOp3Financiamiento.setCellValue(Double.parseDouble(datosArr[6]) * 3);
            celdaOp3Financiamiento.setCellStyle(bodyStyle);
            
            XSSFRow filaOp3Final = sheet.getRow(11);
            XSSFCell celdaOp3Final = filaOp3Final.createCell(16);
            celdaOp3Final.setCellValue(op3PagoFinal);
            celdaOp3Final.setCellStyle(bodyStyle);
            
            for(int op3 = 14; op3 < 25; op3++){
                XSSFRow filaOp3PagoMensual = sheet.getRow(op3);
                XSSFCell celdaOp3PagoMensual = filaOp3PagoMensual.createCell(16);
                celdaOp3PagoMensual.setCellValue(Double.parseDouble(datosArr[6]));
                celdaOp3PagoMensual.setCellStyle(bodyStyle);
            }
            
            XSSFRow filaOp3PagoMensualFinal = sheet.getRow(25);
            XSSFCell celdaOp3PagoMensualFinal = filaOp3PagoMensualFinal.createCell(16);
            celdaOp3PagoMensualFinal.setCellValue(op3MensualidadFinal);
            celdaOp3PagoMensualFinal.setCellStyle(bodyStyle);
        
            file.close();
            
            int numero = (int) (Math.random() * 9999) + 1;
            
            File folder = new File(estado);
            
            if (folder.isDirectory()) { 
                FileOutputStream output = new FileOutputStream(estado + "\\\\" + estado + numero + ".xlsx");
                wb.write(output);
                output.close();
            }else{
                folder.mkdirs();
                FileOutputStream output = new FileOutputStream(estado + "\\\\" + estado + numero + ".xlsx");
                wb.write(output);
                output.close();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
