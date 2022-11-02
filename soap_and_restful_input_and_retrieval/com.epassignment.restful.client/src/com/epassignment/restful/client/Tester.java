package com.epassignment.restful.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.epassignment.restful.model.Film;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;


public class Tester {
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		// Create one film
		Film film = new Film(11311, "Blabla", 1990, "Bla", "BlaBlaBla", "BlaBlaBlaBlaBlackSheep");
		ClientResponse response = service.path("rest").path("films").path(String.valueOf(film.getId())).accept(MediaType.APPLICATION_XML).put(ClientResponse.class, film);
		// Return code should be 201 == created resource
		System.out.println(response.getStatus());
		// Get the film
		System.out.println(service.path("rest").path("films").accept(
				MediaType.TEXT_XML).get(String.class));
		// Get JSON for application
		System.out.println(service.path("rest").path("films").accept(
				MediaType.APPLICATION_JSON).get(String.class));
		// Get XML for application
		System.out.println(service.path("rest").path("films").accept(
				MediaType.APPLICATION_XML).get(String.class));
		
		// Get the Film with id 11311
		System.out.println(service.path("rest").path("films/11311").accept(
				MediaType.APPLICATION_XML).get(String.class));
		// get Film with id 11311
		service.path("rest").path("films/11311").delete();
		// Get the all films, id 11311 should be deleted
		System.out.println(service.path("rest").path("films").accept(
				MediaType.APPLICATION_XML).get(String.class));
			
		// Create a Film
		Form form = new Form();
		form.add("id", "0");
		form.add("title", "Demonstration of the client lib for forms");
		form.add("year", "1990");
		form.add("director", "Demonstration of the client lib for forms");
		form.add("stars", "Demonstration of the client lib for forms");
		form.add("review", "Demonstration of the client lib for forms");
		response = service.path("rest").path("films").type(MediaType.APPLICATION_FORM_URLENCODED)
								   .post(ClientResponse.class, form);
		System.out.println("Form response " + response.getEntity(String.class));
		// Get all films, id 11311 should be created
		System.out.println(service.path("rest").path("films").accept(
				MediaType.APPLICATION_XML).get(String.class));
		
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/com.epassignment.restful").build();
	}
}