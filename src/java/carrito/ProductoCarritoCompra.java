/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package carrito;

import beans.Producto;

/**
 *
 * @author FO-Mañana
 */
public class ProductoCarritoCompra {
    private int cantidad;
    private Producto product;

    public ProductoCarritoCompra(int cantidad, Producto product) {
        this.cantidad = cantidad;
        this.product = product;
    }

    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }
    public void incrementaCantidad(){
    //5-junio TODO implementar este metodo
        cantidad++;
    }
    
    public void decrementaCantidad(){
    cantidad--;
    }
    
    public double getPrecioProducto(){
        double precio=0;
        precio= this.cantidad*this.product.getPrecio();
        precio= Math.round(precio*100);
        precio= precio/100;
        return precio;
}
    
    public String toString(){
        return null;
}

   
}
