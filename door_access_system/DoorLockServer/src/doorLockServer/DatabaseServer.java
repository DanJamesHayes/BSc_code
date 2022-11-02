package doorLockServer;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Class to implement a database connection and execute sql statements
 * 
 * @author Daniel Hayes
 *
 */
@WebServlet("/DatabaseServer")
public class DatabaseServer extends HttpServlet {

	/**
	 * Auto-generated generic serialisation ID
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private Statement stmt;
	private Gson gson = new Gson();
	private RoomDetails room = new RoomDetails();
	
	/**
	 * Initialisation method runs when servlet called, calls parent method.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("DEBUG: Database server is running");
	}
	
	/**
	 * Method to connect to the mysql database and throw exceptions to the 
	 * method that calls it.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void getConnection() throws InstantiationException, 
			IllegalAccessException, ClassNotFoundException, SQLException {
		String user = "hayesd";
		String password = "jemptOll3";
		String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, user, password);
		stmt = conn.createStatement();
	}
	
	/**
	 * Method to close the connection to the mysql database and throw exceptions
	 * to the method that calls it.
	 * 
	 * @throws SQLException
	 */
	private void closeConnection() throws SQLException {
		conn.close();
	}
	
	/**
	 * Unused destroy method.
	 */
	public void destroy() {
		try {
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Class constructor calls parent constructor
	 */
	public DatabaseServer() {
		super();
	}
	
	/**
	 * Method receives HTTP request from rfid reader or motor servo. If the 
	 * request is from the rfid reader a security check is made and the access 
	 * record on mysql is updated with the results. The room details where the 
	 * access attempt was made are added to the room object. If the request is 
	 * from the motor servo the room details are populated based on the lockId.
	 * The room object is printed out as a response in json format.
	 */
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
	    response.setStatus(HttpServletResponse.SC_OK);
		// attemptSuccessful is automatically set to false when constructor
		// is called. Can only be set to true if security check passes.
		String attemptJsonString = request.getParameter("attemptdata");
		String getRoom = request.getParameter("getRoom");
		// Check if request is to get room details
		if(getRoom != null) {
			room = gson.fromJson(getRoom, RoomDetails.class);
			try {
				getRoomDetails(room);
			} catch (InstantiationException | IllegalAccessException 
					| ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Check if request is an access attempt
			if(attemptJsonString != null) {
				room = gson.fromJson(attemptJsonString, RoomDetails.class);
				try {
					securityCheck(room);
					updateAccessRecord(room);
				} catch (InstantiationException | IllegalAccessException 
						| ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// Print results to http response and close connection
		String roomJson = gson.toJson(room);
		PrintWriter out = response.getWriter();
		out.println(roomJson);
		out.close();
	}
	
	/**
	 * Post requests are passed to the doGet method
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
	
	/**
	 * The RFID card and reader from the access attempt are passed to mysql.  If
	 * the reader and card are linked with the same room number the security
	 * check is successful and set as true. If the reader and card are not 
	 * linked to the same room the the attemptSuccessful variable will be set to
	 * false and the room object will be populated with details that are 
	 * assigned to the RFID reader. 
	 * 
	 * If the request is sent from the phone app the room table is checked to 
	 * see if the room number and lock id are linked. If they are the unlock 
	 * attempt is successful.
	 * 
	 * Exceptions are thrown to the doGet method.
	 * 
	 * @param room
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void securityCheck(RoomDetails room) 
			throws InstantiationException, IllegalAccessException, 
			ClassNotFoundException, SQLException {
		System.out.println("\nDEBUG: Connecting to database for security check "
				+ " with " + room.toString());
		if(room.getRfidCardId().equals("Phone App")) {
			String securityCheckSQL = 
					"SELECT COUNT(*) "
					+ "FROM room "
					+ "WHERE lockId = " + room.getLockId() + " "
					+ "AND roomNumber IN ("
						+ "SELECT roomNumber "
						+ "FROM card "
						+ "WHERE username = '" + room.getUsername() 
						+ "' );";
	        getConnection();
			ResultSet rs = stmt.executeQuery(securityCheckSQL);
			int count = 0;
			while (rs.next()){
		        count = rs.getInt("COUNT(*)");
			}
	        closeConnection();
			if(count == 1) {
	        	room.setAttemptSuccessful(true);
			}
		} else {
			String securityCheckSQL = 
					"SELECT roomNumber, lockId "
					+ "FROM room "
					+ "WHERE rfidReaderId = " + room.getRfidReaderId() + " "
					+ "AND roomNumber IN ("
						+ "SELECT roomNumber "
						+ "FROM card "
						+ "WHERE rfidCardId = '" + room.getRfidCardId() 
						+ "' );";
	        getConnection();
			ResultSet rs = stmt.executeQuery(securityCheckSQL);
			while (rs.next()){
		        room.setRoomNo(rs.getString("roomNumber"));
		        room.setLockId(rs.getInt("lockId"));
			}
	        closeConnection();
			System.out.println("DEBUG: Details returned: " + room.toString());
			
			// If valid lock id returned and set attemptSucceccful true if not
			// retrieve room details from database
	        if(room.getLockId() == 0) {
				System.out.println("\nDEBUG: Security check failed. Retrieving "
						+ "room details from database." + room.toString());
	        	String securityFailSQL = 
	        			"SELECT roomNumber "
	        			+ "FROM room "
	        			+ "WHERE rfidReaderId = " + room.getRfidReaderId() 
	        			+ ";";
	        	getConnection();
	    		ResultSet frs = stmt.executeQuery(securityFailSQL);
	    		while (frs.next()){
	    	        room.setRoomNo(frs.getString("roomNumber"));
	    		}
	            closeConnection();
				System.out.println("DEBUG: Room details received from "
						+ "database:" + room.toString());
	        } else {
				System.out.println("\nDEBUG: Security check successful");
	        	room.setAttemptSuccessful(true);
	        }
		}
	}
	
	/**
	 * Method to update the access record table in the mysql database. 
	 * Exceptions are thrown back to the securityCheck method.
	 * 
	 * @param oneAttempt
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void updateAccessRecord (RoomDetails room) 
			throws InstantiationException, IllegalAccessException, 
			ClassNotFoundException, SQLException{
		String sqlStmt = "insert into accessRecord(rfidCardId, roomNumber, "
				+ "attemptSuccssful, attemptTime) " 
				+ "values('" + room.getRfidCardId() + "','" 
				+ room.getRoomNo() + "'," + room.isAttemptSuccessful() 
				+ "," + "now());";
        getConnection();
        stmt.executeUpdate(sqlStmt);
        closeConnection();
	}
	
	/**
	 * This method populates the room object with details based to the lockId.
	 * 
	 * @param room
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void getRoomDetails(RoomDetails room) throws InstantiationException, 
			IllegalAccessException, ClassNotFoundException, SQLException {
		String sqlStmt = 
				"SELECT roomNumber "
				+ "FROM room "
				+ "WHERE lockId = " + room.getLockId()+ ";";
		getConnection();
		ResultSet rs = stmt.executeQuery(sqlStmt);
		while (rs.next()){
	        room.setRoomNo(rs.getString("roomNumber"));
		}
        closeConnection();
	}
}
