<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Ricerca Avanzata</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">

<%-- Include all the required script tags --%>
<jsp:include page="/WEB-INF/includes/ajaxscripts.jsp"/>

</head>


<body>

<div class="container">

<!-- Intestazione (logo e login)-->
	<div class="header"> 
		
		<div id="logo"> 
			<a id="logo" href="index"></a>
		</div>
		
		<div style="float:left; width:700px; padding: 10px 0px 0px 0px;"> 
									
			<c:if test="${ utente==null }"> 
				
					<div class="loginAlto">
					<div style="width:100%">
						
						<form action="ServletLogin" method="post">
							<input type="hidden" name="" value="login">
							<div style="float:left; width:150px; padding: 0px 5px 0px 0px;"> 
								<input style="float:left; height:22px; " type="text" name="nick" title="Inserisci l' username" placeholder="Username" tabindex="1"/>
								<div style="float:left; width:150px; padding: 0px 5px 0px 0px;">Sei un nuovo utente? </div>
								<div style="float:left; width:150px; padding: 0px 5px 0px 0px;"> <a style="font-size: normal; color:#0000FF;" href="registrazione.jsp">Registrati</a> </div> 
							</div>
							<div style="float:left; width:150px; padding: 0px 5px 0px 0px;"> 
								<input style="float:left; height:22px;" type="password" name="password" title="Inserisci la password" placeholder="Password" tabindex="2"/>
								<div style="float:left; width:150px; padding: 0px 5px 0px 0px;">Password dimenticata? </div>
								<div style="float:left; width:150px; padding: 0px 5px 0px 0px;"> <a style="font-size: normal; color:#0000FF;" href="recuperoPassword.jsp">Clicca qui</a> </div><div style="clear:both;"></div>
							</div>
							<div style="float:left; width:75px;"> 
								<input id="bottone-Accedi" style="display:inline;" value="Accedi" type="submit" />
							</div>
							<div style="clear:both"></div> 
						</form>
						
					</div>

				</div>
				<div style="clear:both"></div>	
			
			</c:if>
			
			<c:if test="${ utente!=null }"> 
                    
                     <div class="menuUtente">  				
						
						<div style="float:right;  padding: 0px 0px 0px 0px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">				 
							Benvenuto ${utente.nick} |
						</div> 
						<div style="clear: both;"></div>
						<div style="float:right;  padding: 7px 0px 0px 0px;">  
						  <c:choose>
						  <c:when test="${utente.tipologiaCliente eq 'venditore' }">
						  
						  												  
						 	 <a href="<%=response.encodeURL("ServletDettagliUtente")%>"><strong>I Miei Dati |</strong></a>
						 	 <a href="ServletLeMieInserzioni"><strong>Le Mie Inserzioni |</strong></a>
						 	 <a href="ServletInserzioniOsservate"><strong>Inserzioni Osservate |</strong></a>
						 	 <a href="ServletLeMieAste"><strong>Le Mie Aste |</strong></a>
						 	 <a href="ServletIMieiAcquisti"><strong>I Miei Acquisti |</strong></a>
						  </c:when>
						  <c:when test="${utente.tipologiaCliente eq 'acquirente' }">
							<a href="ServletDettagliUtente"><strong>I Miei Dati |</strong></a>
							<a href="ServletInserzioniOsservate"><strong>Inserzioni Osservate |</strong></a>
							<a href="ServletLeMieAste"><strong>Le Mie Aste |</strong></a>
							<a href="ServletIMieiAcquisti"><strong>I Miei Acquisti |</strong></a>
						  </c:when>
						  <c:when test="${utente.tipologiaCliente eq 'admin' }">
							 <div style="float:left; font-weight: bold;">
								Stato Sito: <font color="${colore}"> ${statoSito} &nbsp; &nbsp; </font> 
							 </div>
							 <a href="amministrazione.jsp"><strong>Home |</strong></a>
							 <a href="categorie.jsp"><strong>Categorie |</strong></a>
							 <a href="produttori.jsp"><strong>Produttori |</strong></a>
							 <a href="prodotti.jsp"><strong>Prodotti |</strong></a>
							 <a href="ServletListaUtenti"><strong>Utenti |</strong></a>
							 <a href="ServletListaInserzioni"><strong>Inserzioni |</strong></a>
						   	 <a href="ServletLogout"><strong>Logout |</strong></a>
						  </c:when>
						  </c:choose>
						 	 <a href="ServletLogout"><strong>Logout |</strong></a>
						</div>
			
				 	</div>
			
			</c:if>
			
			
		</div>
		
	</div>
    	
    	
	<!-- contenitore esterno-->

	<div class="centralBox"> 
		
		<!-- colonna di sinistra -->
		<div style="float:left; background-color:#ffffff; width:850px; height:100%;">
		
		
			<!-- Barra menu -->
			<div class="siteMenu">
				

				<nav>
				    <ul>
				       
				        <li><a href="index">Home</a></li>				        
				        <li><a href="ServletAsteInCorso">Aste in corso</a></li>
    			        <li><a href="ServletAsteInChiusura">Aste in chiusura</a></li>
    			        <li><a href="pagamenti.jsp">Pagamenti</a></li>
    			        <li><a href="contatti.jsp">Contatti</a></li>
				       
				    </ul>
				</nav>
				
				
			</div>
<!-- 		<div style="clear:both;"></div> -->
			
			
			<div class="centralBoxOperations">
			
				<p>&nbsp;</p>
  				<p align="center"><font size="4">${ messaggio }</font></p>
 
  	
  	
  								
  				<div class="centralBoxText">
		  
				  	<p>&nbsp;</p>
   					 <h3 align="center">Ricerca Avanzata Prodotto</h3>
    				<p>&nbsp;</p>
    				
    				<form action="ServletRicercaAvanzataProdotto" method="get" >
    				    					
    					<p>Ricerca per prodotto</p>
    					
    					<p style= "font-size:medium; font-weight: normal;" >Categoria</p>
    					<p>
  						<select id="categoria" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
							<option selected="selected" value="0">Seleziona una categoria</option> 
								<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
									<option value="${categoria.idCategoria}"> ${categoria.nome} </option> 
								</c:forEach>
						</select>
  						</p>
    					<p style= "font-size:medium; font-weight: normal;" >Produttore</p>
  						<p>
  						<select id="produttore"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un produttore" size="1" name="produttore">
								<option selected="selected" value="0"> Seleziona un produttore </option> 
						</select>
  						</p>
  						<p style= "font-size:medium; font-weight: normal;" >Prodotto</p>
  						<p>
  						<select id="prodotto"  disabled="disabled" style="height:20px; width: 200px; border: thin solid #666" title="Seleziona un prodotto" size="1" name="prodotto">
								<option selected="selected" value="0"> Seleziona un produttore </option> 
						</select>
  						</p>
  						
    					<ajax:select
						    baseUrl="${contextPath}/cercaproduttori.ajax"
						    source="categoria"
						    target="produttore"
						    parameters="categoria={categoria}"/>
    				
    					
    					<ajax:select
						    baseUrl="${contextPath}/cercaprodotti.ajax"
						    source="produttore"
						    target="prodotto"
						    parameters="produttore={produttore},categoria={categoria}"/>
  					
  					
  						<p>&nbsp;</p> 
    					<p align="center">	
					    	<input id="bottone-Cerca" type="submit" value="Cerca" id="bottone"/>&nbsp;&nbsp;&nbsp;
					   	 	<input id="bottone-Cerca" name="Ripristina" type="reset" id="bottone" value="Cancella"/>
					  	</p>
  					
  					</form>
  					
  					<p>&nbsp;</p>   				
    					<hr align="center" width="100%" size=1 color="#000000">
					<p>&nbsp;</p> 		    				
    				
  					<form action="ServletRicercaAvanzataInserzione" method="get" >
  					  	
  					  
	   					<h3 align="center">Ricerca Avanzata Inserzione</h3>
	    				<p>&nbsp;</p>
  					  	
  					  	<p>Ricerca per parola chiave</p> 
  					  	
  					  	<p style= "font-size:medium; font-weight: normal;" >Scegli la categoria 
  					  	<select style="height:20px; width: 200px; border: thin solid #666" title="Seleziona una categoria" size="1" name="categoria">
							<option selected="selected" value="0">Tutte le categorie</option> <!-- PER IL METODO RICERCA INSERZIONE, TUTTE LE CATEGORIE è 0 O NULL??? -->
								<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
									<option value="${categoria.idCategoria}"> ${categoria.nome} </option> <!-- COME INVIARE L'ID CATEGORIA ALLA SERVLET PER FARE LA RICERCA??? -->
								</c:forEach>
						</select>
						</p> 
  					  	<p>&nbsp;</p>		    			
    					<p style= "font-size:medium; font-weight: normal;" >
    						Inserisci parola chiave: <input id="autoKeyword" style="height: 20px; width: 150px;" type="text" size="50" maxlength="300" placeholder="Parola chiave" value="" name="parola_chiave" autocomplete="on">
    					</p> 
  					  	  					  	
  					  	
  					  	<p>&nbsp;</p> 
 						<hr align="center" width="80%" size=1 color="#000000">
 						<p>&nbsp;</p> 	    			
 		    		 
  					  	  							    				
    					<p>Ricerca per titolo</p>
    					<p style="font-size:medium; font-weight: normal;" >
    						Inserisci parte del titolo: <input id="autoTitolo" style="height: 20px; width: 150px;" name="titolo" type="text" maxlength="400" placeholder="Titolo" />
    					</p>
   						
   						<p>&nbsp;</p> 					
    					<hr align="center" width="80%" size=1 color="#000000">
						<p>&nbsp;</p>
    					
    					<p>Ricerca per prezzo</p>
    					<p style= "font-size:medium; font-weight: normal;" >
    						Inserzioni con Prezzo da EUR <input name="prezzoMin" type="text" maxlength="7" placeholder="Prezzo Minimo" id="campoTesto3"/> a EUR <input name="prezzoMax" type="text" maxlength="7" placeholder="Prezzo Massimo" id="campoTesto3"/>
    					</p>
 						
 						
						<p>&nbsp;</p> 
    					<p align="center">	
					    	<input id="bottone-Cerca" type="submit" value="Cerca" />&nbsp;&nbsp;&nbsp;
					   	 	<input id="bottone-Cerca" name="Ripristina" type="reset" value="Cancella"/>
					  	</p>
					 
    				</form>
    				
    				<ajax:autocomplete
						source="autoKeyword"
						target="autoKeyword"
						baseUrl="${contextPath}/autocompleteKeyword.ajax"
						parameters="keywordPrefix={autoKeyword}"
						className="autocompletamentokey"
						minimumCharacters = "1"/>
    				
    				<ajax:autocomplete
						source="autoTitolo"
						target="autoTitolo"
						baseUrl="${contextPath}/autocompleteTitolo.ajax"
						parameters="titoloPrefix={autoTitolo}"
						className="autocompletamentokey"
						minimumCharacters = "1"/>
    				
    				
    				<p>&nbsp;</p>
					  					  	
				</div>	
  				
  				
			</div>
			
					
		</div>
		
		
	</div>
		
	<!-- colonna di destra (pubblicitÃ ) -->
	<div style="float:left; width:150px; height:500px;">
		
			<p align="center" >
				<img alt="pubblicità" src="immagini/bannerAsteOnLine.jpg">
			</p> 
			<p align="center" >
				<img alt="pubblicità" src="immagini/bannerAsteOnLine.jpg">
			</p>
		
	</div>
	<div style="clear:both;"></div>
	
	<!-- footer -->	
	<div class="footer">
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/rosario.goglia" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>

</div>


</body>
</html>