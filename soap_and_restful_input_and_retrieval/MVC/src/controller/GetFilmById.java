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
 * Servlet implementation class GetFilmById
 */
@WebServlet("/GetFilmById")
public class GetFilmById extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFilmById() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		FilmDAO fdao = new FilmDAO();
		
		// Check that get parameter can be parsed to integer else query = 0
		int query = 0;
		try {
			query = Integer.parseInt(request.getParameter("FileName"));
		} catch(NumberFormatException e){}

		ArrayList<Film> films  = fdao.getFilmByID(query);
		request.setAttribute("films", films);
	    
		String outputPage;
    	response.setContentType("text/xml");
    	outputPage = "/WEB-INF/results/films-xml.jsp";
    	
    	RequestDispatcher dispatcher = 
	    		request.getRequestDispatcher(outputPage);
	    dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
