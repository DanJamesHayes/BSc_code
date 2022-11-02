package controller;

import java.util.ArrayList;

import com.google.gson.Gson;

import model.Film;
import model.FilmDAO;

/**
 * Controller class for testing DAO functions.  Each function that is tested
 * is immediately followed by the corresponding HTTP get request.
 * 
 * @author Daniel Hayes 
 *
 */
public class MainController {

	public static void main(String[] args) {
		
		FilmDAO fdao = new FilmDAO();
		
		// Test insertFilm method. ID is 0 because it is not required as the 
		// database is set to auto_increment.
		Film film = new Film(0, "Fantastic Mr. Fox", 2009, "Wes Anderson", 
				"George Clooney. Meryl Streep. Bill Murray", 
				"For the reportedly painstaking labor it took to create, "
				+ "the film is a marvel to behold--with wonderful shifts in "
				+ "perspective, an intensely tactile design, and an intentional"
				+ " herky-jerkiness of motion that only enriches the "
				+ "make-believe atmosphere.");
		boolean insertSuccessful = fdao.insertFilm(film);
		System.out.println("Film inserted = " + insertSuccessful);
		// http://localhost:8080/entProgAssignment/InsertFilm?FileName=Fantastic%20Mr.%20Fox&Year=2009&Director=Wes%20Anderson&Stars=George%20Clooney.%20Meryl%20Streep.%20Bill%20Murray&Review=For%20the%20reportedly%20painstaking%20labor%20it%20took%20to%20create%2C%20the%20film%20is%20a%20marvel%20to%20behold--with%20wonderful%20shifts%20in%20perspective%2C%20an%20intensely%20tactile%20design%2C%20and%20an%20intentional%20herky-jerkiness%20of%20motion%20that%20only%20enriches%20the%20make-believe%20atmosphere.

		
		// Test getAllFilms method as film and JSON.
		ArrayList<Film> films = fdao.getAllFilms();
		for(Film oneFilm : films) {
			System.out.println(oneFilm.toString());
		}
		Gson gson = new Gson();
		String allFilmsJson = gson.toJson(films);
		System.out.println(allFilmsJson);
		// http://localhost:8080/entProgAssignment/GetAllFilms?format=json
		
		
		// Test of getFilm method with a non numeric search query
		ArrayList<Film> searchResult = fdao.getFilm("Black");
		for(Film oneFilm : searchResult) {
			System.out.println(oneFilm.toString());
		}
		// http://localhost:8080/entProgAssignment/GetFilm?FileName=black&format=xml
		
		// Test of getFilm method with a numeric search query
		ArrayList<Film> numericSearchResult = fdao.getFilm("1990");
		for(Film oneFilm : numericSearchResult) {
			System.out.println(oneFilm.toString());
		}
		// http://localhost:8080/entProgAssignment/GetFilm?FileName=1990&format=xml

		
		// Test getFilmByID method
		ArrayList<Film> filmByID = fdao.getFilmByID(11311);
		for(Film oneFilm : filmByID) {
			System.out.println(oneFilm.toString());
		}
		// http://localhost:8080/entProgAssignment/GetFilmById?FileName=11311&format=xml
		
		
		// Set ID of newly inserted film to film object and test updateFilm 
		// method.
		film.setId(11311);
		boolean updateSuccessful = fdao.updateFilm(film);
		// Print result of whether or not film update was successful.
		System.out.println("Film updated = " + updateSuccessful);
		// http://localhost:8080/entProgAssignment/UpdateFilm?FileName=Fantastic%20Mr.%20Fox&Id=11311&Year=2009&Director=Wes%20Anderson&Stars=George%20Clooney.%20Meryl%20Streep.%20Bill%20Murray&Review=For%20the%20reportedly%20painstaking%20labor%20it%20took%20to%20create%2C%20the%20film%20is%20a%20marvel%20to%20behold--with%20wonderful%20shifts%20in%20perspective%2C%20an%20intensely%20tactile%20design%2C%20and%20an%20intentional%20herky-jerkiness%20of%20motion%20that%20only%20enriches%20the%20make-believe%20atmosphere.
		
		
		// Test delete film method
		boolean recordDeleted = fdao.deleteFilm("11311");
		System.out.println(recordDeleted);
		// http://localhost:8080/entProgAssignment/DeleteFilm?Id=11311
	}

}
