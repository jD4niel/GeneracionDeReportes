/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Dany
 */
public class Cotizacion {
 private String fecha_inspeccion;
 private int watz,mun_pob_id,pedido_id,proyecto_ne;

    public Cotizacion() {
    }

    public Cotizacion(String fecha_inspeccion, int watz, int mun_pob_id, int pedido_id, int proyecto_ne) {
        this.fecha_inspeccion = fecha_inspeccion;
        this.watz = watz;
        this.mun_pob_id = mun_pob_id;
        this.pedido_id = pedido_id;
        this.proyecto_ne = proyecto_ne;
    }

    public String getFecha_inspeccion() {
        return fecha_inspeccion;
    }

    public void setFecha_inspeccion(String fecha_inspeccion) {
        this.fecha_inspeccion = fecha_inspeccion;
    }

    public int getWatz() {
        return watz;
    }

    public void setWatz(int watz) {
        this.watz = watz;
    }

    public int getMun_pob_id() {
        return mun_pob_id;
    }

    public void setMun_pob_id(int mun_pob_id) {
        this.mun_pob_id = mun_pob_id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public int getProyecto_ne() {
        return proyecto_ne;
    }

    public void setProyecto_ne(int proyecto_ne) {
        this.proyecto_ne = proyecto_ne;
    }

    
 
 
}
