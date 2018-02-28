<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Le mie inserzioni</title>
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
							  <a href="amministrazione.jsp"><strong>Home |</strong></a>
							  <a href="ServletUtenti"><strong>Utenti |</strong></a>
							  <a href="ServletProdotti"><strong>Prodotti |</strong></a>
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
  				  					
  				<div class="centralBoxOfferta">
			     	
			     
			      	<h2 align="center" >Offerta di partecipazione all'asta</h2>
				    
				   
				    <div style="float: left; height:150px; weigh:auto; padding-top: 5px; padding-bottom: 5px; padding-right: 20px; padding-left: 20px" align="center"><img alt="Immagine" src="${inserzioneOfferta.immagini[0].foto }" WIDTH="200" HEIGHT="150" ></div>
      				
      				<div style="float: left; height:160px" align="center">
      				
      						<table  width="auto" height="auto" border="0" cellspacing="5" cellpadding="5" >
				               <tr>
				                <td width="200" id="td1">Prezzo:</td>
					            <c:choose>
						            <c:when test="${inserzioneOfferta.prezzoAggiornato > 0 }">
						              <td width="100" id="campoTesto2">${ inserzioneOfferta.prezzoAggiornato } €</td>
				   	            	</c:when>
						            <c:otherwise>
								   	  <td width="100" id="campoTesto2">${ inserzioneOfferta.prezzoIniziale } €</td>
									</c:otherwise>
				  				</c:choose>
					            
				              </tr>
				              <c:choose>
					              <c:when test="${inserzioneOfferta.acquirente != null}">
						              <tr>
						                <td width="200" id="td1">Ultimo offerente :</td>
						                <td width="100" id="campoTesto2">${inserzioneOfferta.acquirente.nick}</td>
						              </tr>
					              </c:when>
				              </c:choose>
				              <tr>
				                <td width="200" id="td1">Venditore:</td>
				                <td width="100" id="campoTesto2">${inserzioneOfferta.venditore.nome}  ${inserzioneOfferta.venditore.cognome}</td>
				              </tr>
				              
				              <tr>
				                <td width="200" id="td1">Fine Asta:</td>
				                <td width="100" id="campoTesto2">${inserzioneOfferta.dataScadenzaString}</td>
				              </tr>
				           </table>
      				
      				
      				
      				</div>
			      	<div style="clear: both;"></div>
			      
			      	
<!-- 			     METTERE IL DIV SEGUENTE A CENTRO PAGINA!!! -->
			      	<div align="center" style="padding-top: 20px; padding-bottom: 20px;">
       
					      <form action="ServletOfferta" method="post" >
					      		
					      		<input name="idInserzioneForm" type="hidden" value="${ inserzioneOfferta.idInserzione }" maxlength="7" id="campoTesto4"/>
					     		
					      		
					      		<p>Importo Offerta: 
					  			<input name="sommaOfferta" type="text" maxlength="7" id="campoTesto4"/>
					  			€
					  			</p><br />
					     		<p>	
					     		
					     		
					    		&nbsp;&nbsp;&nbsp;<input type="submit" value="Fai l'Offerta" id="bottone"/>&nbsp;&nbsp;&nbsp;
					   			<input name="Ripristina" type="reset" id="bottone" value="Cancella"/>
					 			
					 			</p>
					      </form>
	      
 					</div>
			      
			      
			      
			    
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
	 
© 2013 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/rosario.goglia" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>