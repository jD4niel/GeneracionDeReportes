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
    private int proyecto;

    public Proyectos() {
    }

    public Proyectos(int cotizacion, float preciok, int proyecto) {
        this.cotizacion = cotizacion;
        this.preciok = preciok;
        this.proyecto = proyecto;
    }

    public Proyectos(int id, int cotizacion, float preciok, int proyecto) {
        this.id = id;
        this.cotizacion = cotizacion;
        this.preciok = preciok;
        this.proyecto = proyecto;
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

    public int isProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

   
    
}
