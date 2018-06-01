<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Login Amministratore</title>
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
		
		
				
		
	</div>
    	
    	
	<!-- contenitore esterno-->

	<div class="centralBox"> 
		
		<p>&nbsp;</p>
		<p>&nbsp;</p>
  		<p align="center"><font size="4">${ messaggio }</font></p>
		
		<div class="centralBoxLogin">
				  	<p>&nbsp;</p>
					<p>Accedi come amministratore: </p>
					&nbsp;
					<form action="ServletLoginAmministratore" method="post">
					    <p>Nick:</p>
					    <p>
					      <input align="middle" type="text" name="nick" title="Inserisci l' username" placeholder="Nickname" id="campoTesto" />
					      <br />
					    </p>
					    <p>Password:</p>							    
					    <p>
					 	 <input align="middle" type="password" name="password" title="Inserisci la password" placeholder="Password" id="campoTesto" /><br /><br />
						</p>
						<p align="center">					          
					     <input type="submit" value=" ACCEDI " id="bottone"></input>
					    </p>
					    
					</form>
				    				    
				</div>	
		
		
		<div class="immagineAreaRiservata">
			<img src="immagini/areariservata2.jpg" />		
		</div>
		
		<!-- AGGIUNGERE FORM DEL CONTROLLO SCADENZA, METTERE ENTRAMBI CENTRALI DA UN LATO STATO SITO, DALL'ALTRO LA SCADENZA -->
		
		
	</div>
	<div style="clear:both;"></div>
	
	<!-- footer -->	
	
	<div class="footer">
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>