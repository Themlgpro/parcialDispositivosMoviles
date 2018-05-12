package com.parcial.appsmoviles.parcial;

public class Producto {

    private int Producto_idProducto;
    private int Venta_idVenta;
    private int cantidad;

    public Producto(int producto_idProducto, int venta_idVenta, int cantidad) {
        Producto_idProducto = producto_idProducto;
        Venta_idVenta = venta_idVenta;
        this.cantidad = cantidad;
    }
    public  Producto(){
    }

    public int getProducto_idProducto() {
        return Producto_idProducto;
    }

    public void setProducto_idProducto(int producto_idProducto) {
        Producto_idProducto = producto_idProducto;
    }

    public int getVenta_idVenta() {
        return Venta_idVenta;
    }

    public void setVenta_idVenta(int venta_idVenta) {
        Venta_idVenta = venta_idVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
