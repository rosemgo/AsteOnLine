<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Prodotti</title>
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
			   <h2 align="center">Prodotti</h2>
			   <p>&nbsp;</p>
				
				<p align="center" style="font-size: 20px">Aggiungi prodotto: </p>	
			  	<p>&nbsp;</p> 
			    <form action="ServletAggiungiProdotto">
				   
				   					
	    					<p align="center">Categoria</p>
	    					<p align="center">
	  						<select id="categoria" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  						</p>
	    					<p align="center">Produttore</p>
	  						<p align="center">
	  						<select id="produttore"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  						</p>
	  						
	  						<p>&nbsp;</p>
	  						
	  						<p align="center">Mostra prodotti presenti</p>
	  						
<!-- 	  						<input type="button" value= "Mostra prodotti" id="button"> -->
	  						
	  						<div style="width:250px;height:100px;overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: auto; font-size: small; font-weight: normal">
						 		<span id="prodotti">Seleziona categoria e produttore</span>	
							</div>
	  						
	  						<p>&nbsp;</p>
	  						
	  						<p align="center">Aggiungi prodotto:
	  							
	  							<input id="campoTesto" type="text" size="" maxlength="" placeholder="Nome prodotto" value="" name="nomeProdotto">
   			   				</p>
	  						
	  						<p>&nbsp;</p>
	  						 
	  						<p align="center">
					   			<input type="submit" value="Aggiungi" id="bottone"/>&nbsp;&nbsp;&nbsp;
								<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
					   		</p>
	  						
	  						
	  						
	  						
	  				 </form>
  				 		
    					<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria"
						    target="produttore"
						    parameters="categoria={categoria}"/>
    				
<%--     					<ajax:htmlContent --%>
<%-- 						    baseUrl="${contextPath}/mostraprodotti.ajax" --%>
<%-- 						    source="categoria" --%>
<%-- 						    target="prodotti" --%>
<%-- 						    parameters=""/> --%>
    					
    				
    					<ajax:htmlContent
						    baseUrl="${contextPath}/mostraprodotti.ajax"
						    source="produttore"
						    target="prodotti"
						    parameters="produttore={produttore},categoria={categoria}"/>
    					
    					
			  
			   
			   <p>&nbsp;</p>   				
    				<hr align="center" width="50%" size=1 color="#000000">
			   <p>&nbsp;</p>
			   
			    <p align="center" style="font-size: 20px">Rimuovi prodotto: </p>	
			   <p>&nbsp;</p> 
			   
			   <form action="ServletEliminaProdotto">
			   			
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
			   			
			   			<p align="center">
			   			Seleziona il produttore:
		  					<select id="produttore2"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  							
  						<p>&nbsp;</p> 
			   				   			
			   			<p align="center">
			   			Seleziona il prodotto:
		  					<select id="prodotto2"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un prodotto" size="1" name="prodotto">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  						
  						<p>&nbsp;</p> 
			 				
			 				<p align="center">
					   			<input type="submit" value="Rimuovi" id="bottone"/>&nbsp;&nbsp;&nbsp;
								<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
					   		</p> 			
			   				
  					</form>	
  					
  						<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria2"
						    target="produttore2"
						    parameters="categoria={categoria2}"/>
    				
    					
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="produttore2"
						    target="prodotto2"
						    parameters="produttore={produttore2},categoria={categoria2}"/>
    				
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="categoria2"
						    target="prodotto2"
						    parameters=""/>
		
			
				<p>&nbsp;</p>   				
    				<hr align="center" width="50%" size=1 color="#000000">
			   <p>&nbsp;</p>
			   
			   <p align="center" style="font-size: 20px">Modifica prodotto: </p>	
			   <p>&nbsp;</p> 
			   
			   <form action="ServletModificaProdotto">
			   			
			   			<p align="center">1- Seleziona il prodotto da modificare: </p>
			 			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">				   	
				   		Seleziona la categoria:
		  					<select id="categoria3" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" >
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  					</p>
			   			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">
			   			Seleziona il produttore:
		  					<select id="produttore3"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  							
  						<p>&nbsp;</p> 
			   				   			
			   			<p align="center">
			   			Seleziona il prodotto:
		  					<select id="prodotto3"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un prodotto" size="1" name="prodotto">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  						
  						<p>&nbsp;</p> 
			 			
			 			<p align="center">2- Seleziona i parametri da modificare: 
			 				<img style="margin: 10px 0px 0px 5px" title="Scegliere solo i parametri che si vogliono modificare, non è necessario modificarli tutti" src="immagini/tooltip.png" width="20px" height="20px">
					  	</p>
			 			
			 			<p>&nbsp;</p> 
			 				
			 			<p align="center">				   	
				   		Modifica la categoria:
		  					<select style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  					</p>	
	  					
			 			<p>&nbsp;</p> 
			 			
			 			<p align="center">				   	
				   		Modifica il produttore:
		  					<select style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaProduttori}" var="produttore" > 
										<option value="${produttore.idProduttore}"> ${produttore.nome} </option> 
									</c:forEach>
							</select>
	  					</p>	
			 					
			 			<p>&nbsp;</p> 
			 					 			
			 			<p align="center">Modifica nome prodotto:
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Nome prodotto" value="" name="nomeProdotto">
   			   			</p>
   			   			
   			   			<p>&nbsp;</p> 
   			   			
   			   			<p align="center">
					   		<input type="submit" value="Modifica" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
					   	</p> 			
			   				
			   				
  					</form>	
  					
  					<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria3"
						    target="produttore3"
						    parameters="categoria={categoria3}"/>
    				
    					
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="produttore3"
						    target="prodotto3"
						    parameters="categoria={categoria3},produttore={produttore3}"/>
    				
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="categoria3"
						    target="prodotto3"
						    parameters=""/>
		
			
			
			   <p>&nbsp;</p>   				
    				<hr align="center" width="90%" size="2" color="#000000">
			   <p>&nbsp;</p>
			  
			   <p align="center" style="font-size: 20px">Parole chiave: </p>	
			   <p>&nbsp;</p> 
			   
			   
			   <p align="center">Parole chiave presenti:</p> 
			   
				<div style="width:300px;height:100px;overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: auto; font-size: small; font-weight: normal">
					 
					 <table width="100%" border=0>
					 	<c:forEach items="${applicationScope.listaKeyword}" var="keyword">
					 		<tr>
					 		
					 			<td align="center">${keyword.keyword}</td>
					 		
					 		</tr>
					 	</c:forEach>
					 </table>
				</div>	 
			   
			   <form action="ServletAggiungiKeyword">
			    	
			    		<p align="center" >Aggiungi parole chiave:
	  						
						  		<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="listaKeywords">
   			   					<img style="margin: 10px 0px 0px 5px" title="E' possibile inserire più parole separate solo da virgola, quindi senza lo spazio dopo la virgola" src="immagini/tooltip.png" width="20px" height="20px">
					  	 	
	  					</p>
			    	
			    			
			    	
			    		<p>&nbsp;</p>
	  						 
	  					<p align="center">
					   		<input type="submit" value="Aggiungi" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
				   		</p>
			    
			    
			    </form>
			   
			   <p>&nbsp;</p> 
			   	
				<form action="ServletEliminaKeyword">
			    	
			    		<p align="center">Rimuovi parola chiave:
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="keyword">
   			   			</p>
			    	
			    		<p>&nbsp;</p>
	  						 
	  					<p align="center">
					   		<input type="submit" value="Rimuovi" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
				   		</p>
			    
			    
			     </form>
			    
			    <p>&nbsp;</p> 
			   	
				<form action="ServletModificaKeyword">
			    	
			    		<p align="center">Inserire la parola chiave da modificare:
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="keyword">
   			   			</p>
			    	
			    		<p>&nbsp;</p>
	  					
	  					<p align="center">Inserire la parola chiave compresa di modifica:
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="keywordModificata">
   			   			</p>
	  					
	  					<p>&nbsp;</p>
	  						 
	  					<p align="center">
					   		<input type="submit" value="Modifica" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
				   		</p>
			    
			    
			    </form>
				
			   <p>&nbsp;</p>   				
    				<hr align="center" width="50%" size="1" color="#000000">
			   <p>&nbsp;</p>
				
				<p align="center" style="font-size: 20px">Associa prodotto - parola chiave: </p>	
			   	<p>&nbsp;</p> 
				
				<form action="ServletAssociaProdottoKeyword">
			   			
			   			<p align="center">1- Seleziona il prodotto a cui associare la parola chiave: </p>
			 			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">				   	
				   		Seleziona la categoria:
		  					<select id="categoria4" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" >
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  					</p>
			   			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">
			   			Seleziona il produttore:
		  					<select id="produttore4"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  							
  						<p>&nbsp;</p> 
			   				   			
			   			<p align="center">
			   			Seleziona il prodotto:
		  					<select id="prodotto4"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un prodotto" size="1" name="prodotto">
									<option selected="selected" value="0"> Seleziona un prodotto </option> 
							</select>
	  					</p>
	  					
	  					<p>&nbsp;</p>
	  					
	  					<p align="center">Mostra parole chiave non ancora associate al prodotto:</p>
	  						
	  						<div style="width:250px;height:100px;overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: auto; font-size: small; font-weight: normal">
						 		<span id="keyword">Seleziona il prodotto</span>	
							</div>
	  						
	  					<p>&nbsp;</p>
  						
					
						<p align="center">2- Indicare la parola chiave da associare al prodotto scelto: </p>
			 			
			 			<p>&nbsp;</p> 
			 			
						<p align="center">Associa parola chiave: 
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="keyword">
   			   			</p>
			    	
			    		<p>&nbsp;</p>
	  						 
	  					<p align="center">
					   		<input type="submit" value="Associa" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
				   		</p>
												
				  
				  </form>
					
					
						<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria4"
						    target="produttore4"
						    parameters="categoria={categoria4}"/>
    				
    					
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="produttore4"
						    target="prodotto4"
						    parameters="produttore={produttore4},categoria={categoria4}"/>
    				
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="categoria4"
						    target="prodotto4"
						    parameters=""/>	  
		
						<ajax:htmlContent
						    baseUrl="${contextPath}/mostrakeywordmancanti.ajax"
						    source="prodotto4"
						    target="keyword"
						    parameters="prodotto={prodotto4}"/>
						    
						<ajax:htmlContent
						    baseUrl="${contextPath}/mostrakeywordmancanti.ajax"
						    source="categoria4"
						    target="keyword"
						    parameters=""/>
						    
		
		
		
		
				<p>&nbsp;</p>   				
    				<hr align="center" width="50%" size="1" color="#000000">
			   <p>&nbsp;</p>
				
				<p align="center" style="font-size: 20px">Disassocia prodotto - parola chiave: </p>	
			   	<p>&nbsp;</p> 
				
				<form action="ServletDisassociaProdottoKeyword">
			   			
			   			<p align="center">1- Seleziona il prodotto da cui disassociare la parola chiave: </p>
			 			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">				   	
				   		Seleziona la categoria:
		  					<select id="categoria5" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" >
								<option selected="selected" value="0">Seleziona una categoria</option> 
									<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
										<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
									</c:forEach>
							</select>
	  					</p>
			   			
			   			<p>&nbsp;</p> 
			   			
			   			<p align="center">
			   			Seleziona il produttore:
		  					<select id="produttore5"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
  							
  						<p>&nbsp;</p> 
			   				   			
			   			<p align="center">
			   			Seleziona il prodotto:
		  					<select id="prodotto5"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un prodotto" size="1" name="prodotto">
									<option selected="selected" value="0"> Seleziona un produttore </option> 
							</select>
	  					</p>
	  					
	  					<p>&nbsp;</p>
	  					
	  					<p align="center">Mostra parole chiave già associate al prodotto:</p>
	  						
	  						<div style="width:250px;height:100px;overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: auto; font-size: small; font-weight: normal">
						 		<span id="keyword2">Seleziona il prodotto</span>	
							</div>
	  						
	  					<p>&nbsp;</p>
  						
					
						<p align="center">2- Indicare la parola chiave che si vuole disassociare dal prodotto scelto: </p>
			 			
			 			<p>&nbsp;</p> 
			 			
						<p align="center">Disassocia parola chiave: 
	  						<input id="campoTesto" type="text" size="" maxlength="" placeholder="Parole chiave" value="" name="keyword">
   			   			</p>
			    	
			    		<p>&nbsp;</p>
	  						 
	  					<p align="center">
					   		<input type="submit" value="Disassocia" id="bottone"/>&nbsp;&nbsp;&nbsp;
							<input name="Cancella" type="reset" id="bottone" value="Cancella"/>
				   		</p>
												
				  
				  </form>
					
					
						<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria5"
						    target="produttore5"
						    parameters="categoria={categoria5}"/>
    				
    					
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="produttore5"
						    target="prodotto5"
						    parameters="produttore={produttore5},categoria={categoria5}"/>
    				
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="categoria5"
						    target="prodotto5"
						    parameters=""/>	  
		
						<ajax:htmlContent
						    baseUrl="${contextPath}/mostrakeyword.ajax"
						    source="prodotto5"
						    target="keyword2"
						    parameters="prodotto={prodotto5}"/>
						    
						<ajax:htmlContent
						    baseUrl="${contextPath}/mostrakeyword.ajax"
						    source="categoria5"
						    target="keyword2"
						    parameters=""/>
						  
		
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