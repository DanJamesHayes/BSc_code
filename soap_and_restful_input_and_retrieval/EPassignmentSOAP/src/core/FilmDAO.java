package core;

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
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs1 = null;
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
	 * @return Film[]		
	 */
	public Film[] getAllFilms() {
		
		ArrayList<Film> allFilms = new ArrayList<Film>();
	    String selectSQL = "SELECT * FROM films";
	    
		try{
			openConnection();
			pst = conn.prepareStatement(selectSQL);
		    rs1 = pst.executeQuery();
		    
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	allFilms.add(oneFilm);
		    }
		    
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
		    	rs1.close();
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		
		return allFilms.toArray(new Film[allFilms.size()]);
	}

	/**
	 * Retrieve all films that match passed search query. If query does not 
	 * parse to integer 0 is used to check ID and year column.
	 * 
	 * @param query					String of search query
	 * @return Film[]
	 */
	public Film[] getFilm(String query){
		
		ArrayList<Film> films = new ArrayList<Film>();
	    String selectSQL = "select * from films "
				   + "where id like ? "
				   + "or title like ? "
				   + "or year like ? "
				   + "or director like ? "
				   + "or stars like ?";
	    String strQuery = "%" + query + "%";
	    int intQuery = 0;
	    try {
	    	intQuery = Integer.parseInt(query);
	    } catch(NumberFormatException e){}
	    
		try{
			openConnection();
			pst = conn.prepareStatement(selectSQL);
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
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		
		return films.toArray(new Film[films.size()]);
	}
	
	/**
	 * Retrieve film with passed id parameter
	 * 
	 * @param id					ID of required film
	 * @return Film[]
	 */
	public Film[] getFilmByID(int id){
		
		ArrayList<Film> filmList = new ArrayList<Film>();
	    String selectSQL = "select * from films where id=?";
	    
		try{
			openConnection();
			pst = conn.prepareStatement(selectSQL);
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
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    closeConnection();
		}
		
		return filmList.toArray(new Film[filmList.size()]);
	}
	
	/**
	 * Insert film into database. ID will be assigned by database.
	 * 
	 * @param f						Film object to be inserted
	 * @return boolean				Indication of success or failure
	 */
	public boolean insertFilm(Film f) {
		
		boolean insertSuccessful = false;
		String insertSQL = "insert into "
				+ "films (title, year, director, stars, review) "
				+ "values (?, ?, ?, ?, ?)";
		
		try {
			openConnection();
			pst = conn.prepareStatement(insertSQL);
			pst.setString(1, f.getTitle().toUpperCase());
			pst.setInt(2, f.getYear());
			pst.setString(3, f.getDirector().toUpperCase());
			pst.setString(4, f.getStars().toUpperCase());
			pst.setString(5, f.getReview());
			insertSuccessful = pst.executeUpdate() > 0;
			
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
				+ "title = ?, "
		   		+ "year = ?, "
		   		+ "director = ?, "
		   		+ "stars = ?, "
		   		+ "review = ? "
		   		+ "where id = ?;";
		
		try {
			openConnection();
			pst = conn.prepareStatement(updateSql);
			pst.setString(1, f.getTitle().toUpperCase());
			pst.setInt(2, f.getYear());
			pst.setString(3, f.getDirector().toUpperCase());
			pst.setString(4, f.getStars().toUpperCase());
			pst.setString(5, f.getReview());
			pst.setInt(6, f.getId());
			updateSuccessful = pst.executeUpdate() > 0;
			
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		
		// delete film
		boolean filmDeleted = false;
		String deleteSql = "DELETE FROM films WHERE id = ?";
		String resetAutoInc = "ALTER TABLE films AUTO_INCREMENT = 10001";
	    int intId = 0;
	    try {
	    	intId = Integer.parseInt(id);
	    } catch(NumberFormatException e){}
		
		try {
			openConnection();
			pst = conn.prepareStatement(deleteSql);
			pst.setInt(1, intId);
			pst.addBatch();
			pst.addBatch(resetAutoInc);
			int updates[] = pst.executeBatch();
			if(updates[0] > 0) {
				filmDeleted = true;
			}
			
		} catch(SQLException se) { 
			System.out.println(se); 
		} finally {
		    try {
			    pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection();
		}
		
		return filmDeleted;
	}
}
