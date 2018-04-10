package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletEliminaProdotto
 */
//@WebServlet("/ServletEliminaInserzione")
public class ServletEliminaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(idInserzione);
		String messaggio = "";
		
		/*Se l'inserzione è in asta oppure è scaduta può essere eliminata*/
		if(!inserzione.getStato().equals("pagata") && !inserzione.getStato().equals("aggiudicata")){
		
			int deletedRow = daoI.deleteInserzione(idInserzione);
				
			if(deletedRow == 1){
				messaggio = "L'inserzione n° " + inserzione.getIdInserzione() + " è stato eliminata correttamente!!!";
			}
			else{
				messaggio = "L'inserzione n° " + inserzione.getIdInserzione() + " non è stato eliminata!!!";
			}
				
		}
		else{
			messaggio = "Non puoi eliminare un' inserzione aggiudicata o pagata!!!";
		}	
			
		session.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/ServletListaInserzioni").forward(request, response);
		
	}

	
}
