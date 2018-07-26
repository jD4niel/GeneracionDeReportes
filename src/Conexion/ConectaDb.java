/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.*;

/**
 *
 * @author jdaniel
 */
public class ConectaDb {

    public static void main(String[] args) {
        try {
            Connection mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "");
            Statement mStatement = mConexion.createStatement();
            ResultSet mResulSet = mStatement.executeQuery("SELECT * FROM municipios");

            while (mResulSet.next()) {
                System.out.println(mResulSet.getString("municipio"));
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: ");
            e.printStackTrace();
        }
    }

}
