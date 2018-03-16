<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Le mie Aste</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function () {
  setInterval(rotateImage, 3000);
  var images = new Array('immagini/bannerAsteOnLine.jpg', 'immagini/bannerPubblicitari/kingolotto.gif', 'immagini/bannerPubblicitari/kingolotto2.gif', 'immagini/bannerPubblicitari/dell.gif', 'immagini/pubblicita.png', 'immagini/bannerPubblicitari/italiapubblicita.png');
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
  				  					
  				<div class="centralBoxRisultatiProdotti">
			     
			      <p>&nbsp;</p>
			      <h2 align="center">Le Mie Aste</h2>
			      <p>&nbsp;</p>
			     
			      <p align="center"></p>
			      <c:if test="${ numeroInserzioni > 0 }">
				     <table style="margin-left:auto; margin-right:auto"  border="0" cellspacing="2" cellpadding="2" align="center" width="auto"  id="td2">
				     	<tr>
				    	  <td width="auto" align="left"></td>
				          <td width="auto" id="td1" align="center">Nome</td>
				          <td width="auto" id="td1" align="center">Prezzo Iniziale</td>
				          <td width="auto" id="td1" align="center">Prezzo Finale</td>
				          <td width="auto" id="td1" align="center">Stato</td>    
				    	</tr>
				       	<c:forEach items="${ intervalloLeMieAste }" var="inserzioneCercata">
					        <tr>
					          <td id="td3" align="center"><img src="${inserzioneCercata.immagini[0].foto }"  width="80px" height="80px"></img></td>
					          <td id="td3" align="center">${inserzioneCercata.titolo }</td>
					          <td id="td3" align="center">${inserzioneCercata.prezzoIniziale } €</td>
					          <td id="td3" align="center">${inserzioneCercata.prezzoAggiornato } €</td>     
					          <td id="td1" align="center">${inserzioneCercata.stato }</td>
					          <td width="70" align="center"><a href="ServletDettagliInserzione?idInserzione=${ inserzioneCercata.idInserzione }" target="_new"> <img alt="Dettagli" title="Dettagli" src="immagini/dettagli.png" height="30px"> </a></td>
						      <td width="70"  align="center"><a href="ServletControlloOfferta?idInserzione=${inserzioneCercata.idInserzione}"> <img alt="Fai un'offerta" title="Fai un'offerta" src="immagini/fai offerta.png" height="30px"></a></td>  
						      
					        </tr>
				        </c:forEach>
				      </table>
			        
				      <p>&nbsp;</p>
			        
			          <p align="center">
				     	  	<
				     	  	<c:forEach var="i" begin="1" end="${ numeroPagineMieAste }">
				     	  		
				     	  		  <a style="color: #C6310C"  href="ServletLeMieAste?idNumPagina=${ i }" > ${ i } </a>	
				     	  	
				     	  	</c:forEach>
				     	  	>
				     </p>
			        
			        
			        </c:if>
			      
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
	 
© 2018 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/ros.em.goo" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>