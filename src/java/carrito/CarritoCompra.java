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
 private double subTotal;
    private double total;
    private ArrayList<ProductoCarritoCompra> listaCarrito;

    public CarritoCompra() {
        listaCarrito = new ArrayList<ProductoCarritoCompra>();
    }

    //implementamos metodo añadirProducto mirando si ya esta el producto que viene de la servlet
    //en la listaCarrito y si está llamamos a incrementaCantidad
    public void añadirProducto(Producto producto) {

        boolean productoEncontrado = false;
        for (int i = 0; i < listaCarrito.size(); i++) {
            if (producto.getId() == listaCarrito.get(i).getProduct().getId()) {
                listaCarrito.get(i).incrementaCantidad();
                productoEncontrado = true;
            }
        }
        if (productoEncontrado == false) {
            ProductoCarritoCompra productoNuevo = new ProductoCarritoCompra(1, producto);
            listaCarrito.add(productoNuevo);
        }
        numeroProductos++;
    }

    
    public double getSubTotal(){
        int unidades=0;
       double precio= 0;
        double subTotal=0;
        for (int i = 0; i < listaCarrito.size(); i++) {
         unidades= listaCarrito.get(i).getCantidad();
         precio= listaCarrito.get(i).getProduct().getPrecio();
         subTotal+= unidades*precio;
     }           
        return subTotal;
}
    
   

    public ArrayList<ProductoCarritoCompra> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(ArrayList<ProductoCarritoCompra> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public void limpia() {
        listaCarrito.clear();
        numeroProductos = 0;
    }

    public void actualiza(int cantidad, int productoIdInt) {
        int productoInicial = 0;
        int indexProducto = -1;
        for (int i = 0; i < listaCarrito.size(); i++) {
            if (productoIdInt == listaCarrito.get(i).getProduct().getId()) {

                if (cantidad == 0) {

                    indexProducto = i;
                    this.numeroProductos -= listaCarrito.get(i).getCantidad();

                } else {

                    if (cantidad > listaCarrito.get(i).getCantidad()) {
                        this.numeroProductos += cantidad - listaCarrito.get(i).getCantidad();

                    } else if (cantidad < listaCarrito.get(i).getCantidad()) {
                        this.numeroProductos -= listaCarrito.get(i).getCantidad() - cantidad;

                    }
                    listaCarrito.get(i).setCantidad(cantidad);
                }

                
            
            if (indexProducto != -1) {
                listaCarrito.remove(indexProducto);
            }
            }

           //ProductoCarritoCompra productoCarritoCompra = listaCarrito.get(indexProducto);
            //productoInicial = productoCarritoCompra.getCantidad();
             //TODO completar  esto
        }
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
