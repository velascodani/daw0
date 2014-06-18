/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import beans.OrdenCliente;
import beans.Producto;
import carrito.CarritoCompra;
import carrito.ProductoCarritoCompra;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managers.DatabaseManager;
import managers.LoggerManager;

/**
 *
 * @author OCAD
 */
public class ControllerServlet extends HttpServlet {

    private ArrayList<Categoria> categoriaList;
    private int gastos;
    
    

// 21/05/14 - Modiﬁcar el ControllerServlet perquè implemenf el mètode init i 
//inifalitzi el preﬁx del LoggerManager amb la ruta de l’aplicació     
    //21/05/14- Añadimos Init para crear categorias
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String prefix = getServletContext().getRealPath("/");
        LoggerManager.prefix = prefix;
       gastos=  Integer.parseInt (config.getServletContext().getInitParameter("gastos"));

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

        HttpSession httpSession = request.getSession();
        CarritoCompra carritoCompra = (CarritoCompra) httpSession.getAttribute("carritoCompra");

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
        // si es "/cleanCart" asignar a dirección
        if (userPath.equals("/cleanCart")) {
            carritoCompra.limpia();
            httpSession.setAttribute("carritoCompra", carritoCompra);
            userPath = "cart";
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

        //5-junio abrimos la sesson y recuperamos el objeto carrito
        HttpSession httpSession = request.getSession();
        CarritoCompra carritoCompra = (CarritoCompra) httpSession.getAttribute("carritoCompra");

        // si es "/addToCart" asignar a dirección
        if (userPath.equals("/addToCart")) {

            userPath = "category";

            //5-junio comprobamos si existe el carrito o no y sino existe lo creamos
            if (carritoCompra == null) {

                carritoCompra = new CarritoCompra(gastos);
            }
            String productoIdString = request.getParameter("productoID");

            int productoID = Integer.parseInt(productoIdString);

// llamamos a un metodo para recuperar el producto seleccionado
            Producto producto = cogerProd(productoID);
            carritoCompra.añadirProducto(producto);
            httpSession.setAttribute("carritoCompra", carritoCompra);

        }

        // si es "/updateCart" asignar a dirección
        if (userPath.equals("/updateCart")) {

            int cantidad = Integer.parseInt(request.getParameter("quantity"));
            int producteID = Integer.parseInt(request.getParameter("productId"));
            carritoCompra.actualiza(cantidad, producteID);
            httpSession.setAttribute("carritoCompra", carritoCompra);

            userPath = "cart";
        }

        // si es "/purchase" asignar a dirección
        if (userPath.equals("/purchase")) {
            //recuperar datos formulario

            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String direcion = request.getParameter("direcion");
            String tarjeta = request.getParameter("tarjeta");

            if (nombre != null && !nombre.equals("")
                    && email != null && !email.equals("")
                    && telefono != null && !telefono.equals("")
                    && direcion != null && !direcion.equals("")
                    && tarjeta != null && !tarjeta.equals("")) {

                int numeroConfirmacion = gestionaOrden(nombre, email, direcion, telefono, tarjeta, carritoCompra);

            } else {
                userPath = "checkout";

            }

// crear cliente BD
            // devolver id cliente
            // crear orden
            // añadir productos en la orden
            userPath = "confirmation";
        }

        // Montar el String url con los parametros de arriba
        String url = "/WEB-INF/view/" + userPath + ".jsp";

        request.setAttribute("view", url);
        request.getRequestDispatcher(url).forward(request, response);

        DatabaseManager.cerrarConexion();
    }

    //5-junio metodo para coger el producto seleccionado y tramitamos el producto q hemos cogido en
    //la clase CarritoCompra
    private Producto cogerProd(int productoID) {

        Producto producto = null;
        String sql = "SELECT * FROM producte WHERE id =" + productoID;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = DatabaseManager.conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String img = resultSet.getString("img");
                String desc = resultSet.getString("desc");
                double preu = resultSet.getDouble("preu");
                producto = new Producto(id, nom, preu, desc, img);

            }
        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());

        }
        return producto;

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

    private int gestionaOrden(String nombre, String email, String direcion, String telefono, String tarjeta, CarritoCompra carritoCompra) {

        OrdenCliente ordenCliente = null;
        try {
            DatabaseManager.conn.setAutoCommit(false);
            int clienteID = creaCliente(nombre, email, telefono, direcion, tarjeta);
            ordenCliente = creaOrden(carritoCompra, clienteID);
            creaProductoOrden(carritoCompra, ordenCliente.getId());
            DatabaseManager.conn.commit();
            DatabaseManager.conn.setAutoCommit(true);
            return 0;
        } catch (SQLException ex) {
            try {
                DatabaseManager.conn.rollback();
            } catch (SQLException ex1) {
                LoggerManager.getLog().error(ex.toString());
            }
            LoggerManager.getLog().error(ex.toString());
        } finally {
            return ordenCliente.getNumeroConfirmacion();
        }

    }

    private int creaCliente(String nombre, String email, String telef, String direcion, String tarjeta) {

        int clienteID = 0;

       

        String creaClienteSql = "INSERT into client (nom, correu, telef, carrer, targeta) "
                + "VALUES ('"+nombre+"','"+email+"', '"+telef+"',  '"+direcion+"', '"+tarjeta+"' )";
        
        try {
            clienteID= DatabaseManager.executeUpdate(creaClienteSql);
        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());
                   
        }

            return clienteID;

        }

    

    private OrdenCliente creaOrden(CarritoCompra carritoCompra, int clienteID) {
        
        OrdenCliente ordenCliente= null;
        Random random= new Random();
        int numeroConfirmacion= random.nextInt(9999991);
        String stringClienteID= String.valueOf(clienteID);
        int ordenID=0;
        
         String creaOrdenSql = "INSERT into ordre_client (preu_total, confirm_id, client_dni) "
                + "VALUES ("+carritoCompra.getTotal()+","+numeroConfirmacion+", '"+stringClienteID+"')";
        
        try {
            ordenID= DatabaseManager.executeUpdate(creaOrdenSql);
        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());
                   
        }
        ordenCliente= new OrdenCliente(ordenID, numeroConfirmacion);
        return ordenCliente;
        
        
        
    }

    private void creaProductoOrden(CarritoCompra carritoCompra, int id) {
       String stringID= null;
       String stringCantidad= null;
        String ordenProductoSql= "INSERT into producte_has_ordre_client (producte_id,ordre_client_id,cantidad)"
                + "VALUES (PRODUCTOID, "+id+", CANTIDAD)";
        stringID= Integer.toString(id);
        
        
        for(int i=0; i<carritoCompra.getListaCarrito().size(); i++){
          stringCantidad= Integer.toString(carritoCompra.getListaCarrito().get(i).getCantidad());
        if(id== carritoCompra.getListaCarrito().get(i).getProduct().getId()){
           ordenProductoSql= ordenProductoSql.replaceAll("PRODUCTOID", stringID);
        ordenProductoSql= ordenProductoSql.replaceAll("CANTIDAD", stringCantidad);
        }
    }
    }

}
