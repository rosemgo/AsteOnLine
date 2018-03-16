<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Pagamenti</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<!-- 
<script type="text/javascript"
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4LDc9ZWLw5wAeIgn1TZLG8stdzldHINg&sensor=false">
</script>

<script type="text/javascript">
      function initialize() {
        
    	  var myLatlng = new google.maps.LatLng(41.175170117362384, 14.638466835021973);
    	  
    	  var mapOptions = {
          center: myLatlng,
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR}
        };
        
    	  var mymap = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        
     // definizione segnapunto
        var marker = new google.maps.Marker({
           position: myLatlng,
           map: mymap,
           title:"BELFROS Company S.p.a",
        });
     
        var contentString = '<em>Via Santa Croce 81 </br> 82038 Vitulano </br> tel. 0824870461 </br> ROSBELF Company Spa </em>';
        var infoWindow = new google.maps.InfoWindow({
            content: contentString
        });
    	
        google.maps.event.addListener(marker, 'click', function() {
            infoWindow.open(mymap,marker);
        }); 
     
     
      }
      google.maps.event.addDomListener(window, 'load', initialize);
</script>

-->

<body>

<div class="containerContatti">

<!-- Intestazione (logo e login) -->
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
    	
    	
	<!-- contenitore esterno -->

	<div class="centralBoxContatti"> 
		
		<!-- colonna di sinistra -->
		<div style="float:left; background-color:#ffffff; width:850px; height:100%;">
		
		
<!-- 					Barra menu -->
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

			
					
			<div class="centralBoxContatti">
			
				<p>&nbsp;</p>
  				<p align="center">
					<img alt="Immagine pagamenti" src="immagini/contatti.jpg">
				</p>				  				
							
			<!--  	<div id="map-canvas" style="float:left; margin-left: 50px"></div> -->
			
			<div class="google-maps">				
 				<iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d3003.1314016269926!2d14.637705900802615!3d41.17530110468682!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133a479ee7644a41%3A0xc632c72ce4be643c!2sVia+Vennerici%2C+3B%2C+82038+Vitulano+BN!5e0!3m2!1sit!2sit!4v1521219506195" width="400" height="400" frameborder="0" style="border:0" allowfullscreen></iframe>

			</div>
			
				<div style="float:left; padding-left: 10px">
								
					<p>ROSBELF Company spa </br>
						Via Santa Croce </br>
						Vitulano (Benevento) </br>
					</p>
					<p>CAP 82038 Vitulano </br>
						Italy 
					</p>
					<p>P. Iva 07975301717</p>
					<p>&nbsp;</p>
					<p>Telefono 0824870461</p>
					<p>Per qualunque informazione telefonare al numero su </br>
					    indicato dalle ore 9,30 alle ore 13,30 dal lunedi </br>
						al sabato, oppure via mail a  <a href="mailto:rosariogoglia@gmail.com"> <font color="#C6310C"> rosariogoglia@gmail.com </font> </a> </br>
						o <a href="mailto:marco.belfiore88@gmail.com"> <font color="#C6310C"> marco.belfiore88@gmail.com </font> </a> 

								
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
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/rosario.goglia" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>

</body>
</html>