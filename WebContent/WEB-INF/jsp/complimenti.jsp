<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Complimenti</title>
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
									
			
			<c:if test="${ utente!=null }"> 
                    
                     <div class="menuUtente">  				
						
						<div style="float:right;  padding: 0px 0px 0px 0px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">				 
							Benvenuto ${utente.nick} |
						</div> 
						<div style="clear: both;"></div>
						<div style="float:right;  padding: 7px 0px 0px 0px;">  
						  <c:choose>
						  <c:when test="${utente.tipologiaCliente eq 'venditore' }">
						 	 <a href="ServletDettagliUtente"><strong>I Miei Dati |</strong></a>
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
							  <a href="amministrazione.jsp"><strong>Home |</strong></a>
							  <a href="ServletUtenti"><strong>Utenti |</strong></a>
							  <a href="ServletProdotti"><strong>Prodotti |</strong></a>
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
  				<p>&nbsp;</p> 					
  			 				  					
  				<div class="centralBoxOfferta">
			     	
			     
			      	<h2 align="center">Riepilogo d'acquisto</h2>
				    
				   
				    <div style="float: left; height:150px; weigh:auto; padding-top: 5px; padding-bottom: 5px; padding-right: 20px; padding-left: 20px" align="center"><img alt="Immagine" src="${inserzioneOfferta.immagini[0].foto }" WIDTH="200" HEIGHT="150" ></div>
      				
      				<div style="float: left; height:160px" align="center">
      				
      						<table width="auto" height="auto" border="0" cellspacing="5" cellpadding="5" >
				               <tr>
						        <td width="200" id="td1">Titolo Inserzione :</td>
						        <td width="100" id="campoTesto2">${ inserzioneOfferta.titolo }</td>
				               </tr>
				               <tr>
				                <c:choose>
					              	<c:when test="${inserzioneOfferta.stato eq 'in asta' }">
						              	<tr>
							              	<td width="70" id="td1">Prezzo attuale:</td>
							              	<td width="50" id="campoTesto2">${ inserzioneOfferta.prezzoAggiornato} €</td>
						              	</tr>
						              	<tr>
								          	<td width="80" id="td1">Data scadenza:</td>
								           	<td width="50" id="campoTesto2">${ inserzioneOfferta.dataScadenzaString}</td>
							            </tr>
					              	</c:when>
					              	<c:when test="${inserzioneOfferta.stato eq 'pagata' || inserzioneOfferta.stato eq 'aggiudicata'}">
						              	<tr>
							              	<td width="70" id="td1">Prezzo finale:</td>
							              	<td width="50" id="campoTesto2">${ inserzioneOfferta.prezzoAggiornato } €</td>
						              	</tr>
						              	<tr>
							              	<td width="80" id="td1">Aggiudicato il:</td>
							              	<td width="50" id="campoTesto2">${ inserzioneOfferta.dataScadenzaString}</td>
						              	</tr>
					              	</c:when>
              					</c:choose>	
				              				            
				              </tr>
				              <tr>
				                <td width="200" id="td1">Venditore:</td>
				                <td width="100" id="campoTesto2">${inserzioneOfferta.venditore.nome}  ${inserzioneOfferta.venditore.cognome}</td>
				              </tr>
				              				              
				           </table>
      				
      				
      				
      				</div>
			      	<div style="clear: both;"></div>
			      
			      <p>&nbsp;</p>

			      <p align="center"> <a href="ServletDettagliInserzione?idInserzione=${ inserzioneOfferta.idInserzione}" target="_new"><strong> Visualizza dettagli inserzione </strong></a> 
			      
			      <p>&nbsp;</p>
			      
			       	
 				<hr width=70% size=1 color=000000>
 				
 				<p>&nbsp;</p>
 					<h3 align="center">Riepilogo informazioni Acquirente</h3>
 				<p>&nbsp;</p>
      	
      			<table style="margin-left: auto; margin-right: auto" width="600" border="0" cellspacing="5" cellpadding="5">
	             <tr>
	              	<td width="60" id="td1">Nome:</td>
	                <td width="100" id="campoTesto2">${ utente.nome }</td>
	                <td width="70" id="td1">Cognome:</td>
	                <td width="100" id="campoTesto2">${ utente.cognome }</td>
	             </tr>
	             <tr>
	                <td width="170" id="td1">Codice Fiscale:</td>
	                <td width="100" id="campoTesto2">${ utente.codiceFiscale }</td>
	                <td width="170" id="td1">Telefono:</td>
	                <td width="100" id="campoTesto2">${ utente.telefono }</td>
	                
	              </tr>
	              <tr>
	                <td width="170" id="td1">Indirizzo:</td>
	                <td width="100" id="campoTesto2">${ utente.indirizzo }</td>
	                <td width="170" id="td1">Città:</td>
	                <td width="100" id="campoTesto2">${ utente.comune.nomeComune }</td>
	                
	              </tr>
	              <tr>
	                <td width="170" id="td1">Provincia:</td>
	                <td width="100" id="campoTesto2">${ utente.comune.provincia.nomeProvincia }</td>
	                <td width="170" id="td1">CAP:</td>
	                <td width="100" id="campoTesto2">${ utente.cap }</td>
	                
	              </tr>
            	</table>
				
				
			      
			      
			    
			   </div>
  				
			</div>
					
		</div>
		
		<!-- colonna di destra (pubblicitÃ ) -->
		<div style="float:left; width:150px; height:100%; background-color:#ffffff">
		
			<p align="center" >
				<img alt="pubblicità" src="immagini/bannerAsteOnLine.jpg">
			</p>
		
		</div>
		<div style="clear:both;"></div>
		
	</div>
	<div style="clear:both;"></div>
	
	<!-- footer -->	
	
	<div class="footer">
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>