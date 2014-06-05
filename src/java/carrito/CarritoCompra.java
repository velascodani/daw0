/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito;

import beans.Producto;
import java.util.ArrayList;

/**
 *
 * @author FO-Mañana
 */
public class CarritoCompra {

    private int numeroProductos;
    private double total;
    private ArrayList<ProductoCarritoCompra> listaCarrito = new ArrayList<ProductoCarritoCompra>();

    public CarritoCompra() {
    }

    //implementamos metodo añadirProducto mirando si ya esta el producto que viene de la servlet
    //en la listaCarrito y si está llamamos a incrementaCantidad
    
    public void añadirProducto(Producto producto) {
        
        for(int i=0; i<listaCarrito.size();i++){
     if (producto.getId() == listaCarrito.get(i).getProducto().getId()) {
                listaCarrito.get(i).incrementaCantidad();
            }
        }
    }

    public double subTotal() {
        return 0;
    }

    public ArrayList<ProductoCarritoCompra> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(ArrayList<ProductoCarritoCompra> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public void limpia() {
    }

    public void actualiza(int cantidad, int productoIdInt) {
//TODO completar
    }

    public void calculaTotal(String gastosEnvio) {
    }

    public double getTotal() {
        return 0;
    }

    public int getNumeroProductos() {
        return numeroProductos;
    }

    public void setNumeroProductos(int numeroProductos) {
        this.numeroProductos = numeroProductos;
    }

}
