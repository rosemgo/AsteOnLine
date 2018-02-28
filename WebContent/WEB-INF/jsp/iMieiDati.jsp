<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - iMieiDati</title>
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
  				
  					
  				<div class="centralBoxText">
				  	
				  	<h2 align="center" >I miei dati</h2>
    				
				  	<p align="center"> 
				  		<table width="700" border="0" cellspacing="20" cellpadding="5">
				  		  
				  		  <tr>
				  		  <c:choose>
					          	<c:when test="${isBanned}">
					          		<td id="td1" align="center"> <font color="#FF0000">Bannato</font></td>	
					          	</c:when>
					          	<c:otherwise>
					          		<td id="td1" align="center"> <font color="#00FF00">Non Bannato</font></td>	
					          	</c:otherwise>
					      </c:choose>
				  		  </tr>
				  		  
				  		  <tr>
			                <td width="120" id="td1">Codice Fiscale:</td>
			                <td width="579" id="campoTesto2">${utente.codiceFiscale }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Nome:</td>
			                <td width="579" id="campoTesto2">${utente.nome }</td>
			              </tr>
			             <tr>
			                <td width="106" id="td1">Cognome:</td>
			                <td width="579" id="campoTesto2">${utente.cognome }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Indirizzo:</td>
			                <td width="579" id="campoTesto2">${utente.indirizzo }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Comune:</td>
			                <td width="579" id="campoTesto2">${ utente.comune.nomeComune }  (${ utente.comune.provincia.nomeProvincia })</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">CAP:</td>
			                <td width="579" id="campoTesto2">${utente.cap }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Telefono:</td>
			                <td width="579" id="campoTesto2">${utente.telefono }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">N° Carta di Credito:</td>
			                <td width="579" id="campoTesto2">${utente.numContoCorrente}</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">eMail:</td>
			                <td width="579" id="campoTesto2">${utente.eMail }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Nick:</td>
			                <td width="579" id="campoTesto2">${utente.nick }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Password:</td>
			                <td width="579" id="campoTesto2">${utente.password}</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Modalità Utente:</td>
			                <td width="579" id="campoTesto2">${utente.tipologiaCliente }</td>
			              </tr>
				  		</table>
				  	  
				  	  <p>&nbsp;</p>
				     
				  	  
				  	<p align="center">
				  		<input type="button" value="Modifica dati" id="bottone1" onclick="location.href='ServletControlloModificaDati';"/></input>
				  		&nbsp;&nbsp;&nbsp;
				  		<input type="button" value="Modifica Password" id="bottone1" onclick="location.href='ServletControlloModificaPassword';"/></input>
				  	</p>
				  	
				  	 <p>&nbsp;</p>
				  				  	
				</div>	
  				
			</div>
					
		</div>
		
		<!-- colonna di destra (pubblicità) -->
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
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/rosario.goglia" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>