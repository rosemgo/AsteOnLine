<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Amministrazione</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<body>

<div class="container">

<!-- Intestazione (logo e login)-->
	<div class="header"> 
		
		<div id="logo"> 
			<a id="logo" href="amministrazione.jsp"></a>
		</div>
		
		<div style="float:left; width:700px; padding: 10px 0px 0px 0px;"> 
			
			<c:if test="${ utente != null }">
                    <div class="menuUtente"> 	
                    	                    	
						<div style="float:left; width:100%px; padding: 0px 0px 0px 0px;">  
						   	 <font style="font-family: Trebuchet MS, Arial, Helvetica, sans-serif;">Benvenuto ${utente.nick} |</font>
						   	 <a href="amministrazione.jsp"><strong>Home |</strong></a>
							 <a href="categorie.jsp"><strong>Categorie |</strong></a>
							 <a href="produttori.jsp"><strong>Produttori |</strong></a>
							 <a href="prodotti.jsp"><strong>Prodotti |</strong></a>
							 <a href="ServletListaUtenti"><strong>Utenti |</strong></a>
							 <a href="ServletListaInserzioni"><strong>Inserzioni |</strong></a>
						   	 <a href="ServletLogout"><strong>Logout |</strong></a>
						</div>	
                    							
				 	</div>
			</c:if>	
		</div>
		
	</div>
    	
    	
	<!-- contenitore esterno-->

	<div class="centralBox"> 
		
		<div class="leftBox">
		 	<h2 align="center">Modalità Sito</h2>
		 	
		 	<c:choose>
		 		<c:when test="${ applicationScope.inManutenzione }" >
		 			<c:set var="statoSito" value="OFF-LINE" scope="session" />
		 			<c:set var="colore" value="#FF0000" scope="session" />
		 		</c:when>
		 		<c:otherwise>
		 			<c:set var="statoSito" value="ON-LINE" scope="session" />
		 			<c:set var="colore" value="#00FF00" scope="session" /> 
		 		</c:otherwise>
		 	</c:choose>
		 	<h4 align="center"> Stato: <font size="4" color="${ colore }" > ${ statoSito } </font></h4>
		 	&nbsp;
		 	
			<form action="ServletSetStatoSito" method="post">
				<p align="center"> 
					<input type="submit" value="ON-LINE" id="bottone" name="statoSito">
					<input type="submit" value="OFF-LINE" id="bottone" name="statoSito">
				</p>
			</form>
			
		</div>
		
		<div class="rightBox">
		
			<h2 align="center">Aggiorna scadenze aste</h2>
			&nbsp;
			<form action="ServletAggiornaScadenzaAste" method="get"> <!--il metodo sarà get perchè non c'è un passaggio di paramentri! 
																			la scadenza delle aste presenti nel sito dalla servlet in questione -->
				<p align="center">
					<input type="submit" value="Aggiorna" id="bottone">
				</p>
								
			</form>
			
		</div>
		
		<p>&nbsp;</p> 
		
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