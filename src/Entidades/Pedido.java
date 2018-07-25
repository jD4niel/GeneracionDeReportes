/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author jdaniel
 */
public class Pedido {

    private int id;
    private int luminaria_id;
    private int cantidad;

    public Pedido() {
    }

    public Pedido(int id, int luminaria_id, int cantidad) {
        this.id = id;
        this.luminaria_id = luminaria_id;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLuminaria_id() {
        return luminaria_id;
    }

    public void setLuminaria_id(int luminaria_id) {
        this.luminaria_id = luminaria_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
