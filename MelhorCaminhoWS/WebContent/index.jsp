<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Controle</title>
        
        <script src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
        <script>
            var ctxPath = "<%=request.getContextPath() %>";
            var jsonString = JSON.stringify();
            $(function(){                
                $("#postMalhaDados").on("click", function(){
                    $.ajax({
                        url: $(this).attr("id") === "postMessage" ? ctxPath+"/rota/mapa" : ctxPath+"/rota/mapa",
                        type: "POST",
                        data: '{"origem":"BH", "destino":"Abaete", "distancia":"120"}',
                        contentType: "application/json",
                        cache: false,
                        dataType: "json"
                    });
                });                
            });
        </script>
                
    </head>
 
    <body>
       <ul>
           <li><a href="<%=request.getContextPath() %>/rota/qualcaminho"><%=request.getContextPath() %>Qual Caminho</a></li>
           <li><a href="<%=request.getContextPath() %>/rota/teste"><%=request.getContextPath() %>Ping WebService</a></li>
           <li><button id="postMalhaDados">Importar Malha de Dados</button></li>
       </ul>
           
    </body>
    
</html>