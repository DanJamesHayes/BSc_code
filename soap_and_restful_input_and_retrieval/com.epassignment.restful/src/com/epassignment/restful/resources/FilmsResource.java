package com.epassignment.restful.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.epassignment.restful.dao.FilmDAO;
import com.epassignment.restful.model.Film;

/**
 * Class to handle or pass on http request 
 * 
 * @author Daniel Hayes
 *
 */
@Path("/films")
public class FilmsResource {
	
	FilmDAO fdao = new FilmDAO();
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * handles get requests to .../films, produces results in TEXT_XML
	 * 
	 * @return List<Film>
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Film> getFilmsBrowser() {
		List<Film> films = new ArrayList<Film>();
		films = (List<Film>) fdao.getAllFilms();	
		return films; 
	}
	
	/**
	 * handles get requests to .../films, produces results in APPLICATION_XML 
	 * and APPLICATION_JSON 
	 * 
	 * @return List<Film> 
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Film> getFilms() {
		List<Film> films = new ArrayList<Film>();
		films = (List<Film>) fdao.getAllFilms();
		return films; 
	}

	/**
	 *  handles get requests to .../films/count, produces results in TEXT_PLAIN
	 *  
	 * @return String
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = fdao.filmCount();
		return String.valueOf(count);
	}
	
	/**
	 * Handles post requests to ../films with APPLICATION_FORM_URLENCODED 
	 * contained, produces results in TEXT_HTML
	 * 
	 * @param id
	 * @param title
	 * @param year
	 * @param director
	 * @param stars
	 * @param review
	 * @param servletResponse
	 * @throws IOException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newFilm(
			@FormParam("id") String id,
			@FormParam("title") String title,
			@FormParam("year") String year,
			@FormParam("director") String director,
			@FormParam("stars") String stars,
			@FormParam("review") String review,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Film film = new Film(Integer.parseInt(id), title, 
				Integer.parseInt(year), director, stars, review);
		fdao.insertFilm(film);
		servletResponse.sendRedirect("../create_film.html");
	}
	
	/**
	 * Instantiates FilmResource object with passed parameters
	 * 
	 * @param id
	 * @return FilmResource
	 */
	@Path("{film}")
	public FilmResource getFilm(
			@PathParam("film") String id) {
		return new FilmResource(uriInfo, request, id);
	}
	
}
