<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Pagamenti</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">
</head>


<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function () {
  setInterval(rotateImage, 4000);
  var images = new Array('immagini/bannerAsteOnLine.jpg', 'immagini/pubblicita.png', 'immagini/bannerPubblicitari/bettersoftware.png', 'immagini/bannerPubblicitari/lenovys.gif');
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
			
					
			<div class="centralBox">
			
				<p>&nbsp;</p>
  				<p align="center">
					<img alt="Immagine pagamenti" src="immagini/pagamento.jpg">
				</p>				  				
							
				<p>&nbsp;</p>
				
				<p style="font-weight: bold;">AsteOnLine combatte tutti i tentativi di TRUFFE ONLINE, facendo da "arbitro" nella compra-vendita tra acquirente e venditore! </br>
				Tutte le transazioni passano per la nostra società, in modo da garantire al compratore la massima sicurezza!!! </p>
				
				<p>&nbsp;</p>
				
				<p>Su AsteOnLine potrai pagare i tuoi acquisti scegliendo tra i seguenti metodi di pagamento:</p>
				
				<p>&nbsp;</p>
				
				<p>Ritiro in sede:</p>
				<p>Pagate il costo del prodotto senza spese aggiuntive e potete scegliere se pagare in contanti, carta di credito o bancomat.
				
				<p>&nbsp;</p>
				
				<p>Bonifico Bancario:<p>
				<p>Scegliendo questo metodo di pagamento anticipato, immediatamente dopo l'acquisto sarà inviata una mail riepilogativa dell'ordine e saranno elencati gli estremi per effettuare il pagamento. Una volta effettuato il pagamento dovrete inviare una copia al seguente indirizzo e-mail: 
				<p>amministrazione@AsteOnLine.org</p>				
				<p>Nostre coordinate bancarie intestazione ROSBELF COMPANY S.P.A :</p>
												
				<p>UniCredit S.p.A.</p>
 				<p>IT 77 O 07070 0301 000000 333000</p> 

				<p>&nbsp;</p>
				
				<p>Contrassegno contante: <p>
				<p>Con questo metodo di pagamento potrai pagare l’ordine effettuato solo in contante al corriere che consegna la merce. Il costo del contrassegno verrà aggiunto sul totale dell'ordine in fase di evasione ad una tariffa di € 7,00</p>
				
				<p>&nbsp;</p>

				<p>Prezzi:<p>
				<p>I prezzi delle inserzioni pubblicate su AsteOnLine sono da considerarsi in euro e già comprensivi di IVA.<p>
				
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