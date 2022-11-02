package com.epassignment.restful.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.epassignment.restful.dao.FilmDAO;
import com.epassignment.restful.model.Film;

/**
 * Class to handle http requests
 * 
 * @author Daniel Hayes
 *
 */
public class FilmResource {
	
	FilmDAO fdao = new FilmDAO();

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	/**
	 * Class constructor, requires all attributes
	 * 
	 * @param uriInfo		URI address
	 * @param request		request type
	 * @param id			film id
	 */
	public FilmResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	/**
	 * handles get requests to .../films/id, produces results in APPLICATION_XML 
	 * and APPLICATION_JSON
	 * 
	 * @return Film
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Film getFilm() {
		Film film = fdao.getFilmByID(Integer.parseInt(id));
		if(film==null)
			throw new RuntimeException("Get: Film with " + id + " not found");
		return film;
	}
	
	/**
	 * handles get requests to .../films/id, produces results in TEXT_XML
	 * 
	 * @return Film
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	public Film getFilmHTML() {
		Film film = fdao.getFilmByID(Integer.parseInt(id));
		if(film==null)
			throw new RuntimeException("Get: Film with " + id + " not found");
		return film;
	}
	
	/**
	 * Handles put requests to .../films, contains APPLICATION_XML details of
	 * film to be updated
	 * 
	 * @param film
	 * @return Response			calls putAndGetResponse method
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putFilm(JAXBElement<Film> film) {
		Film c = film.getValue();
		return putAndGetResponse(c);
	}
	
	/**
	 * Handles delete requests to .../films/id
	 */
	@DELETE
	public void deleteFilm() {
		boolean c = fdao.deleteFilm(id);
		if(c==false)
			throw new RuntimeException("Delete: Film with " + id 
					+ " not found");
	}
	
	/**
	 * Completes put request with data passed from putFilm method
	 * 
	 * @param film
	 * @return Response			outcome of put attempt 201 = success, 204 = fail
	 */
	private Response putAndGetResponse(Film film) {
		Response res;
		int fid = film.getId();
		if(fdao.getFilmByID(fid).getId() != 0) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		} else {
			res = Response.noContent().build();
		}
		fdao.updateFilm(film);
		return res;
	}
}
