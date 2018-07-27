/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import Entidades.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Dany
 */
public class Insertar {
     private PreparedStatement pInsertar;
     private Statement mStatement;
     private  Connection mConexion;
     
    public void insertarProyecto(Proyectos p) throws SQLException{
        mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "");
        mStatement = mConexion.createStatement();
        
        pInsertar = mConexion.prepareStatement("INSERT INTO proyectos (`precio_kwh`, `proyecto_ne`, `cotizaciones_id`)"+"values(?,?,?)");
        pInsertar.setString(1, String.valueOf(p.getPreciok()));
        pInsertar.setString(2, String.valueOf(p.isProyecto()));
        pInsertar.setString(3, String.valueOf(p.getCotizacion()));
        
        pInsertar.executeUpdate();
        
    }
    public void insertarPedido(Pedido p)throws SQLException{
        mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "");
        mStatement = mConexion.createStatement();
        
        pInsertar = mConexion.prepareStatement("INSERT INTO `pedido` (`cantidad`, `luminaria_id`)"+"values(?,?)");
        pInsertar.setString(1, String.valueOf(p.getCantidad()));
        pInsertar.setString(2, String.valueOf(p.getLuminaria_id()));
        
        pInsertar.executeUpdate();
        
    }
    public void insertarCot(Cotizacion p)throws SQLException{
        mConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/novaelectric", "root", "");
        mStatement = mConexion.createStatement();
        
        pInsertar = mConexion.prepareStatement("INSERT INTO `cotizaciones` (`fecha_inspeccion`, `watz`, `mun_poblacion_id`, `pedido_id`)"+"values(?,?,?,?)");
        pInsertar.setString(1, String.valueOf(p.getFecha_inspeccion()));
        pInsertar.setString(2, String.valueOf(p.getWatz()));
        pInsertar.setString(3, String.valueOf(p.getMun_pob_id()));
        pInsertar.setString(4, String.valueOf(p.getPedido_id()));
        
        pInsertar.executeUpdate();
        
    }
}

