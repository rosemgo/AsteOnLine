package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.dao.ProvinciaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProvinciaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Provincia;

import javax.servlet.http.*;
import org.ajaxtags.helpers.*;
import org.ajaxtags.servlets.*;
import java.util.*;

public class ServletCercaComuni extends BaseAjaxServlet {

	private static final long serialVersionUID = -3534695539530815007L;
	
	private Map<String, String> comuniMap;
  

  
  @Override
  public void init() {
    comuniMap = new HashMap<String,String>();
    ProvinciaDao provinciaDao = new ProvinciaDaoMysqlJdbc();
    for(Provincia provincia: provinciaDao.getProvince()) {
    	if(provincia == null)
    		System.out.println("Provincia vuota");
    	else
    		System.out.println("Provincia: " + provincia.getNomeProvincia());
      comuniMap.put(String.valueOf(provincia.getIdProvincia()), makeComuniList((ArrayList<Comune>)provincia.getListaComuni()));
    }
  }

  private String makeComuniList(ArrayList<Comune> comuni) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    for(Comune comune: comuni) {
	      builder.addItem(comune.getNomeComune(), String.valueOf(comune.getIdComune()));
	    }
	    return(builder.toString());
	  }
  
  @Override
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    String state = request.getParameter("provincia");
    System.out.println("RICEVO LA PROVINCIA: " + state);
    
    String listaComuni = comuniMap.get(state);
    if (comuniMap == null) {
      return("");
    } else { 
      return(listaComuni);
    }
  }
  

}