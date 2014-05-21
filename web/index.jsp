<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : index
    Created on : 12-mar-2014, 16:48:46
    Author     : Administrador
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


    <div id="indexLeftColumn">
        <div id="welcomeText">
            <p>Bienvenid@ a la tienda ecológica.</p>
            
            
        </div>
    </div>
<%--21/05/14-Sustituimos html fijo por bucle --%>
    <div id="indexRightColumn">
        <c:forEach var="categoria" items="${categoriaList}">
            <div class="categoryBox">
            <a href="category?categoryId=${categoria.id}">
                 <span class="categoryLabel">${categoria.id}</span>
                <span class="categoryLabelText">${categoria.nombre}</span>
                <img src="${initParam.categoriasImagenesPath}/${categoria.imagen}" alt="${categoria.nombre}">
            </a>
        </div>
            
        </c:forEach>
        
    </div>
   



