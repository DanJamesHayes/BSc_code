package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FilmDAO;

/**
 * Servlet implementation class DeleteFilm
 */
@WebServlet("/DeleteFilm")
public class DeleteFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFilm() {
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
		String id = request.getParameter("Id");
		
		boolean recordDeleted = fdao.deleteFilm(id);
		
		if(recordDeleted == true) {
			out.print("Record deleted.");
		} else {
			out.print("Delete request failed, ID not recognised.");
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
