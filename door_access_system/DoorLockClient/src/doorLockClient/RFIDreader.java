package doorLockClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.google.gson.Gson;
import com.phidget22.PhidgetException;
import com.phidget22.RFID;
import com.phidget22.RFIDTagEvent;
import com.phidget22.RFIDTagListener;
import com.phidget22.RFIDTagLostEvent;
import com.phidget22.RFIDTagLostListener;

/**
 * 
 * @author Daniel Hayes
 *
 */
public class RFIDreader {
	
	private Gson gson = new Gson();
	private RFID rfid = new RFID();
	private RoomDetails accessRequest = new RoomDetails();
	private String accessRequestJson;
	private RoomDetails requestOutcome = new RoomDetails();
	private String requestOutcomeJson;
	private static String accessAttemptURL = 
    		"http://localhost:8080/DoorLockServer/DatabaseServer";
	private MqttPublisher publish = new MqttPublisher();
    
	
	/**
	 * Main function which calls the class constructor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new RFIDreader();
		} catch (PhidgetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Class constructor which activates the RFID reader for 10 minutes.  In 
	 * reality the reader would be listening permanently or a set time frame
	 * specified by business requirements.
	 * 
	 * @throws PhidgetException
	 * @throws InterruptedException
	 */
	private RFIDreader() throws PhidgetException, InterruptedException {
		
		
		/**
		 * Method which reacts to RFID cards being presented to the reader 
		 * and passes the card and reader serial numbers to the security
		 * check method in the DatabaseServer class.
		 */
		RFIDTagListener listener = new RFIDTagListener() {
			@Override
			public void onTag(RFIDTagEvent arg0) {
				
				// retrieving rfid reader serial number
				int readerId = 0;
				try {
					readerId = rfid.getDeviceSerialNumber();
				} catch (PhidgetException e1) {
					e1.printStackTrace();
				}
				
				// setting rfid reader and tag serial numbers to RoomDetails 
				// object and creating json string
				String cardId = arg0.getTag();
				accessRequest.setRfidCardId(cardId);
				accessRequest.setRfidReaderId(readerId);
				accessRequestJson = gson.toJson(accessRequest);
				
				// Sending json string to database server to make access request
		        System.out.println("\n\nDEBUG: Making security check with "
		        		+ "database server");
		        System.out.println("DEBUG: JSON details sent to server: " 
		        		+ accessRequestJson);
				try {
					requestOutcomeJson = sendAttemptToServer(accessRequestJson);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// Creating RoomDetails object from json string returned from
				// database
		        System.out.println("\nDEBUG: JSON details recieved from "
		        		+ "server: " + requestOutcomeJson);
				requestOutcome = gson.fromJson(requestOutcomeJson, 
						RoomDetails.class);
				
				/*
				 * The MqttPublish class is called with details of the 
				 * suitable topic dependent on the on the result of the 
				 * security check
				 */
				publish.setTopic(requestOutcome.getRoomNo());
				if(requestOutcome.isAttemptSuccessful() == true) {
					try {
						publish.publishUnlock(requestOutcomeJson);
					} catch (MqttException e) {
						e.printStackTrace();
					}
				} else {
					try {
						publish.publishAccessDenied(requestOutcomeJson);
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}
			}
		};
		RFIDTagLostListener lostlistener = new RFIDTagLostListener() {
			@Override
			public void onTagLost(RFIDTagLostEvent arg0) {
				// unused method
			}
		};
		rfid.addTagListener(listener);
		rfid.addTagLostListener(lostlistener);
		rfid.open(5000);
        rfid.setAntennaEnabled(true);
        System.out.println("DEBUG: RFID activated for 10 minutes");
		Thread.sleep(600000);
		rfid.close();
        System.out.println("\n\nDEBUG: Closed RFID");
	}
	
	/**
	 * Method which takes the rfidReader and cardId of an access attempt in json
	 * format and sends the details to the server to be checked for validity
	 * in the database. Room and lock details are returned if attempt succeeds.
	 * Exceptions thrown back to tag reader in the class constructors.
	 * 
	 * @param accessAttemptJson
	 * @return string containing room and lock details
	 * @throws IOException
	 */
	public String sendAttemptToServer(String accessAttemptJson) 
			throws IOException {
		URL url;
        HttpURLConnection conn;
        BufferedReader br;
        accessAttemptJson = URLEncoder.encode(accessAttemptJson, "UTF-8");
        String fullURL = accessAttemptURL + "?attemptdata="+accessAttemptJson;
        String line;
        String result = "";
        url = new URL(fullURL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = br.readLine()) != null) {
           result += line;
        }
        br.close();
        return result;  
	}
}
