<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Login</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function () {
  setInterval(rotateImage, 4000);
  var images = new Array('immagini/bannerAsteOnLine.jpg', 'immagini/pubblicita.png', 'immagini/bannerPubblicitari/italiapubblicita.png');
  var index = 1;
 
  function rotateImage() {
    $('.big-img img').fadeOut('slow', function () {
      $(this).attr('src', images[index]);
      $(this).fadeIn('slow', function () {
        if (index == images.length - 1) {
          index = 0;
        } else {
          index++;
        }
      });
    });
  }
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
  				<p>&nbsp;</p>
  					
  				<div class="centralBoxLogin">
				  	<p>&nbsp;</p>
					<p>Accedi con i dati definiti in fase di registrazione: </p>
					&nbsp;
					<form action="ServletLogin" method="post">
					    <p>Nick:</p>
					    <p>
					      <input align="middle" type="text" name="nick" title="Inserisci l' username" placeholder="Nickname" id="campoTesto" />
					      <br />
					    </p>
					    <p>Password:</p>							    
					    <p>
					 	 <input align="middle" type="password" name="password" title="Inserisci la password" placeholder="Password" id="campoTesto" /><br /><br />
						
						 <p>&nbsp;</p>
											          
					     <input type="submit" value=" ACCEDI " id="bottone"></input>
					     &nbsp;&nbsp;&nbsp;
					     <input type="button" onclick ="location.href='registrazione.jsp';" value=" REGISTRATI " id="bottone" />
					    </p>
					</form>
				    
				    <p>&nbsp;</p>
				    
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
