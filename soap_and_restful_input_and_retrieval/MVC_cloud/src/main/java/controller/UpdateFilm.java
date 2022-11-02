package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import model.FilmDAO;

/**
 * Servlet implementation class UpdateFilm
 */
@WebServlet("/UpdateFilm")
public class UpdateFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFilm() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse 
	 * 		response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		FilmDAO fdao = new FilmDAO();
		
		// Try and catch block not required as only valid ids can be passed
		int id = Integer.parseInt(request.getParameter("Id"));
		String title = request.getParameter("FileName");

		// Check year parameter can be parsed to integer else set to 0 and
		// inform user default value was recorded
		int year = 0;
		String errorMessage = "";
		try {
			year = Integer.parseInt(request.getParameter("Year"));
		} catch(NumberFormatException e){
			errorMessage = "<br />Incorrect format entered for year, default "
					+ "value recorded";
		}
		String director = request.getParameter("Director");
		String stars = request.getParameter("Stars");
		String review = request.getParameter("Review");
		Film film = new Film(id, title, year, director, stars, review);
		
		boolean updateSuccessful = fdao.updateFilm(film);
		if(updateSuccessful == true) {
			out.print("Record updated." + errorMessage);
		} else {
			out.print("Update request failed.");
		}
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
