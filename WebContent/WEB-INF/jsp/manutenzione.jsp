<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AsteOnLine - Manutenzione</title>
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>
<body>
	<div class="container">
		
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
		<div class="centralBox"> 
			
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p align="center"><strong><img src="immagini/manutenzione.jpg"/></strong></p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			
    	</div>
    	
    	<div class="footer">
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
		</div>
		
    </div>
</body>
</html>