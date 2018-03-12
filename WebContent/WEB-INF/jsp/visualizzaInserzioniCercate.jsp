<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Visualizza Ricerca Inserzioni</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
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
		<div style="float:left; background-color:#ffffff; width:850px; height:100%px;">
		
		
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
  				  					
  				<div class="centralBoxRisultatiProdotti">
			     
			      <p>&nbsp;</p>
			      <h2 align="center">Risultati Ricerca</h2>
			      <p>&nbsp;</p>
			     
			      <p align="center"></p>
			      <c:if test="${ numeroInserzioni > 0 }">
				         <table width="830" align="center" border="0" cellpadding="5" cellspacing="5">
					        <tr>
					          <th id="campoTesto3">Immagine</th>
					          <th id="campoTesto3">Titolo</th>
					          <th id="campoTesto3">Prodotto</th>
					          <th id="campoTesto3">Categoria</th>
					          <th id="campoTesto3">Data Scadenza</th>
					          <th id="campoTesto3">Prezzo</th>
					          <th ></th>
					          <th ></th>
					        </tr>
					       
					      <c:forEach items="${intervalloInserzioniCercate}" var="inserzioneCercata" > 
					     		<tr>
					     		  
					     		  <td id="td2" align="center"><img src="${ inserzioneCercata.immagini[0].foto }"  width="80px" height="80px"></img></td>
						          <td id="td2" align="center">${inserzioneCercata.titolo }</td>
						          <td id="td2" align="center">${inserzioneCercata.prodotto.nome } </td>
						          <td id="td2" align="center">${inserzioneCercata.prodotto.categoria.nome } </td>
						     	  <td id="td2" align="center">${inserzioneCercata.dataScadenzaString}</td>
						          <c:choose>
					          		<c:when test="${inserzioneCercata.prezzoAggiornato > 0}">
					          			<td id="td2" align="center">${inserzioneCercata.prezzoAggiornato } €</td>					          		
					          		</c:when>
					          		<c:otherwise>
					          			<td id="td2" align="center">${inserzioneCercata.prezzoIniziale } €</td>
					          		</c:otherwise>				          			          
					          	  </c:choose>   
						           
						          <td id="td2" align="center"><a href="ServletDettagliInserzione?idInserzione=${ inserzioneCercata.idInserzione }" target="_new"> <img src="immagini/dettagli.png" alt="Dettagli" title="Dettagli" height="30px"> </a></td>
				          
						        </tr>
					        </c:forEach>
					      </table>
				     	
				     	  <p>&nbsp;</p>
				     	  <p>&nbsp;</p>
				     	  
				     	  <p align="center">
				     	  	<
				     	  	<c:forEach var="i" begin="1" end="${ numeroPagineRicerca }">
				     	  		
				     	  		  <a style="color: #C6310C" href="ServletRicercaInserzioni?idNumPagina=${ i }" > ${ i } </a>	
				     	  	
				     	  	</c:forEach>
				     	  	>
				     	  </p>
				     	 
				     	
				     	</c:if>
				     	
			      
			      <p>&nbsp;</p>
			      <p>&nbsp;</p>			      		           
			    
			   </div>
  				
			</div>
					
		</div>
		
		<!-- colonna di destra (pubblicitÃ ) -->
		<div class="big-img" style="float:left; width:150px; height:100%; background-color:#ffffff">
		
			<p align="center" >
				<img alt="pubblicità" src="immagini/bannerAsteOnLine.jpg">
			</p>
		
		</div>
		<div style="clear:both;"></div>
		
	</div>
	<div style="clear:both;"></div>
	
	<!-- footer -->	
	
	<div class="footer">
	 
		© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="https://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>