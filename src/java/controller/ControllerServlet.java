/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import beans.Producto;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.DatabaseManager;
import managers.LoggerManager;

/**
 *
 * @author OCAD
 */
public class ControllerServlet extends HttpServlet {

    private ArrayList<Categoria> categoriaList;

// 21/05/14 - Modiﬁcar el ControllerServlet perquè implemenf el mètode init i 
//inifalitzi el preﬁx del LoggerManager amb la ruta de l’aplicació     
    //21/05/14- Añadimos Init para crear categorias
    @Override
    public void init() throws ServletException {
        super.init();
        String prefix = getServletContext().getRealPath("/");
        LoggerManager.prefix = prefix;

        /*28.05.14
         - Llamada a la función  Crear Categorías y guardar la lista en la sesión
         */
        DatabaseManager.abrirConexion();
        categoriaList = crearCategorias();
        getServletContext().setAttribute("categoriaList", categoriaList);

        DatabaseManager.cerrarConexion();

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//28.05.14 llamada a la función DatabaseManager.java para conectar con la Base de Datos
        DatabaseManager.abrirConexion();

        // Leer el mapeo en web.xml
        String userPath = request.getServletPath();
        Categoria categoria;

        // si es "/category" asignar a dirección    
        if (userPath.equals("/category")) {

            //22-5-14 definimos metodo para buscar categoria, pasamos la categoria seleccionada y la lista de productos de dicha categoria
            
            String categoriaId = request.getQueryString();
            ArrayList<Producto> listaPdtos = buscarPdtosCategoria(categoriaId);
// 29/05/2014 LLamada a la función para buscar Categoría seleccionada  desde el menú principal             
            categoria = buscarCategoriaPorId(categoriaId);
            request.getSession().setAttribute("categoriaSeleccionada", categoria);
            request.getSession().setAttribute("listaProductos", listaPdtos);
            userPath = "category";

        }

        // si es "/viewCart" asignar a dirección
        if (userPath.equals("/viewCart")) {
            userPath = "cart";

            // 21/05/14 - Añadido  log            
            if (LoggerManager.DEBUG) {
                LoggerManager.getLog().info("Opción seleccionada ver carrito");
            }

        }

        // si es "/checkout" asignar a dirección
        if (userPath.equals("/checkout")) {
            userPath = "checkout";
        }

        // Montar el String url con los parametros de arriba
        String url = "/WEB-INF/view/" + userPath + ".jsp";
        request.setAttribute("view", url);
        request.getRequestDispatcher(url).forward(request, response);
        DatabaseManager.cerrarConexion();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//28.05.14 llamada a la función DatabaseManager.java para conectar con la Base de Datos
        DatabaseManager.abrirConexion();

        // Leer el mapeo en web.xml
        String userPath = request.getServletPath();

        // si es "/addToCart" asignar a dirección
        if (userPath.equals("/addToCart")) {
            userPath = "category";
        }

        // si es "/updateCart" asignar a dirección
        if (userPath.equals("/updateCart")) {
            userPath = "cart";
        }

        // si es "/cleanCart" asignar a dirección
        if (userPath.equals("/cleanCart")) {
            userPath = "cart";
        }

        // si es "/purchase" asignar a dirección
        if (userPath.equals("/purchase")) {
            userPath = "confirmation";
        }

        // Montar el String url con los parametros de arriba
        String url = "/WEB-INF/view/" + userPath + ".jsp";

        request.setAttribute("view", url);
        request.getRequestDispatcher(url).forward(request, response);

        DatabaseManager.cerrarConexion();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    //22-5-14 buscamos categoria por id, devolvemos la categoria q coincide con el id q le pasamos
    private ArrayList<Producto> buscarPdtosCategoria(String categoriaId) {

        ArrayList<Producto> pdtoList = new ArrayList<Producto>();

        int categoriaIdInt = Integer.parseInt(categoriaId);
        String pdtoSql = "SELECT * FROM producte WHERE categoria_id =" + categoriaIdInt;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = DatabaseManager.conn.prepareStatement(pdtoSql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String img = resultSet.getString("img");
                String desc = resultSet.getString("desc");
                double preu = resultSet.getDouble("preu");
                Producto producto = new Producto(id, nom, preu, desc, img);
                pdtoList.add(producto);

            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());
        } finally {
            return pdtoList;
        }

    }

    /*28.05.14 FUNCION PARA CREAR CATEGORIAS
    
     - Llamada a la función DatabaseManager.java para conectar con la Base de Datos
     - Preeparar sql
     - Ejecutar sql 
     - Tratar registros mediante bucle
     - Crear categorías
     */
    private ArrayList<Categoria> crearCategorias() {

        categoriaList = new ArrayList<Categoria>();

        try {
            String categoriaSql = "SELECT * FROM categoria";
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            preparedStatement = DatabaseManager.conn.prepareStatement(categoriaSql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String img = resultSet.getString("img");
                Categoria categoria = new Categoria(id, nom, img);
                categoriaList.add(categoria);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());
        } finally {
            return categoriaList;
        }

    }
/*29/05/14
    Función que selecciona de la clase Categoría el objeto de la arryalist mediante su id.
    Este objeto se trata en category.jsp para mostrar la categoría
    */
    private Categoria buscarCategoriaPorId(String categoriaId) {
        Categoria categoriaTmp = null;
        int categoriaIdInt = Integer.parseInt(categoriaId);
        for (int i = 0; i < categoriaList.size(); i++) {
            if (categoriaIdInt == categoriaList.get(i).getId()) {
                return categoriaList.get(i);
            }

        }
        return categoriaTmp;
    }

}
