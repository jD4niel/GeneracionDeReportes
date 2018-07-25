/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Entidades.Pedido;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;

/**
 *
 * @author jdaniel
 */
public class Consulta {

    public Connection getConnection() {
        Connection mConexion = null;
        try {
            mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "pass");
            Statement mStatement = mConexion.createStatement();
            ResultSet mResulSet = mStatement.executeQuery("SELECT * FROM estados");

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mConexion;
    }

    public ArrayList<Pedido> getData(int capacidad_id) {

        ArrayList<Pedido> list = new ArrayList<Pedido>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM luminarias INNER JOIN capacidad_lum ON luminarias.id_capacidad=capacidad_lum.id WHERE capacidad_lum.id=" + capacidad_id);

            Pedido ped;
            while (rs.next()) {
                ped = new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cantidad"),
                        rs.getInt("luminaria_id")
                );
                list.add(ped);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

}
