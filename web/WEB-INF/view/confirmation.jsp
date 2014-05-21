<%--
    Document   : confirmar
    Created on : 12-mar-2014, 16:48:46
    Author     : Administrador
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



    <div id="singleColumn">

        <p id="confirmationText">
            <strong>Su orden está siendo procesada.<br> <br>
               En un plazo máximo de 24 horas recibirá el pedido.</strong>
            <br><br>
            Pedido referencia:
            <strong>[ número de referencia ]</strong>
            <br>
            Para cualquier aclaración sobre el pedido , <a href="#">contactenos</a> haciendo constar
            referencia y fecha pedido.
            <br><br>
            <strong>Gracias por comprar en nuestra tienda!</strong>

        </p>

        <div class="summaryColumn" >

            <table id="orderSummaryTable" class="detailsTable" >
                <tr class="header">
                    <th colspan="3">[ detalles de la compra ]</th>
                </tr>
                <tr class="tableHeading">
                    <td>Producto</td>
                    <td>Cantidad</td>
                    <td>Precio</td>
                </tr>
                <tr class="lightBlue">
                    <td>
                        [nombre producto]
                    </td>
                    <td class="quantityColumn">
                        [cuantidad]
                    </td>
                    <td class="confirmationPriceColumn">
                        &euro; [precio]
                    </td>
                </tr>
                <tr class="white">
                    <td>[nombre producto]</td>
                    <td class="quantityColumn">
                        [cuantidad]
                    </td>
                    <td class="confirmationPriceColumn">
                        &euro; [precio]
                    </td>
                </tr>


                <tr class="lightBlue">
                    <td colspan="3" style="padding: 0 20px"><hr>
                    </td>
                </tr>

                <tr class="lightBlue">
                    <td colspan="2" id="deliverySurchargeCellLeft">
                        <strong>Gastos de envío:</strong>
                    </td>
                    <td id="deliverySurchargeCellRight">
                        &euro; [gastos de envio]
                    </td>
                </tr>

                <tr class="lightBlue">
                    <td colspan="2" id="totalCellLeft">
                        <strong>Total:</strong>
                    </td>
                    <td id="totalCellRight">
                        &euro; [total orden]
                    </td>
                </tr>

                <tr class="lightBlue">
                    <td colspan="3" style="padding: 0 20px"><hr>
                    </td>
                </tr>

                <tr class="lightBlue">
                    <td colspan="3" id="dateProcessedRow">
                        <strong>Fecha pedido:</strong>
                        [fecha orden]
                    </td>
                </tr>
            </table>

        </div>

        <div class="summaryColumn" >

            <table id="deliveryAddressTable" class="detailsTable">
                <tr class="header">
                    <th colspan="3">Detalles del envio</th>
                </tr>

                <tr>
                    <td colspan="3" class="lightBlue">
                        [nombre cliente]
                        <br>
                        [direcion cliente]
                        <br>
                        Barcelona  [comarca cliente]
                        <br>
                        <hr>
                        <strong>email:</strong>  [email cliente]
                        <br>
                        <strong>telefono</strong>  [telefono cliente]
                    </td>
                </tr>
            </table>

        </div>
    </div>