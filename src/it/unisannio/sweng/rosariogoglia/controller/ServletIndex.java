package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.bean.HomeBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che fa il redirect alla pagine index del sito
 */


public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				
			try {
				
				HomeBean homeBean = new HomeBean();
				request.setAttribute("homeBean", homeBean);
				
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
		
		
	}
	
	

}
