/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WriteException;

/**
 *
 * @author Dany
 */
public class GenerarExcel {
    public static Generar g = new Generar();
    public static void main(String[] args){
        String [][] entrada = new String[2][7];
        entrada[1][1]="CANTIDAD";
        entrada[1][2]="UNIDAD";
        entrada[1][3]="DESCRIPCION";
        entrada[1][4]="MARCA";
        entrada[1][5]="PRECIO";
        entrada[1][6]="TOTAL";
        String ruta="jovani.xls";
        g.generarExcel(entrada, ruta);
    }
}
