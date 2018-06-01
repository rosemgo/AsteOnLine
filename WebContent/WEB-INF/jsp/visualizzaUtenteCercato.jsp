<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Visualizza Ricerca Utente</title>
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
		
		<p>&nbsp;</p>
 		<p align="center"><font size="4">${ messaggio }</font></p>
 
 
		<div class="centralBoxAmministrazione">
		
		
				<p>&nbsp;</p>
			     	 <h2 align="center">Ricerca Utenti</h2>
			    <p>&nbsp;</p>
			   
			   	<div style= "font-size:medium; font-weight: bold;" align="center">
    				<form action="ServletRicercaUtente" align="center">
    					Ricerca utente per nickname: 
    					<input id="campoTesto" type="text" size="50" maxlength="300" placeholder="Nickname" value="" name="nick">
    					<input type="submit" value="Cerca">
    				</form>
    	  	   	</div> 
			   
			    <p>&nbsp;</p>
			   
			   <c:if test="${utenteCercato != null}">
			      <table style="margin-left:auto; margin-right:auto"  border="0" cellspacing="5" cellpadding="5" align="center" width="835"  id="td2">
				     	<tr>
				     	  <td width="auto" align="left"></td>
				    	  <td width="auto" id="td1" align="center">Codice Fiscale</td>
				          <td width="auto" id="td1" align="center">Nick</td>
				          <td width="auto" id="td1" align="center">Password</td>
				          <td width="auto" id="td1" align="center">Tipologia</td>
				          <td width="auto" id="td1" align="center">Stato</td>    
				    	</tr>

					    <tr>
					         
					          <td id="td1" align="center">${utenteCercato.idUtente}</td>
					          <td id="td1" align="center">${utenteCercato.codiceFiscale}</td>
					          <td id="td1" align="center">${utenteCercato.nick} </td>
					          <td id="td1" align="center">${utenteCercato.password}</td>
					          <td id="td1" align="center">${utenteCercato.tipologiaCliente}</td>
					          <c:choose>
					          	<c:when test="${utenteCercato.flagAbilitato eq true}">
					          		<td id="td1" align="center"> <font color="#00FF00">Abilitato</font></td>	
					          	</c:when>
					          	<c:otherwise>
					          		<td id="td1" align="center"> <font color="#FF0000">Disabilitato</font></td>	
					          	</c:otherwise>
					          </c:choose>
					          					          
					          <td id="td2" width="78" align="center"><a href="ServletDettagliUtente?idUtente=${utenteCercato.idUtente}" target="_new"> <img src="immagini/dettagliutente.png" alt="Dettagli utente" title="Dettagli" height="30px"> </a></td>
					          <c:choose>
						          <c:when test="${utenteCercato.flagAbilitato eq false}">         
							         <td id="td2" width=83  align="center"><a href="ServletSetStatoUtente?idUtente=${utenteCercato.idUtente}"> <img src="immagini/abilitautente.png" alt="Abilita" title="Abilita" height="30px"> </a></td>
								  </c:when>
								  <c:when test="${utenteCercato.tipologiaCliente eq 'admin' && (utente.idUtente == utenteCercato.idUtente)}">
							          <td width="83" id="td2" align="center"><a href="ServletControlloModificaPassword"><img src="immagini/modificapassword.png" alt="Modifica Password" title="Modifica Password " height="30px"></a></td> 
							      </c:when>
						          <c:otherwise>
						         	 <td id="td2" width=83  align="center"><a href="ServletSetStatoUtente?idUtente=${utenteCercato.idUtente}" > <img src="immagini/disabilitautente.png" alt="Disabilita" title="Disabilita " height="30px"> </a></td>
								  </c:otherwise>
					          </c:choose>
					          
					     </tr>
				   </table>
				 </c:if>   
		      	
		      	<p>&nbsp;</p> 
				<p>&nbsp;</p> 		
				
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