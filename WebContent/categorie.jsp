<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Categorie</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>

<%-- Include all the required script tags --%>
<jsp:include page="/WEB-INF/includes/ajaxscripts.jsp"/>


<body>

<div class="container">

<!-- Intestazione (logo e login)-->
	<div class="header"> 
		
		<div id="logo"> 
			<a id="logo" href="amministrazione.jsp"></a>
		</div>
		
		<div style="float:left; width:700px; padding: 10px 0px 0px 0px;"> 
			
                    <div class="menuUtente"> 	
                                        
						<div style="float:left; width:100%px; padding: 0px 0px 0px 0px;">  
						   	 <font style="font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">Benvenuto ${utente.nick} |</font>
						   	 <a href="amministrazione.jsp"><strong>Home |</strong></a>
							 <a href="categorie.jsp"><strong>Categorie |</strong></a>
							 <a href="produttori.jsp"><strong>Produttori |</strong></a>
							 <a href="prodotti.jsp"><strong>Prodotti |</strong></a>
							 <a href="ServletListaUtenti"><strong>Utenti |</strong></a>
							 <a href="ServletListaInserzioni"><strong>Inserzioni |</strong></a>
						   	 <a href="ServletLogout"><strong>Logout |</strong></a>
						</div>	
						<div style="clear: both;"></div>
						<br />
						<div style="float:right; font-weight: bold;">
							Stato Sito: <font color="${colore}"> ${statoSito} </font> 
						</div>
                    							
				 	</div>
				
		</div>
		
	</div>
    	
    	
	<!-- contenitore esterno-->

	<div class="centralBox"> 
		
		<p>&nbsp;</p>
 		<p align="center"><font size="4">${ messaggio }</font></p>
 
 
		<div class="centralBoxAmministrazione">
		
		
			   <p>&nbsp;</p>
			   <h2 align="center">Categorie</h2>
			   <p>&nbsp;</p>
					
				<p align="center">Categorie presenti:</p> 
			   			   
				<div style="width:300px;height:100px;overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: auto; font-size: small; font-weight: normal">
					 
					 <table width="100%" border=0>
					 	<c:forEach items="${applicationScope.listaCategorie}" var="categoria">
					 		<tr>
					 		
					 			<td align="center">${categoria.nome}</td>
					 		
					 		</tr>
					 	</c:forEach>
					 </table>
				</div>
				
			 	<form action="ServletAggiungiCategoria">
			   
			   		<p align="center" style="font-size: 20px">Aggiungi categoria: 
			   			<input id="campoTesto" type="text" size="50" maxlength="300" placeholder="Categoria" value="" name="categoria">
   			   		</p>	
   			   		
			   		<p>&nbsp;</p>
			   		
			   		<p align="center">
			   			<input type="submit" value="Aggiungi" id="bottone"/>&nbsp;&nbsp;&nbsp;
						<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
			   		</p>
			   		   	
			  	</form>
			   
			
			   <p>&nbsp;</p>   				
    				<hr align="center" width="50%" size=1 color="#000000">
			   <p>&nbsp;</p>
			   
			   <form action="ServletEliminaCategoria">
			   
			   		<p align="center">
			   			<font style="font-size: 20px">Rimuovi la categoria: </font>
				   			<select style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria per la ricerca" size="1" name="idCategoria">
								<option selected="selected" value="0">Seleziona la categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option>
									</c:forEach>
							</select>
			   		</p>	
   			   		
			   		<p>&nbsp;</p>
			   		
			   		<p align="center">
			   			<input type="submit" value="Rimuovi" id="bottone"/>&nbsp;&nbsp;&nbsp;
						<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
			   		</p>
			   		   	
			   </form>
			   
			    <p>&nbsp;</p>   				
    				<hr align="center" width="50%" size=1 color="#000000">
			   <p>&nbsp;</p>
			   
			   <form action="ServletModificaCategoria">
			   
			   		<p align="center">
			   			<font >Seleziona la categoria: </font>
				   			<select style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="idCategoria">
								<option selected="selected" value="0">Seleziona la categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option>
									</c:forEach>
							</select>
			   		</p>
			   		
			   		<p>&nbsp;</p> 
			   		
			   		<p align="center" style="font-size: 20px">Modifica categoria: 
			   			<input id="campoTesto" type="text" size="50" maxlength="300" placeholder="Categoria" value="" name="categoria">
   			   		</p>	
   			   			
   			   		
			   		<p>&nbsp;</p>
			   		
			   		<p align="center">
			   			<input type="submit" value="Modifica" id="bottone"/>&nbsp;&nbsp;&nbsp;
						<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
			   		</p>
			   		   	
			   </form>
			   
			   
			   <p>&nbsp;</p>   				
    				<hr align="center" width="90%" size=2 color="#000000">
			   <p>&nbsp;</p> 		    				
    					   
			   <p align="center" style="font-size: 20px">Associa produttore a categoria: </p>	
			   <p>&nbsp;</p> 
			   
			   <form action="ServletAssociaProduttore">
			   
			   			<p align="center">				   	
				   		Seleziona la categoria:
		  					<select id="cate" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  					</p>
			   			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">
			   			Seleziona il produttore:
		  					<select id="prod"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>	
			   			
			   			<ajax:select
						    baseUrl="${contextPath}/cercaproduttorimancanti.ajax"
						    source="cate"
						    target="prod"
						    parameters="categoria={cate}"/>
			   			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">
    						<input type="submit" value="Associa" id="bottone"/>&nbsp;&nbsp;&nbsp;
					   		<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
			   			</p>
			   </form>
			   
			   <p>&nbsp;</p>   				
    				<hr align="center" width="50%" size=1 color="#000000">
			  
			   
			   <p>&nbsp;</p>
			   <p align="center" style="font-size: 20px">Disassocia produttore dalla categoria: </p>	
			   <p>&nbsp;</p> 
			   
			    <form action="ServletDisassociaProduttore">
			   
				   	<p align="center">
				   	
				   		Seleziona la categoria:
	    			
	  					<select id="categoria2" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
							<option selected="selected" value="0">Seleziona una categoria</option> 
								<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
									<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
								</c:forEach>
						</select>
	  				</p>
	  				
	  				<p>&nbsp;</p> 
					
	  				
	    			<p align="center">Seleziona il produttore:
	  					<select id="produttore2"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
								<option selected="selected" value="0"> Seleziona un produttore </option> 
						</select>
	  				</p>				
					
    					<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria2"
						    target="produttore2"
						    parameters="categoria={categoria2}"/>
    				
    					
					<p>&nbsp;</p> 
					
					<p align="center">
    					<input type="submit" value="Disassocia" id="bottone"/>&nbsp;&nbsp;&nbsp;
						<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
			   		</p>
				
				</form>
			   
			   
			   <p>&nbsp;</p> 
			
			   	
		
		</div>
		
		
		
		<div class="immagineAreaRiservata">
			<img src="immagini/areariservata2.jpg" />		
		</div>
		
		<p>&nbsp;</p> 
		
	</div>
	<div style="clear:both;"></div>
	
	<!-- footer -->	
	
	<div class="footer">
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>