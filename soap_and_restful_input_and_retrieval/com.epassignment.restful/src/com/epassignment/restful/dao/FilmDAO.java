package com.epassignment.restful.dao;

import java.util.ArrayList;

import com.epassignment.restful.model.Film;

import java.sql.*;

/**
 * Data Access Object class to implement CRUD of films database
 * 
 * @author Daniel Hayes
 *
 */
public class FilmDAO {
	
	private Film oneFilm = null;
	private Connection conn = null;
	private Statement stmt = null;
	private String user = "hayesd";
	private String password = "jemptOll3";
	private String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;

	/**
	 * Empty constructor to instantiate FilmDAO object
	 */
	public FilmDAO() {}
	
	/**
	 * Create database connection and statement
	 */
	private void openConnection() {
	    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch (InstantiationException | IllegalAccessException 
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Close database connection
	 */
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Iterate over result set and return next film as film object
	 * 
	 * @param rs					ResultSet from SQL query
	 * @return Film
	 */
	private Film getNextFilm(ResultSet rs){
    	Film thisFilm=null;
		try {
			thisFilm = new Film(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getInt("year"),
					rs.getString("director"),
					rs.getString("stars"),
					rs.getString("review"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return thisFilm;		
	}
	
	/**
	 * Get all films from database
	 * 
	 * @return ArrayList<Film>		
	 */
	public ArrayList<Film> getAllFilms() {
		ArrayList<Film> allFilms = new ArrayList<Film>();
	    String selectSQL = "select * from films";
		try{
			openConnection();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	allFilms.add(oneFilm);
		    }
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		return allFilms;
	}
	
	/**
	 * Get amount of films in database
	 * 
	 * @return int
	 */
	public int filmCount(){
		int count = 0;
	    String selectSQL = "select count(*) from films";
		try{
			openConnection();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	count = rs1.getInt("count(*)");
		    }
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		return count;
	}

	/**
	 * Retrieve all films that match passed search query. If query does not 
	 * parse to integer 0 is used to check ID and year column.
	 * 
	 * @param query					String of search query
	 * @return ArrayList<Film>
	 */
	public ArrayList<Film> getFilm(String query){
		ArrayList<Film> films = new ArrayList<Film>();
	    String selectSQL = "select * from films "
	    		+ "where title like '%" + query + "%' "
	    		+ "or director like '%"+ query + "%' "
				+ "or stars like '%"+ query + "%';";
		try{
			openConnection();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	films.add(oneFilm);
		    }
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		return films;
	}
   
	/**
	 * Retrieve film with passed id parameter
	 * 
	 * @param id					ID of required film
	 * @return Film
	 */
	public Film getFilmByID(int id){
		Film film = new Film();
	    String selectSQL = "select * from films where id="+id;
		try{
			openConnection();
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	film = getNextFilm(rs1);
		    }
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		return film;
	}
	
	/**
	 * Insert film into database. ID will be assigned by database.
	 * 
	 * @param f						Film object to be inserted
	 * @return boolean				Indication of success or failure
	 */
	public boolean insertFilm(Film f) {
		boolean insertSuccessful = false;
		String insertSQL = "insert into films "
				+ "(title, year, director, stars, review) values (ucase('" 
				+ f.getTitle() + "'), " 
				+ f.getYear() + ", ucase('"
				+ f.getDirector() + "'), ucase('"
				+ f.getStars() + "'), '"
				+ f.getReview() + "');";
		try {
			openConnection();
			insertSuccessful = stmt.executeUpdate(insertSQL) > 0;
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    closeConnection();
		}
		return insertSuccessful;
	}
   
	/**
	 * Update film details of film relating to give ID.
	 * 
	 * @param f						Film object containing amended details
	 * @return boolean				Indication of success or failure
	 */
	public boolean updateFilm(Film f) {
		boolean updateSuccessful = false;
		String updateSql = "update films set "
				+ "title = ucase('" + f.getTitle() + "'), "
		   		+ "year = " + f.getYear() + ", "
		   		+ "director = ucase('" + f.getDirector() + "'), "
		   		+ "stars = ucase('" + f.getStars() + "'), "
		   		+ "review = '" + f.getReview() + "' "
		   		+ "where id = " + f.getId() + ";";
		try {
			openConnection();
			updateSuccessful = stmt.executeUpdate(updateSql) > 0;
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
			closeConnection();
		}
		return updateSuccessful;
	}
   
	/**
	 * Delete film of given film ID and reset auto_increment
	 * 
	 * @param id					ID of film to be delete
	 * @return boolean				Indication of success or failure
	 */
	public boolean deleteFilm(String id) {
		boolean filmDeleted = false;
		String deleteSql = "delete from films where id = " + id;
		String resetAutoInc = "ALTER TABLE films AUTO_INCREMENT = 10001";
		try {
			openConnection();
			filmDeleted = stmt.executeUpdate(deleteSql) > 0;
			stmt.executeUpdate(resetAutoInc);
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
			closeConnection();
		}
		return filmDeleted;
	}
}
