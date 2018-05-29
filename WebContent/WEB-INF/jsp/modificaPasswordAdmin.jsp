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
				  
				  	<h3 align="center" >Modifica Password</h3>
    				<p>&nbsp;</p>
    				    				
					 <form action="ServletModificaPassword" method="post" > 	
					    <p>&nbsp;</p>
					 
					    <p>Vecchia password*</p>
					  	<p>
					    	<input name="oldPsw" type="password" id="campoTesto"/>
					  	</p>
					     <p>Nuova password*</p>
					  	<p>
					    	<input name="psw" type="password" id="campoTesto"/>
					  	</p>					  
					  	<p>Conferma nuova password*</p>
					  	<p>
					    	<input name="psw2" type="password" id="campoTesto"/>
					  	</p>
					  <p>&nbsp;</p>
					  
					  <p align="center">
					   <input type="submit" value="Invia" id="bottone"/>&nbsp;
					   <input name="Cancella" type="reset" id="bottone" value="Cancella"/>
					  </p>
					  
					  <p>&nbsp;</p>
 
					</form>
					<p>&nbsp;</p>
					<h5 align="right">*campi obbligatori &nbsp;</h5>	
					  	
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