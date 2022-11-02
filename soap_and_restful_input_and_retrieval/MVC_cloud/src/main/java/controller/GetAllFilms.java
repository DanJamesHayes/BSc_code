package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import model.FilmDAO;

/**
 * Servlet implementation class GetAllFilms
 */
@WebServlet("/GetAllFilms")
public class GetAllFilms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllFilms() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse 
	 * 		response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		FilmDAO fdao = new FilmDAO();
		ArrayList<Film> films = fdao.getAllFilms();
		request.setAttribute("films", films);
	    String format = request.getParameter("format");
	    String outputPage;
	    
	    if ("xml".equals(format)) {
	    	response.setContentType("text/xml");
	    	outputPage = "/WEB-INF/results/films-xml.jsp";
	    } else if ("string".equals(format)) {
			response.setContentType("text/plain");
			outputPage = "/WEB-INF/results/films-string.jsp";
	    } else {
		    response.setContentType("application/json");
		    outputPage = "/WEB-INF/results/films-json.jsp";
	    }
	    
	    RequestDispatcher dispatcher = 
	    		request.getRequestDispatcher(outputPage);
	    dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse 
	 * 		response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
