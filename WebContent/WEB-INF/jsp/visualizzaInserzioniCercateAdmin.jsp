<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> <LINK REL="SHORTCUT ICON" href="favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aste On Line - Inserzioni Cercate Admin</title>
<meta name="" content="">
<link href="css/style.css" type="text/css" rel="stylesheet">


<%-- Include all the required script tags --%>
<jsp:include page="/WEB-INF/includes/ajaxscripts.jsp"/>

</head>

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
						  	Benvenuto ${utente.nick} |
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
			     	 <h2 align="center">Inserzioni</h2>
			    <p>&nbsp;</p>
			     
			     <div style= "font-size:medium; font-weight: bold; text-align:left; padding-left: 175px;">
    				<form action="ServletRicercaInserzioneAdmin">
    					Ricerca inserzione per titolo: 
    					<input id="autoTitolo" style=" height: 20px; width: 300px;" type="text" size="50" maxlength="" placeholder="Titolo inserzione" value="" name="titolo">
    					<input id="bottone-Cerca" style="display:inline;" type="submit" value="Cerca">
    				</form>
    				
    				<ajax:autocomplete
						source="autoTitolo"
						target="autoTitolo"
						baseUrl="${contextPath}/autocompleteTitolo.ajax"
						parameters="titoloPrefix={autoTitolo}"
						className="autocompletamentokey"
						minimumCharacters = "1"/>
    					
    				
    	  	   	</div> 
    	  	   	
			   <p>&nbsp;</p>
			    <c:if test="${ numeroInserzioni > 0 }">
				         
<!-- 				         <table width="950" align="center" border="0" cellpadding="5" cellspacing="5"> -->
					     <table style="margin-left:auto; margin-right:auto; font-size: 15px; width: auto;" border="0" cellpadding="5" cellspacing="5">
					     
					        <tr>
					          <th id="campoTesto3">Immagine</th>
					          <th id="campoTesto3">Titolo</th>
					          <th id="campoTesto3">Prodotto</th>
					          <th id="campoTesto3">Categoria</th>
					          <th id="campoTesto3">Data Scadenza</th>
					          <th id="campoTesto3">Venditore</th>
					          <th id="campoTesto3">Stato</th>
					          <th ></th>
					          <th ></th>
					        </tr>
					       
					      <c:forEach items="${intervalloInserzioniCercate}" var="inserzioneCercata" > 
					     		<tr>
					     		  
					     		  <td id="td2" align="center"><img src="${inserzioneCercata.immagini[0].foto }"  width="80px" height="80px"></img></td>
						          <td id="td2" align="center">${inserzioneCercata.titolo }</td>
						          <td id="td2" align="center">${inserzioneCercata.prodotto.nome } </td>
						          <td id="td2" align="center">${inserzioneCercata.prodotto.categoria.nome  } </td>
						     	  <td id="td2" align="center">${inserzioneCercata.dataScadenzaString}</td>
						          <td id="td2" align="center">${inserzioneCercata.venditore.nome} ${inserzioneCercata.venditore.cognome}</td>
						          <td id="td2" align="center">${inserzioneCercata.stato}</td>
						          						           
						          <td id="td2" align="center" width="70px"><a href="ServletDettagliInserzione?idInserzione=${ inserzioneCercata.idInserzione }" target="_new"> <img src="immagini/dettagli.png" alt="Dettagli" title="Dettagli" height="30px"> </a></td>
				          			
				          		  <c:if test="${inserzioneCercata.stato eq 'in asta' || inserzioneCercata.stato eq 'scaduta'}">
				          		     <td id="td2" align="center" width="70px"><a href="ServletEliminaInserzione?idInserzione=${ inserzioneCercata.idInserzione }"> <img alt="Elimina" title="Elimina" src="immagini/rimuovi.png" height="30px"> </a></td>
				          		  </c:if>
				          
				                 </tr>
					        </c:forEach>
					      </table>
				      
				      <p>&nbsp;</p> 
				      
				      <p align="center">
				     	  	<
				     	  	<c:forEach var="i" begin="1" end="${numeroPagineRicercaPerTitolo}">
				     	  		
				     	  		  <a style="color: #C6310C" href="ServletRicercaInserzioneAdmin?idNumPagina=${ i }" > ${ i } </a>	
				     	  	
				     	  	</c:forEach>
				     	  	>
				      </p>
				      
				      
			     </c:if>
			     
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
	 
		© 2013 - AsteOnLine edit by <a style="color:#0000FF;" href="http://www.facebook.com/rosario.goglia" target="_new">Rosario Emanuel Goglia</a> and <a style="color:#0000FF;" href="https://www.facebook.com/marco.belfiore.54?fref=ts" target="_new">Marco Belfiore</a>
	 
	</div>


</div>


</body>
</html>