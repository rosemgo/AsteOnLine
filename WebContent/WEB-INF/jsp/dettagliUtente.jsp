<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Dettagli Utente</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>

<jsp:useBean id="homeBean" scope="session" class="it.unisannio.sweng.rosariogoglia.bean.HomeBean" />

<body>

<div class="container">

<!-- Intestazione (logo e login)-->
	<div class="header"> 
		
		<div id="logo"> 
			<a id="logo" href="index"></a>
		</div>
		
		<div style="float:left; width:700px; padding: 10px 0px 0px 0px;"> 
			
                    <div class="menuUtente"> 	
                    		
                    	<div style="float:left; width:30%px; padding: 0px 0px 0px 0px; font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">				 
						  	Benvenuto ${utente.nick}|
						</div> 
						<div style="float:left; width:70%px; padding: 0px 0px 0px 0px;">  
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
		
		<div class="centralBoxAmministrazioneNoBorder">
		
			<div class="centralBoxText">
		
		
				<p>&nbsp;</p>
				     	 <h2 align="center">Dettagli Utente</h2>
				<p>&nbsp;</p>
				     
				<p align="center"> 
				  		<table width="700" border="0" cellspacing="20" cellpadding="0">
				  		  
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
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.codiceFiscale }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Nome:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.nome }</td>
			              </tr>
			             <tr>
			                <td width="106" id="td1">Cognome:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.cognome }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Indirizzo:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.indirizzo }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Comune:</td>
			                <td width="579" id="campoTesto2">${ utenteDaVisualizzare.comune.nomeComune }  (${ utente.comune.provincia.nomeProvincia })</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">CAP:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.cap }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Telefono:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.telefono }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">N° Carta di Credito:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.numContoCorrente}</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">eMail:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.eMail }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Nick:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.nick }</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Password:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.password}</td>
			              </tr>
			              <tr>
			                <td width="106" id="td1">Modalità Utente:</td>
			                <td width="579" id="campoTesto2">${utenteDaVisualizzare.tipologiaCliente }</td>
			              </tr>
				  		</table>   
			   					
			</div>
				
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