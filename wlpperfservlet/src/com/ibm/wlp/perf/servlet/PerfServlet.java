
package com.ibm.wlp.perf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * Servlet implementation class PerfServlet
 */
@WebServlet({"/PerfServlet", "/ps"})
public class PerfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    XMLPrinter xmlp = new XMLPrinter();
	    try {
	    	String filter = request.getParameter("filter");
	    	if (filter != null) {
	    		
	    	  xmlp.printServletStatistic = filter.equalsIgnoreCase("servlets") ? true : false;
	    	  xmlp.printSessionStatistic = filter.equalsIgnoreCase("sessions") ? true : false;
	    	  xmlp.printConnectionPoolStatistic = filter.equalsIgnoreCase("connections") ? true : false;
	    	  xmlp.printThreadStatistic = filter.equalsIgnoreCase("threads") ? true : false;
	    	  xmlp.printJvmStatistic = filter.equalsIgnoreCase("jvm") ? true : false;
	    	}
			xmlp.generateXML(response.getOutputStream());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
