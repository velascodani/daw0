<%-- 
    Document   : error
    Created on : 20-may-2014, 11:49:21
    Author     : OCAD
--%>

<%@page isErrorPage="true" %>
<!DOCTYPE html>
<%-- 21/05/14 - Generada p�gina de error.
--%>
<html>
    <head>
        
        <title>P�gina de error</title>
    </head>
    <body bgcolor="FFCCCC">
        <h1>P�gina de error</h1>
        
        <h2>Detalles error</h2>
        <p>Tipo de error:
        <%=exception.getMessage()%>
       
        </p>
         <p>Tipo de error:
        <%=exception.toString()%>
        </p>
        <p>La p�gina que ha causado el error:
            <%=request.getAttribute("view")%>    
        </p>
        
        <h4><a href="index.jsp">Volver a inicio</a></h4>
    </body>
</html>
