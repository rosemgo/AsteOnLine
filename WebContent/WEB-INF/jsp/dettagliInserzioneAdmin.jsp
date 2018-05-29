<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Dettagli Inserzione Admin</title>
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
		
		<p>&nbsp;</p>
 		<p align="center"><font size="4">${ messaggio }</font></p>
 
		
		<div class="centralBoxAmministrazione">
			
				
				   <h2 align="center">Dettagli Inserzione</h2>
				    <p>&nbsp;</p>
				    <p>&nbsp;</p>
				    
				    <div style="margin-left: 300px; margin-right: 300px; " >
				    
				    <ul id="gallery">
						<c:forEach items="${inserzione.immagini}" var="immagine" > 
		      				<li>
								<a href="#">
									<img src=" ${ immagine.foto }" alt="Miniatura immagine inserzione" width="100px" height="75px" />
									<span><img alt="Immagine" src=" ${ immagine.foto }" alt="Immagine inserzione" width="400px" height="300px"/></span>
								</a>
							</li>
		      			</c:forEach>
				     </ul>   
				    
				    </div>
				    <div style="clear: both"></div>    
				  
				    <p>&nbsp;</p>
				    <div style="float:left; margin-left: 150px; margin-right: auto;" >
					 <table  style="margin-left:auto; margin-right:auto"  border="0" cellspacing="5" cellpadding="0" align="center">
					              <tr>
					                <td id="td1">Titolo:</td>
					                <td id="campoTesto2">${ inserzione.titolo }</td>
					              </tr>
					               <tr>
					                <td id="td1">Prodotto:</td>
					                <td id="campoTesto2">${ inserzione.prodotto.nome}</td>
					              </tr>
					              <tr>
					                <td id="td1">Categoria:</td>
					                <td id="campoTesto2">${ inserzione.prodotto.categoria.nome}</td>
					              </tr>
					              <tr>
					                <td id="td1">Descrizione:</td>
					                <td id="campoTesto2">${ inserzione.descrizione }</td>
					              </tr>
					              <tr>
					                <td id="td1">Prezzo Iniziale:</td>
					                <td id="campoTesto2">${ inserzione.prezzoIniziale } €</td>
					              </tr>
					              <c:choose>
					              	<c:when test="${inserzione.stato eq 'aggiudicata' || inserzione.stato eq 'pagata'}">
					              		<tr>
					                		<td id="td1">Prezzo Finale:</td>
					                		<td id="campoTesto2">${ inserzione.prezzoAggiornato} €</td>
					               		</tr>
					                </c:when>
					                <c:when test="${ inserzione.prezzoAggiornato > 0  && inserzione.stato eq 'in asta'}">
					                	<tr>
							                <td id="td1">Prezzo Attuale:</td>
							                <td id="campoTesto2">${ inserzione.prezzoAggiornato} €</td>
						               	</tr>
					                </c:when>
					                <c:otherwise></c:otherwise>
					              </c:choose>
					             
					              <tr>
					                <td id="td1">Venditore:</td>
					                <td id="campoTesto2">${ inserzione.venditore.nick  }</td>
					              </tr>
					              <c:if test="${inserzione.stato eq 'aggiudicata' || inserzione.stato eq 'pagata' }">
					             	<tr>
						                <td id="td1">Aggiudicato da: </td>
						                <td id="campoTesto2">${ inserzione.acquirente.nick }</td>
					              	</tr>
					              </c:if>
					              <tr>
					                <td id="td1">Data Fine Asta:</td>
					                <td id="campoTesto2">${ inserzione.dataScadenzaString }</td>
					              </tr>
					            </table>
				            </div>
				  			
				 			<div style="float:right; width:200px; height:225px; overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: 150px; font-size: small; font-weight: normal">
					 			
								 <table width="100%" border=0>
								 	<tr>
								 		<td id="td2">Nick</td>
								 		<td id="td2">Offerta</td>
								 	</tr>
								 	<c:forEach items="${inserzione.offerte}" var="offerta">
								 		<tr>
								 			<td >${offerta.utente.nick}</td>
								 			<td >${offerta.somma} €</td> 
								 		</tr>
								 	</c:forEach>
								 </table>
							</div>
							<div style="clear: both;"></div>
				  			 
				  			<p>&nbsp;</p>			  			
				  			<p align="center"><input type="button" onClick="window.close();"  value=" CHIUDI DETTAGLI " id="bottone1" /></input></p>
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