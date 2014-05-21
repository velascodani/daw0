/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.ArrayList;

/**
 *
 * @autor OCAD
 * 
 * 21/05/14-Creamos clase y constructor
 */
public class Categoria {
    private Integer id;
    private String nombre;
    private String imagen;
    private ArrayList<Producto> productoList;
    
    public Categoria (int id, String nombre,String imagen){
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.productoList = new ArrayList<Producto>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
