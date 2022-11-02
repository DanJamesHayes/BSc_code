package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

/**
 * Data Access Object class to implement CRUD of films database
 * 
 * @author Daniel Hayes
 *
 */
public class FilmDAO {
	
	private Film oneFilm = null;
	private ResultSet rs1 = null;
	
	/**
	 * Empty constructor to instantiate FilmDAO object
	 */
	public FilmDAO() {}

	/**
	 * Iterate over result set and return next film as film object
	 * 
	 * @param rs					ResultSet from SQL query
	 * @return Film
	 */
	private Film getNextFilm(ResultSet rs) {
		
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
		String SQL_QUERY = "SELECT * FROM films";
	    
		try (Connection conn = DataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
				ResultSet rs = pst.executeQuery();) {
			
			conn.setAutoCommit(false);
			
		    while(rs.next()){
		    	oneFilm = getNextFilm(rs);
		    	allFilms.add(oneFilm);
		    }
		    
		} catch(SQLException se) { 
			System.out.println(se);
		}
		
		return allFilms;
	}

	/**
	 * Retrieve all films that match passed search query. If query does not 
	 * parse to integer 0 is used to check ID and year column.
	 * 
	 * @param query					String of search query
	 * @return ArrayList<Film>
	 */
	public ArrayList<Film> getFilm(String query) {
		
		ArrayList<Film> films = new ArrayList<Film>();
	    String SQL_QUERY = "SELECT * FROM films "
				   + "WHERE id LIKE ? "
				   + "OR title LIKE ? "
				   + "OR year LIKE ? "
				   + "OR director LIKE ? "
				   + "OR stars LIKE ?";
	    String strQuery = "%" + query + "%";
	    int intQuery = 0;
	    try {
	    	intQuery = Integer.parseInt(query);
	    } catch(NumberFormatException e){}
	   
	    try (Connection conn = DataSource.getConnection();
	    		PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {

			pst.setInt(1, intQuery);
			pst.setString(2, strQuery);
			pst.setInt(3, intQuery);
			pst.setString(4, strQuery);
			pst.setString(5, strQuery);
			rs1 = pst.executeQuery();
		   
			while(rs1.next()){
				oneFilm = getNextFilm(rs1);
				films.add(oneFilm);
			}
		   
	   	} catch(SQLException se) {
	   		System.out.println(se); 
	   	} finally {
		    try {
		    	rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	}
	   
	   	return films;
   }

	/**
	 * Retrieve film with passed id parameter
	 * 
	 * @param id					ID of required film
	 * @return ArrayList<Film>
	 */
	public ArrayList<Film> getFilmByID(int id){
		
		ArrayList<Film> filmList = new ArrayList<Film>();
		String SQL_QUERY = "SELECT * FROM films WHERE id = ?";
	    
		try (Connection conn = DataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {

			pst.setInt(1, id);
		    rs1 = pst.executeQuery();
			
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	filmList.add(oneFilm);
		    }
		    
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
		    	rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	}
		
		return filmList;
	}

	/**
	 * Insert film into database. ID will be assigned by database.
	 * 
	 * @param f						Film object to be inserted
	 * @return boolean				Indication of success or failure
	 */
	public boolean insertFilm(Film f) {
		
		boolean insertSuccessful = false;
		String SQL_QUERY = "INSERT INTO "
				+ "films (title, year, director, stars, review) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try  (Connection conn = DataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_QUERY);){

			pst.setString(1, f.getTitle().toUpperCase());
			pst.setInt(2, f.getYear());
			pst.setString(3, f.getDirector().toUpperCase());
			pst.setString(4, f.getStars().toUpperCase());
			pst.setString(5, f.getReview());
	        insertSuccessful = pst.executeUpdate() > 0;
	        
		} catch(SQLException se) {
			System.out.println(se); 
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
		String SQL_QUERY = "UPDATE films SET "
				+ "title = ?, "
		   		+ "year = ?, "
		   		+ "director = ?, "
		   		+ "stars = ?, "
		   		+ "review = ? "
		   		+ "WHERE id = ?;";
		
		try (Connection conn = DataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_QUERY);) {

			pst.setString(1, f.getTitle().toUpperCase());
			pst.setInt(2, f.getYear());
			pst.setString(3, f.getDirector().toUpperCase());
			pst.setString(4, f.getStars().toUpperCase());
			pst.setString(5, f.getReview());
			pst.setInt(6, f.getId());
			updateSuccessful = pst.executeUpdate() > 0;
			
		} catch(SQLException se) { 
			System.out.println(se); 
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
		String SQL_QUERY_1 = "DELETE FROM films WHERE id = ?";
		String SQL_QUERY_2 = "ALTER TABLE films AUTO_INCREMENT = 10001";
	    int intId = 0;
	    try {
	    	intId = Integer.parseInt(id);
	    } catch(NumberFormatException e){}
		
		try (Connection conn = DataSource.getConnection();
				PreparedStatement pst = conn.prepareStatement(SQL_QUERY_1);) {

			pst.setInt(1, intId);
			pst.addBatch();
			pst.addBatch(SQL_QUERY_2);
			int updates[] = pst.executeBatch();
			if(updates[0] > 0) {
				filmDeleted = true;
			}
			
		} catch(SQLException se) { 
			System.out.println(se); 
		}
		
		return filmDeleted;
	}
}
