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
            mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "");
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
    
    public int caraVeintiCinco(String estado,String municipio,String poblacion,int tipo){
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT luminarias.nombre,poblacion.nombre, SUM((pedido.cantidad*cotizaciones.watz)*0.25) FROM cotizaciones \n" +
            "INNER JOIN pedido ON pedido.id=cotizaciones.pedido_id\n" +
            "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n" +
            "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n" +
            "INNER JOIN poblacion on poblacion.id=municipio_poblacion.poblacion_id\n" +
            "INNER JOIN municipios on municipios.id=municipio_poblacion.municipio_id\n" +
            "INNER JOIN estados_municipios on estados_municipios.municipios_id=municipios.id\n" +
            "INNER JOIN estados on estados.id=estados_municipios.estados_id\n" +
            "WHERE poblacion.nombre='"+poblacion+"' and municipios.municipio='"+municipio+"' and estados.estado='"+estado+"' and NOT luminarias.id=(1 OR 2 OR 3) and cotizaciones.proyecto_ne="+tipo +
            " GROUP BY luminarias.nombre,poblacion.nombre");
            if (rs.next()) {
                valor = Integer.parseInt(rs.getString("SUM((pedido.cantidad*cotizaciones.watz)*0.25)"));
                return valor;
            } else {
                return 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public int cargaWatz(String estado,String municipio,String poblacion,int tipo){
       try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT luminarias.nombre,poblacion.nombre, SUM(pedido.cantidad*cotizaciones.watz) FROM cotizaciones \n" +
                "INNER JOIN pedido ON pedido.id=cotizaciones.pedido_id\n" +
                "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n" +
                "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n" +
                "INNER JOIN poblacion on poblacion.id=municipio_poblacion.poblacion_id\n" +
                "INNER JOIN municipios on municipios.id=municipio_poblacion.municipio_id\n" +
                "INNER JOIN estados_municipios on estados_municipios.municipios_id=municipios.id\n" +
                "INNER JOIN estados on estados.id=estados_municipios.estados_id \n" +
                "WHERE poblacion.nombre='"+poblacion+"' and municipios.municipio='"+municipio+"' and estados.estado='"+estado+"' and cotizaciones.proyecto_ne="+tipo +
                " GROUP BY luminarias.nombre,poblacion.nombre");
                if (rs.next()) {
                valor = Integer.parseInt(rs.getString("SUM(pedido.cantidad*cotizaciones.watz)"));
                return valor;
            } else {
                return 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public float totalLuminarias(String estado,String municipio,String poblacion,int tipo){
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            float valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT SUM(pedido.cantidad) as suma FROM cotizaciones \n" +
            "INNER JOIN pedido ON pedido.id=cotizaciones.pedido_id\n" +
            "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n" +
            "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n" +
            "INNER JOIN poblacion on poblacion.id=municipio_poblacion.poblacion_id\n" +
            "INNER JOIN municipios on municipios.id=municipio_poblacion.municipio_id\n" +
            "INNER JOIN estados_municipios on estados_municipios.municipios_id=municipios.id\n" +
            "INNER JOIN estados on estados.id=estados_municipios.estados_id \n" +
            "WHERE poblacion.nombre='"+poblacion+"' and municipios.municipio='"+municipio+"' and estados.estado='"+estado+"' and cotizaciones.proyecto_ne="+tipo);
            if (rs.next()) {
                valor = Float.parseFloat(rs.getString("suma"));
                System.out.println(valor);
                return valor;
            } else {
                return 1;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
