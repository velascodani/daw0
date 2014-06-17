<%--
    Document   : carrito
    Created on : 12-mar-2014, 16:48:46
    Author     : Administrador
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">




            <div id="centerColumn">

                <p>Tu carrito de la compra contiene ${carritoCompra.numeroProductos} artículos.</p>

                 <div id="actionBar">
                    <%-- si esta definido el carrito i el numero de elemento  es >0--%>
                    <a href="cleanCart" class="bubble hMargin">limpiar carrito</a>
                     <%-- si el usuario ha selecionado una categoria sino me quedo en la pagina inicial--%>
                     
                    <a href="category?${categoriaSeleccionada.id}" class="bubble hMargin">continuar la compra</a>
                     <%-- si esta definido el carrito i el numero de elemento es >0 procedo con el pedido--%>
                     
                    <a href="checkout" class="bubble hMargin">proceder con el pedido</a>
                </div>

                <%-- si el carrito esta definido y tengo elementos en el carrito  --%>  
                <h4 id="subtotal"> Subtotal compra: ${carritoCompra.subTotal}</h4>

                <table id="cartTable">

                    <tr class="header">
                        <th>Producto</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                    </tr>
                    
<c:forEach var="producto" items="${carritoCompra.listaCarrito}" varStatus="indicePar"> 
            <c:if test="${(indicePar.index % 2)==0}">
           
            <tr>
               
                
                <td class="lightBlue">
                    <tr>
                        <td class="lightBlue">
                            <img src="${initParam.productosImagePath}/${producto.product.imagen}" alt="product image">
                        </td>
                        
                        <td class="lightBlue">${producto.product.nombre}</td>
                        
                        <td class="lightBlue">
                            ${producto.product.precio * producto.cantidad} <%-- precio productos --%>
                            <br>
                            <span class="smallText">
                                ${producto.product.precio}
                               
                            </span>
                        </td>
                        
                        <td class="lightBlue">${producto.cantidad}

                            <form action="updateCart" method="post">
                                <input type="hidden"
                                       name="productId"
                                       value="${producto.product.id}"> <%--id del producto --%>
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="${producto.cantidad}" <%--cuantidad de productos que tengo en el carrito --%>
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="Actualizar">
                            </form>
                        </td>
                    </tr>
            </c:if>
            <c:if test="${(indicePar.index % 2)!=0}">
           
            <tr>
               
                
                <td class="white">
                    <tr>
                        <td class="white">
                            <img src="${initParam.productosImagePath}/${producto.product.imagen}" alt="product image">
                        </td>
                        
                        <td class="white">${producto.product.nombre}</td>
                        
                        <td class="white">
                            ${producto.product.precio * producto.cantidad} <%-- precio productos --%>
                            <br>
                            <span class="smallText">
                                ${producto.product.precio}
                               
                            </span>
                        </td>
                        
                        <td class="white">${producto.cantidad}

                            <form action="updateCart" method="post">
                                <input type="hidden"
                                       name="productId"
                                       value="${producto.product.id}"> <%--id del producto --%>
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="${producto.cantidad}" <%--cuantidad de productos que tengo en el carrito --%>
                                       name="quantity">
                                
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>
            </c:if>         
</c:forEach>
<!--
                     <tr>
                        <td class="white">
                            <img src="#" alt="product image">
                        </td>
                        
                        <td class="white">[ nombre producto ]</td>
                        
                        <td class="white">
                            [&euro; precio ] <%-- precio productos --%>
                            <br>
                            <span class="smallText">
                                [detalles precio unitad]
                                
                            </span>
                        </td>
                        
                        <td class="white">[ cuantidad ]

                            <form action="updateCart" method="post">
                                <input type="hidden"
                                       name="productId"
                                       value="productoId"> <%--id del producto --%>
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1" <%--cuantidad de productos que tengo en el carrito --%>
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>

                     <tr>
                        <td class="lightBlue">
                            <img src="#" alt="product image">
                        </td>
                        
                        <td class="lightBlue">[ nombre producto ]</td>
                        
                        <td class="lightBlue">
                            [&euro; precio ] <%-- precio productos --%>
                            <br>
                            <span class="smallText">
                                [detalles precio unitad]
                               
                            </span>
                        </td>
                        
                        <td class="lightBlue">[ cuantidad ]

                            <form action="updateCart" method="post">
                                <input type="hidden"
                                       name="productId"
                                       value="productoId"> <%--id del producto --%>
                                <input type="text"
                                       maxlength="2"
                                       size="2"
                                       value="1" <%--cuantidad de productos que tengo en el carrito --%>
                                       name="quantity">
                                <input type="submit"
                                       name="submit"
                                       value="actualizar">
                            </form>
                        </td>
                    </tr>
                    --!>

                </table>

            </div>
