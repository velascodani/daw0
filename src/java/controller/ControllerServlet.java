/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrador
 */
public class ControllerServlet extends HttpServlet {

    

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
         if(userPath.equals("/category")){
            userPath = "category";
        }
         
        // si es "/viewCart" asignar a dirección
        if(userPath.equals("/viewCart")){
            userPath = "cart";
        }
        
        // si es "/checkout" asignar a dirección
        if(userPath.equals("/checkout")){
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
        if(userPath.equals("/addToCart")){
            userPath = "category";
        }
        
        // si es "/updateCart" asignar a dirección
        if(userPath.equals("/updateCart")){
            userPath = "cart";
        }
        
        // si es "/cleanCart" asignar a dirección
        if(userPath.equals("/cleanCart")){
            userPath = "cart";
        }
        
        // si es "/purchase" asignar a dirección
        if(userPath.equals("/purchase")){
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

}
