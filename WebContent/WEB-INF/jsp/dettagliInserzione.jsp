<%@ page import= "it.unisannio.sweng.rosariogoglia.model.*" %>
<%@ page import= "java.util.ArrayList" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Dettagli Inserzioni</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"> 
<script type="text/javascript" src="js/jquery.countdown.js"></script>

<script type="text/javascript">
									
<%
Inserzione ins = (Inserzione) session.getAttribute("inserzione");
Date datascadenza = ins.getDataScadenza();
Calendar data = Calendar.getInstance();
data.setTime(datascadenza);		
%>
									 
var giorno = <%=data.get(Calendar.DAY_OF_MONTH) %>;
var mese = <%=data.get(Calendar.MONTH) %>; 
var anno = <%=data.get(Calendar.YEAR) %>;
var ore = <%=data.get(Calendar.HOUR_OF_DAY) %>;
var minuti = <%=data.get(Calendar.MINUTE) %>;
var secondi = <%=data.get(Calendar.SECOND) %>;
							
									
$(function () { 
	$('#contoAllaRovescia').countdown({until: new Date(anno, mese, giorno, ore, minuti, secondi)}); 
}); 
									
</script> 								

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 

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
  				  	
  				  
  				  	
  				  					
  				<div class="centralBoxTextInserzione">
				    <h2 align="center">Dettagli Inserzione</h2>
				    <p>&nbsp;</p>
				    <p>&nbsp;</p>
				    
				    
				     <div style="margin-left:200px; margin-left:200px;">
				    
				    	<ul id="gallery">
						    <c:forEach items="${ inserzione.immagini }" var="immagine" > 
		      						<li>
										<a href="#">
											<img src=" ${ immagine.foto }" alt="Miniatura immagine inserzione" width="100px" height="75px" />
											<span><img alt="Immagine" src=" ${ immagine.foto }" alt="Immagine inserzione" width="400px" height="300px"/></span>
										</a>
									</li>
		      				</c:forEach>
				     	</ul> 
				    
<!-- 				    <ul id="gallery"> -->
					    
<%-- 					    <c:forEach items="${inserzione.immagini}" var="immagine" >  --%>
<%-- 	 						<p align="center"><img alt="Immagine" src=" ${ immagine.foto }" width="300px" height="200px"></p>  --%>
	      					
	    							
<!-- 									<li> -->
<!-- 										<a href="#"> -->
<%-- 											<em><img src=" ${ immagine.foto }" alt="Miniatura immagine inserzione" width="100px" height="75px" /></em> --%>
<%-- 											<span><img alt="Immagine" src=" ${ immagine.foto }" alt="Immagine inserzione" width="400px"/></span> --%>
<!-- 										</a> -->
<!-- 									</li> -->
									
							    				
	      				
<%-- 	      				</c:forEach> --%>
				    
<!-- 				    </ul>    -->
				    
				    </div>
				    <div style="clear: both"></div>   
				      
				       
				    <p>&nbsp;</p>
				   
				   	
				   	<div style="float:left; margin-left: auto; margin-right: auto;" >
						<table  style="margin-left:10px; margin-right:auto"  border="0" cellspacing="5" cellpadding="0" align="center">
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
				          
				           <div style="float:right; width:200px; height:200px; overflow-y: scroll; border:1px solid black; margin-left: auto; margin-right: 10px; font-size: small; font-weight: normal">
					 			
								 <table width="100%" border=0>
								 	<tr>
								 		<td id="td2">Nick</td>
								 		<td id="td2">Offerta</td>
								 	</tr>
								 	<c:forEach items="${inserzione.offerte}" var="offerta">
								 		<tr>
								 			<td width="150px">${offerta.utente.nick}</td>
								 			<td width="50px">${offerta.somma} €</td> 
								 		</tr>
								 	</c:forEach>
								 </table>
							</div>
							<div style="clear: both;"></div>
				           
				            <p>&nbsp;</p>
				            
				            <p align="center" style="font-size:16pt; color: #FF0000">L' inserzione scadrà tra: </p>
					        <div id="contoAllaRovescia" style="margin-left: auto; margin-right: auto;"></div>
				            
				            
				  			<p>&nbsp;</p>
				  			<p>&nbsp;</p>
				 			<c:if test="${ (inserzione.venditore.nick ne utente.nick) && (inserzione.stato eq 'in asta')}"> 
					  			<p align="center">
					  				<c:if test="${ !giaOsservata }"> 
					  					<a href="ServletOsservaInserzione?idInserzione=${inserzione.idInserzione}" target="_new"><strong> Osserva Inserzione </strong></a>
					  					&nbsp;
					  					&nbsp;
					  				</c:if>
					  				
					  				<a href="ServletControlloOfferta?idInserzione=${inserzione.idInserzione}" target="_new"><strong> Fai offerta </strong></a>  
						        </p>  	
				  			</c:if>
				  			 
				  			<p>&nbsp;</p>			  			
				  			<p align="center"><input type="button" onClick="window.close();"  value=" CHIUDI DETTAGLI " id="bottone1" /></input></p>
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