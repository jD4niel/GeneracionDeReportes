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
public class Proyectos {
    private int id,cotizacion;
    private float preciok;
    private float carga_watz,carga_25,total_carga,consumo_promedio,total_lamparas;

    public Proyectos() {
    }

    public Proyectos(int cotizacion, float preciok,  float carga_watz, float carga_25, float total_carga, float consumo_promedio, float total_lamparas) {
        this.cotizacion = cotizacion;
        this.preciok = preciok;
        this.carga_watz = carga_watz;
        this.carga_25 = carga_25;
        this.total_carga = total_carga;
        this.consumo_promedio = consumo_promedio;
        this.total_lamparas = total_lamparas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(int cotizacion) {
        this.cotizacion = cotizacion;
    }

    public float getPreciok() {
        return preciok;
    }

    public void setPreciok(float preciok) {
        this.preciok = preciok;
    }


    public float getCarga_watz() {
        return carga_watz;
    }

    public void setCarga_watz(float carga_watz) {
        this.carga_watz = carga_watz;
    }

    public float getCarga_25() {
        return carga_25;
    }

    public void setCarga_25(float carga_25) {
        this.carga_25 = carga_25;
    }

    public float getTotal_carga() {
        return total_carga;
    }

    public void setTotal_carga(float total_carga) {
        this.total_carga = total_carga;
    }

    public float getConsumo_promedio() {
        return consumo_promedio;
    }

    public void setConsumo_promedio(float consumo_promedio) {
        this.consumo_promedio = consumo_promedio;
    }

    public float getTotal_lamparas() {
        return total_lamparas;
    }

    public void setTotal_lamparas(float total_lamparas) {
        this.total_lamparas = total_lamparas;
    }
    
    
    
   
    
}
