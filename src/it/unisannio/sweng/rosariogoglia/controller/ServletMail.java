package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.RandomPasswordDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.RandomPasswordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.RandomPassword;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.RandomPasswordImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;
import it.unisannio.sweng.rosariogoglia.utility.GenerarePasswordRandom;
import it.unisannio.sweng.rosariogoglia.utility.MD5;
import it.unisannio.sweng.rosariogoglia.utility.MailUtility;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletMail extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	
private static final long serialVersionUID = 1L;

public ServletMail(){ super(); }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
	   
    //String mitt = request.getParameter("mittente");
    //String dest = request.getParameter("destinatario");
    //String oggetto = request.getParameter("oggetto");
	//String testo = request.getParameter("contenuto");

    
	  String nickname = request.getParameter("nickname"); 
	  String eMail = request.getParameter("eMail");
	  
	  UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
	  
	  String messaggio = "";
	  UtenteRegistrato utente = new UtenteRegistratoImpl(); 
	  
	  if((nickname != null && !nickname.equals("")) && (eMail != null && !eMail.equals(""))){
		  //verifico che il nickname appartenga ad un utente reale
		  if(daoU.controlloNick(nickname)){
			  utente = daoU.getUtenteRegistratoByNick(nickname);
			  eMail = utente.geteMail();
		  }
		  else if(daoU.controlloeMail(eMail)){
			  utente = daoU.getUtenteRegistratoByeMail(eMail);
		  }
		  else{
			  messaggio = "Il Nickname ed eMail per cui è richiesto il recupero dati non sono esistenti!!!!!!";
			  request.setAttribute("messaggio", messaggio);
			  request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
			  return;
		  }
		  
	  }
	  else if((nickname != null && !nickname.equals(""))){
		  if(daoU.controlloNick(nickname)){
			  utente = daoU.getUtenteRegistratoByNick(nickname);
			  eMail = utente.geteMail();
		  }
		  else{
			  messaggio = "Il Nickname per cui è richiesto il recupero dati non è esistente!!!";
			  request.setAttribute("messaggio", messaggio);
			  request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
			  return;
		  } 
		  	  
	  }
	  else if((eMail != null && !eMail.equals(""))){
		  if(daoU.controlloeMail(eMail)){
			  utente = daoU.getUtenteRegistratoByeMail(eMail);
		  }
		  else{
			  messaggio = "L'eMail per cui è richiesto il recupero dati non sono esistenti!!!";
			  request.setAttribute("messaggio", messaggio);
			  request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
			  return;
		  } 
	  }
	  else{
		  messaggio = "Inserire almeno un parametro tra nickname e Mail per recuperare la password!!!";
		  request.setAttribute("messaggio", messaggio);
		  request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
		  return;
	  }
		 
	
	  //genero una nuova password di ingresso per inviarla all'utente
	  String passwordNuova = GenerarePasswordRandom.generate();
	  System.out.println("Password nuova: " + passwordNuova);
	  
	  RandomPasswordDao rpDao = new RandomPasswordDaoMysqlJdbc();
	  
	  //controllo se la password generata è già esistente, in caso affermativo ne viene generata un'altra
	  while(rpDao.checkHashPassword(passwordNuova)){
		  passwordNuova = GenerarePasswordRandom.generate();
	  }
	  	  
	  String passwordNuovaCriptata;
	try {
	
		//cripto la password appena generata random 
		passwordNuovaCriptata = MD5.md5(passwordNuova);
	
		//aggiorno nel database la nuova password di accesso per l'utente
		daoU.updatePassword(utente.getNick(), passwordNuovaCriptata);
		
		RandomPassword randPass = new RandomPasswordImpl();
		randPass.setHashPassword(passwordNuovaCriptata);
		randPass.setIdUtente(utente.getIdUtente());
		
		//inserisco la password random generata nella tabella RandomPassword, per non avere il problema della generazione di 2 pass uguali
		rpDao.insertRandomPassword(randPass);
	
	} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e1) {
		e1.printStackTrace();
	}
	 	  
	  
    String mittente = "rosariogoglia@gmail.com";
    String destinatario = eMail;
    String oggetto = "Recupero credenziali di accesso AsteOnLine";
    String testo = ("" + '\n' +'\n' + "Le tue credenziali di accesso ad AsteOnLine sono: " + '\n' + '\n' + '\n' +
    		"Nickname: " + utente.getNick() + '\n' + 
    		"Password: " + passwordNuova + '\n' + '\n' + '\n'+
    		"Accedi direttamente al sito dal link sottostante e... Buon AsteOnline!!!" + '\n' + '\n' + '\n' +
    		"                    http://rosariogoglia.myqnapcloud.com:30000/AsteOnLine/index");
    
    
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try{
    	
      MailUtility.sendMail(destinatario, mittente, oggetto, testo);
      request.setAttribute("messaggio", "Mail di recupero inviata, controlla la tua posta!!!");
      request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
      
    }
    catch (MessagingException exc){
    	
    	request.setAttribute("messaggio", "Mail di recupero non inviata!!! Riprovare!!!");
        request.getRequestDispatcher("WEB-INF/jsp/confermaRecuperoPassword.jsp").forward(request, response);
        
      log("MessagingException: " + destinatario);
      log(exc.toString());
    
    }
  
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    this.doPost(request, response);
  }
  
} 