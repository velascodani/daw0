<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
    Document   : categoria
    Created on : 12-mar-2014, 16:48:46
    Author     : Administrador
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%-- 22/05/14 pasamos los productos de la categoria seleccionada, con el if controlamos para que el resto de categorias salgan
como link para q se puedan seleccionar tambien
        --%>  

<div id="categoryLeftColumn">

    <c:forEach var="categoria" items="${categoriaList}">

        <c:if test="${categoriaSeleccionada.id==categoria.id}">

            <div class="categoryButton" id="selectedCategory">
                <span class="categoryText">${categoria.nombre}</span>
            </div>  </c:if>
        
        <c:if test="${categoriaSeleccionada.id!=categoria.id}">

            <a href="category?${categoria.id}" class="categoryButton">
                <span class="categoryText">${categoria.nombre}</span>
            </a>
        </c:if>
    </c:forEach>

</div>

<div id="categoryRightColumn">
    <p id="categoryTitle">${categoriaSeleccionada.nombre}</p>

    <table id="productTable">

        <%-- 22/05/14 dentro del dolar tiene q coincidir con el request de la servlet dedicado a los productos
se cambia color de línea para cada producto dependiendo si es par o impar en base al valor recogido por varStatus        
        --%>  

        <c:forEach var="producto" items="${listaProductos}" varStatus="indicePar"> 
            <c:if test="${(indicePar.index % 2)==0}">
           
            <tr>
                <td class="lightBlue">
                    <img src="${initParam.productosImagePath}/${producto.imagen}" alt="${producto.imagen}">
                </td>
                <td class="lightBlue">
                    [ ${producto.nombre} ]
                    <br>
                    <span class="smallText">[  ${producto.descripcion}  ]</span>
                </td>
                <td class="lightBlue">[ ${producto.precio} ]</td>
                <td class="lightBlue">
                    <form action="addToCart" method="post">
                        <%-- hemos puesto un campo mas para enviar la id del producto seleccionado--%>
                        <input type="hidden" name="productoID" value="${producto.id}">
                        <input type="submit" value="comprar">
                    </form>
                </td>
            </tr>
            </c:if>
               <c:if test="${(indicePar.index % 2)!=0}">
           
            <tr>
                <td class="white">
                <img src="${initParam.productosImagePath}/${producto.imagen}" alt="${producto.imagen}">
                </td>
                <td class="white">
                    [ ${producto.nombre} ]
                    <br>
                    <span class="smallText">[  ${producto.descripcion}  ]</span>
                </td>
                <td class="white">[ ${producto.precio} ]</td>
                <td class="white">
                    <form action="addToCart" method="post">
                        <input type="hidden" name="productoID" value="${producto.id}">
                        <input type="submit" value="comprar">
                    </form>
                </td>
            </tr>
            </c:if>
        </c:forEach>
    </table>
</div>

