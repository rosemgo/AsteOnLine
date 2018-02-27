<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>



<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Registrazione</title>
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
		
		<div style="float:left; width:700px; padding: 10px 0px 0px 0px;"> 
									
					
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
				  
				  	<p align="center">Inserisci i tuoi dati per registrarti</p>
    				<p>&nbsp;</p>
    				
    				<form action="ServletRegistrazione" method="post">
    				
    					<p>Nome*</p>
						<p>
							<input name="nome" type="text" maxlength="40" id="campoTesto" ></input>
						</p>
						<p>Cognome*</p>
						<p>
							<input name="cognome" type="text" maxlength="40" id="campoTesto" ></input>
						</p>
						<p>Codice fiscale*</p>
					  	<p>
					  		<input name="codFis" type="text" maxlength="16" id="campoTesto"></input>
					  	</p>
						<p>Indirizzo*</p>
						<p>
							<input name="indirizzo" type="text" maxlength="100" id="campoTesto" ></input>
						</p>
						<p>Provincia*</p>
						<p>
  						<select id="provincia"  style="height:25px; width: 250px; border: thin solid #666" title="Seleziona una provincia" size="1" name="provincia">
							<option selected="selected" value="">Seleziona una provincia</option> 
								<c:forEach items="${ applicationScope.listaProvince }" var="provincia"> 
									<option value="${provincia.idProvincia}"> ${provincia.nomeProvincia} </option> 
								</c:forEach>
						</select>
  						</p>
  						
  						<p>Città*</p>
  						<p>
						<select id="comune" disabled="disabled" style="height:25px; width: 250px; border: thin solid #666" title="Seleziona un comune" size="1" name="comune">
							<option selected="selected" value="">Seleziona Comune</option> 
						</select>
						</p>
						
						<ajax:select 
							baseUrl="${contextPath}/cercacomuni.ajax" 
							source="provincia" 
							target="comune" 							
							parameters="provincia={provincia}" />
  					 
  					 
  					 	<p>CAP*</p>
					  	<p>
						  <input name="cap" type="text" maxlength="5" id="campoTesto" ></input>
						 </p>
						<p>Telefono</p>
					  	
					  	 <p>	
						  	<input name="telefono" type="text" maxlength="10" id="campoTesto" ></input>
						  	<img style="margin: 10px 0px 0px 5px" title="Inserire solo le cifre senza separatori" src="immagini/tooltip.png" width="20px" height="20px">
					  	 </p>	
					  	 
					 	<p>Mail*</p>
					  	<p>
					  		<input name="mail" type="text" maxlength="50" id="campoTesto" />
					 	</p>
					    <p>N° Carta di Credito*</p>
					  	<p>
					  		<input name="cc" type="text" maxlength="16" id="campoTesto" />
					  	</p>
					  	<p>Nick*</p>
					  	<p>
					  		<input name="nick" type="text" maxlength="20" id="campoTesto" />
					  	</p>
					   	<p>Crea password*</p>
					  	<p>
					  		<input name="password" type="password" id="campoTesto"/>
					  	</p>
					 	<p>Conferma password*</p>
					  	<p>
					  		<input name="password2" type="password" id="campoTesto"/>
					  	</p>
					    <p>Modalità utente*:   
					  		<input type="radio" name="tipCliente"  value="venditore" />Venditore
					  		<input type="radio" name="tipCliente" value="acquirente" />Acquirente
					  	</p>
					  	<p>&nbsp;</p>	
					   	<p align="center">	
					    	<input type="submit" value="Invia" id="bottone"/>
					    	&nbsp;&nbsp;&nbsp;
					    	<input name="Ripristina" type="reset" id="bottone" value="Cancella"/>
					  	</p>
					  </form>
					  <p>&nbsp;</p>
					  <h5 align="right">*campi obbligatori &nbsp;</h5>	
					  	
				</div>	
  				
			</div>
					
		</div>
		
		<!-- colonna di destra (pubblicità) -->
		<div style="float:left; width:150px; height:auto; background-color:#ffffff">
		
			
				<p align="center">
					<img alt="pubblicità" src="immagini/bannerAsteOnLine.jpg">
				</p>
			
			
				<p align="center">
					<img alt="pubblicità" src="immagini/bannerPubblicitari/turbo.gif">
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