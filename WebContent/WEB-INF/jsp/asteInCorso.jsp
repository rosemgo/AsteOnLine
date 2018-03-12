<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Aste In Corso</title>
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
			<a id="logo" href="index"></a>
		</div>
		
<!-- 		<div id="logo">  -->
			
<!-- 		</div> -->
		
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
			
			<!-- Barra di ricerca -->
			<div class="searchBar">
				
				<form action="ServletRicercaInserzioni" style="padding: 0px 0px 0px 0px;" method="get">
									 
					<div style="float:left; height:25px; padding: 0px 5px 0px 0px;"> 
						<input id="autoKeyword" style="height:20px" type="text" size="50" maxlength="300" title="Inserisci la parola chiave di ricerca" placeholder="Parola chiave" value="" name="parola_chiave" > 
					</div>


					<div style="float:left; padding: 0px 5px 0px 0px; ">
						<select style="height:25px;" title="Seleziona una categoria per la ricerca" size="1" name="categoria">
							<option selected="selected" value="0">Tutte le categorie</option> <!-- PER IL METODO RICERCA INSERZIONE, TUTTE LE CATEGORIE è 0 O NULL??? -->
								<c:forEach items="${applicationScope.listaCategorie}" var="categoria" > 
									<option value="${categoria.idCategoria}"> ${categoria.nome} </option> <!-- COME INVIARE L'ID CATEGORIA ALLA SERVLET PER FARE LA RICERCA??? -->
								</c:forEach>
						</select>
					</div>
					<div style="float:left; padding: 0px 5px 0px 0px;"> <input id="bottone-Cerca" style="display:inline;" type="submit" value="Cerca"> </div> 
					<div style="float:left; padding-top:4px;"> <a style="font-size:small; color:#0000FF;" href="ricercaAvanzata.jsp" title="Ricerca avanzata">Ricerca avanzata</a></div>
					<div style="clear:both;"></div> 
					
				</form>
				
				<ajax:autocomplete
					source="autoKeyword"
					target="autoKeyword"
					baseUrl="${contextPath}/autocompleteKeyword.ajax"
					parameters="keywordPrefix={autoKeyword}"
					className="autocompletamentokey"
					minimumCharacters = "1"/>
				
				
				
			</div>
			<div style="clear:both;"></div>
			
			<!-- sottocolonna di sinistra -->
		<div class="asteInCorso">
				
				
				
				<!-- contenitore delle inserzioni più popolari--> 
				<div style="float:left; width:100%; height:100%; padding: 5px 7.5px 5px 7.5px; background-color: #ffffff;">
				
					<div id="inserzione">
							<div id="inserzioneTitolo"> ${intervalloAsteInCorso[0].titolo} </div>
							<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[0].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[0].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
							<div id="inserzionePrezzo"> 
								
								<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[0].prezzoAggiornato > 0}">
										 
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[0].idInserzione}">
										 		<span>${intervalloAsteInCorso[0].prezzoAggiornato} € </span>
										 		</br>
									 			<span style="font-size: 18px">Fai offerta </span>
										 	</a>
										 
										 	 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[0].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[0].idInserzione }"> 
											 	<span> ${intervalloAsteInCorso[0].prezzoIniziale} €</span>
									 			</br>
									 			<span style="font-size: 18px">  Fai offerta </span>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
								
							</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[1].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[1].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[1].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  	
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[1].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[1].idInserzione}">
										 		<div>${intervalloAsteInCorso[1].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[1].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[1].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[1].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[2].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[2].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[2].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[2].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[2].idInserzione}">
										 		<div>${intervalloAsteInCorso[2].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[2].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[2].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[2].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[3].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[3].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[3].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  	
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[3].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[3].idInserzione}">
										 		<div>${intervalloAsteInCorso[3].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[3].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[3].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[3].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div style="clear:both;"></div>
					
					<!-- tre riquadri alla seconda riga-->				
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[4].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[4].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[4].immagini[0].foto}" width="100%px" height="120px" /> </a> </div>
						<div id="inserzionePrezzo"> 
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[4].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[4].idInserzione}">
										 		<div>${intervalloAsteInCorso[4].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[4].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[4].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[4].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[5].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[5].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[5].immagini[0].foto}" width="100%px" height="120px" /> </a> </div>
						<div id="inserzionePrezzo"> 
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[5].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[5].idInserzione}">
										 		<div>${intervalloAsteInCorso[5].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[5].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[5].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[5].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>	
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[6].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[6].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[6].immagini[0].foto}" width="100%px" height="120px" /> </a> </div>
						<div id="inserzionePrezzo"> 
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[6].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[6].idInserzione}">
										 		<div>${intervalloAsteInCorso[6].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[6].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[6].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[6].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					
					
					<div id="inserzione">
							<div id="inserzioneTitolo"> ${intervalloAsteInCorso[7].titolo} </div>
							<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[7].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[7].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
							<div id="inserzionePrezzo"> 
								
								<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[7].prezzoAggiornato > 0}">
										 
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[7].idInserzione}">
										 		<span>${intervalloAsteInCorso[7].prezzoAggiornato} € </span>
										 		</br>
									 			<span style="font-size: 18px">Fai offerta </span>
										 	</a>
										 
										 	 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[7].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[7].idInserzione }"> 
											 	<span> ${intervalloAsteInCorso[7].prezzoIniziale} €</span>
									 			</br>
									 			<span style="font-size: 18px">  Fai offerta </span>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
								
							</div>
					</div>
					
					<div style="clear:both;"></div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[8].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[8].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[8].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  	
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[8].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[8].idInserzione}">
										 		<div>${intervalloAsteInCorso[8].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[8].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[8].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[8].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[9].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[9].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[9].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[9].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[9].idInserzione}">
										 		<div>${intervalloAsteInCorso[9].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[9].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[9].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[9].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
						
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[10].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[10].idInserzione} " target="_new"> <img src="${intervalloAsteInCorso[10].immagini[0].foto}" width="100%px" height="100%px" /> </a> </div>
						<div id="inserzionePrezzo">  	
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[10].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[10].idInserzione}">
										 		<div>${intervalloAsteInCorso[10].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[10].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[10].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[10].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div id="inserzione">
					
						<div id="inserzioneTitolo"> ${intervalloAsteInCorso[11].titolo} </div>
						<div id="inserzioneImmagine"> <a href="ServletDettagliInserzione?idInserzione=${intervalloAsteInCorso[11].idInserzione}" target="_new"> <img src="${intervalloAsteInCorso[11].immagini[0].foto}" width="100%px" height="120px" /> </a> </div>
						<div id="inserzionePrezzo"> 
							<div class="button">
									<c:choose>
										 <c:when test="${intervalloAsteInCorso[11].prezzoAggiornato > 0}">
										 	<a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[11].idInserzione}">
										 		<div>${intervalloAsteInCorso[11].prezzoAggiornato} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
										 	</a> 
										 </c:when>
										 <c:when test="${intervalloAsteInCorso[11].prezzoAggiornato <= 0}">
											 <a class="bottoneInserzione" href="ServletControlloOfferta?idInserzione=${intervalloAsteInCorso[11].idInserzione }"> 
											 	<div> ${intervalloAsteInCorso[11].prezzoIniziale} €</div>
									 			<div style="font-size: 18px">  Fai offerta </div>
											 </a>
										 </c:when>
										 <c:otherwise>
										 	
										 </c:otherwise>
										 
									</c:choose>
								</div>
						</div>
					</div>
					
					<div style="clear:both;"></div>
								
				</div>
				
				<p>&nbsp;</p>
			        
			    <p align="center">
				  	<
				  	<c:forEach var="i" begin="1" end="${numeroPagineAsteInCorso}">
				     	  		
						  <a style="font-size: normal; color: #C6310C" href="ServletAsteInCorso?idNumPagina=${ i }" > ${ i } </a>	
				     	  	
				    </c:forEach>
				    >
				</p>
			
			
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
	 	
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="https://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 	
	</div>


</div>


</body>
</html>