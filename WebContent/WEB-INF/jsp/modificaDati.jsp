<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%-- Include all the required script tags --%>
<jsp:include page="/WEB-INF/includes/ajaxscripts.jsp"/>

<title>Aste On Line - Modifica dati</title>
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
 
  					
  				<div class="centralBoxText">
				  
				  	<h3 align="center" >I miei dati</h3>
    				
    				
    				<form action="ServletModificaDati" method="post">
    				
    					<p>Nome*</p>
						<p>
							<input name="nome" type="text" maxlength="40" id="campoTesto" value="${utente.nome}"></input>
						</p>
						<p>Cognome*</p>
						<p>
							<input name="cognome" type="text" maxlength="40" id="campoTesto" value="${utente.cognome}"></input>
						</p>
						<p>Codice fiscale*</p>
					  	<p>
					  		<input name="codFis" type="text" maxlength="16" id="campoTesto" value="${utente.codiceFiscale}"/>
					  	</p>
						<p>Indirizzo*</p>
						<p>
							<input name="indirizzo" type="text" maxlength="100" id="campoTesto" value="${utente.indirizzo}"></input>
						</p>
						

						<p>Provincia</p>  						
  						<p>
  						<select id="provincia" style="height:25px; width: 250px " title="Seleziona una provincia" size="1" name="provincia">
							<option selected="selected" value="${utente.comune.provincia.idProvincia}"> ${utente.comune.provincia.nomeProvincia}</option> 
								<c:forEach items="${applicationScope.listaProvince}" var="provincia" > 
									<option value="${provincia.idProvincia}"> ${provincia.nomeProvincia} </option> 
								</c:forEach>
						</select>
  						</p>
						
						<p>Città*</p>
  						<p>
  						<select id="comune"  disabled="disabled" style="height:25px; width: 250px " title="Seleziona un comune" size="1" name="comune">
								<option selected="selected" value="${utente.comune.idComune}"> ${utente.comune.nomeComune}</option> 
						</select>
  						</p>

					  <ajax:select
					    baseUrl="${contextPath}/cercacomuni.ajax"
					    source="provincia"
					    target="comune"
					    parameters="provincia={provincia}"/>

  						<p>CAP*</p>
					  	<p>
						  <input name="cap" type="text" maxlength="5" id="campoTesto" value="${utente.cap}"></input>
						 </p>
						 <p>Telefono</p>
					  	 
					  		<div title="Inserire solo le cifre senza separatori" >
						  		<input name="telefono" type="text" maxlength="10" id="campoTesto" ></input>
						  		<img style="margin: 10px 0px 0px 5px" src="immagini/tooltip.png" width="20px" height="20px">
					  	 	</div>
					  	
					 	<p>Mail*</p>
					  	<p>
					  		<input name="mail" type="text" maxlength="50" id="campoTesto" value="${utente.eMail}"/>
					 	</p>
					    <p>N° Carta di Credito*</p>
					  	<p>
					  		<input name="cc" type="text" maxlength="16" id="campoTesto"  value="${utente.numContoCorrente}"/>
					  	</p>
					  	<p>&nbsp;</p>
					   	<p align="center">	
					    	<input type="submit" value="Invia" id="bottone"/>&nbsp;&nbsp;&nbsp;
					    	<input name="Ripristina" type="reset" id="bottone" value="Ripristina"/>
					  	</p>
					  </form>
					  <p>&nbsp;</p>
					  <h5 align="right">*campi obbligatori &nbsp;</h5>	
					  	
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
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="https://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>