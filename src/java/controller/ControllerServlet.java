/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.LoggerManager;

/**
 *
 * @author Administrador
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

        // si es "/category" asignar a dirección    
        if (userPath.equals("/category")) {
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

    //21/05/14- Creamos ArrayList
    private ArrayList<Categoria> createCategoriasBeans() {
        
        ArrayList<Categoria> categoriaTmp = new ArrayList<Categoria>();
        
        Categoria lacteos = new Categoria( 1, "Lácteos","");
        Categoria frutas = new Categoria(2,"Frutas","");
        Categoria verduras = new Categoria(3,"Verduras","");
        Categoria legumbres = new Categoria(4,"Legumbres","");
        
        categoriaTmp.add(lacteos);
        categoriaTmp.add(frutas);
        categoriaTmp.add(verduras);
        categoriaTmp.add(legumbres);
        
        return categoriaTmp;
     
       
    }

}
