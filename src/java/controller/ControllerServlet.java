/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import beans.Producto;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        categoriaList = createCategoriasBeans();
        getServletContext().setAttribute("categoriaList", categoriaList);

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

        // Leer el mapeo en web.xml
        String userPath = request.getServletPath();
        Categoria categoria;

        // si es "/category" asignar a dirección    
        if (userPath.equals("/category")) {
            
            //22-5-14 definimos metodo para buscar categoria, pasamos la categoria seleccionada y la lista de productos de dicha categoria
            String categoriaId = request.getQueryString();
            categoria = buscarCategoria(categoriaId);
            request.getSession().setAttribute("categoriaSeleccionada", categoria);
            request.getSession().setAttribute("listaProductos", categoria.getProductoList());
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

    //21/05/14- Creamos ArrayList con categorias
    private ArrayList<Categoria> createCategoriasBeans() {

        ArrayList<Categoria> categoriaTmp = new ArrayList<Categoria>();
        ArrayList<Producto> lacteosTmp = new ArrayList<Producto>();
        ArrayList<Producto> frutasTmp = new ArrayList<Producto>();
        ArrayList<Producto> verdurasTmp = new ArrayList<Producto>();
        ArrayList<Producto> legumbresTmp = new ArrayList<Producto>();
  
        //22/05/14 Creando productos
         
        Categoria lacteos = new Categoria(1, "Lácteos", "lacteos.jpg");
        Categoria frutas = new Categoria(2, "Frutas", "frutas.jpg");
        Categoria verduras = new Categoria(3, "Verduras", "verduras.jpg");
        Categoria legumbres = new Categoria(4, "Legumbres", "legumbres.jpg");

        Producto huevo = new Producto(1, "Huevos", 0.50, "Huevos ecologicos", null, null, 1);
        Producto leche = new Producto(2, "Leche", 1.00, "Leche de vaca", null, null, 1);
        Producto lecheSoja = new Producto(3, "Leche de Soja", 1.80, "Leche de Soja", null, null, 1);
        Producto queso = new Producto(4, "Quesos", 2.50, "Quesos Variados", null, null, 1);

        Producto manzana = new Producto(1, "Manzana", 0.40, "Manzanas del Campo", null, null, 2);
        Producto naranja = new Producto(2, "Naranja", 0.30, "Naranjas del Campo", null, null, 2);
        Producto platano = new Producto(3, "Plátano", 0.50, "Platanos de Canarias", null, null, 2);
        Producto fresa = new Producto(4, "Fresas", 0.40, "Fresas del Campo", null, null, 2);

        Producto alcachofa = new Producto(1, "Alcachofa", 0.40, "Alcachofas del Campo", null, null, 3);
        Producto coliflor = new Producto(2, "Coliflor", 0.40, "Coliflor del Campo", null, null, 3);
        Producto judia = new Producto(3, "Judías", 0.40, "Judía tierna", null, null, 3);
        Producto acelgas = new Producto(4, "Acelgas", 0.40, "Acelgas Frescas", null, null, 3);

        Producto garbanzos = new Producto(1, "Garbanzos", 0.40, "Garbanzos pa pearte", null, null, 4);
        Producto lentejas = new Producto(2, "Lentejas", 0.40, "Lentejas Frescas", null, null, 4);
        Producto judias = new Producto(3, "Judías", 0.40, "Judías", null, null, 4);
        Producto soja = new Producto(4, "Soja", 0.40, "Granos de Soja", null, null, 4);

        // 22-5-14 Añadidos productos a lista de productos
        lacteosTmp.add(huevo);
        lacteosTmp.add(leche);
        lacteosTmp.add(lecheSoja);
        lacteosTmp.add(queso);
        lacteos.setProductoList(lacteosTmp);

        
        frutasTmp.add(manzana);
        frutasTmp.add(naranja);
        frutasTmp.add(platano);
        frutasTmp.add(fresa);
        frutas.setProductoList(frutasTmp);

        verdurasTmp.add(alcachofa);
        verdurasTmp.add(coliflor);
        verdurasTmp.add(judia);
        verdurasTmp.add(acelgas);
        verduras.setProductoList(verdurasTmp);

        legumbresTmp.add(garbanzos);
        legumbresTmp.add(lentejas);
        legumbresTmp.add(judias);
        legumbresTmp.add(soja);
        legumbres.setProductoList(legumbresTmp);
        
        
        //22-5-14 Añadir lista de productos a categorias
        categoriaTmp.add(lacteos);
        categoriaTmp.add(frutas);
        categoriaTmp.add(verduras);
        categoriaTmp.add(legumbres);
      
                
        return categoriaTmp;

    }

    //22-5-14 buscamos categoria por id, devolvemos la categoria q coincide con el id q le pasamos
    private Categoria buscarCategoria(String categoriaId) {
        
      Categoria categoria= null;
      int categoriaIdInt = Integer.parseInt(categoriaId);
        
      for(int i=0; i<categoriaList.size(); i++){
          if(categoriaList.get(i).getId()==categoriaIdInt){
          return categoriaList.get(i);
          }
      
      }  
      
        return categoria;
        
    }

}
