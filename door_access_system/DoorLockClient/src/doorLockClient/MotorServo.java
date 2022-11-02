package doorLockClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.google.gson.Gson;
import com.phidget22.PhidgetException;
import com.phidget22.RCServo;

/**
 * Class to control the servo motor for a lock which is specified when the 
 * constructor is called. The lock serial number is used once and never stored.
 * 
 * @author Daniel Hayes
 *
 */
public class MotorServo {
	
	private RCServo servo = new RCServo();
	private Gson gson = new Gson();
	private RoomDetails room = new RoomDetails();
	private String roomJson;
	
    private static String getRoomURL = 
    		"http://localhost:8080/DoorLockServer/DatabaseServer";
    
    private static final String BROKER_URL = 
    		"tcp://broker.mqttdashboard.com:1883";
    private static final String userid = "19003699";
    private String clientId = userid + "-sub";
    private MqttClient mqttClient;
	
	/**
	 * Constructor method opens the motor servo, collects the servo serial
	 * number and passes it to the DatabaseServer to find the corresponding room
	 * number.  The room number is then added to the mqtt subscribe topic before
	 * the topic is subscribed to.  The room number and lock id are passed to 
	 * unlock singleton to be compared to those that are published to Mqtt by 
	 * the rfid reader.  Exceptions are thrown to the main method.
	 * 
	 * @param lockID
	 * @throws PhidgetException
	 * @throws InterruptedException
	 * @throws MqttException 
	 */
    private MotorServo() throws PhidgetException, 
			InterruptedException, MqttException {
    	
    	// Retrieving servo serial number
		servo.open(5000);
		int LockId = servo.getDeviceSerialNumber();
        Thread.sleep(500);
        servo.close();
        
        // Retrieving corresponding room details to servo serial number
		room.setLockId(LockId);
		roomJson = gson.toJson(room);
        System.out.println("DEBUG: Requesting room details from database");
        System.out.println("DEBUG: json string sent: " + roomJson);
		try {
			roomJson = getRoom(roomJson);
	        System.out.println("\nDEBUG: Room details recieved from server: " 
	        		+ roomJson);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Setting retrieved room number to unlock singleton
		room = gson.fromJson(roomJson, RoomDetails.class);
		UnlockSingleton.setLockDetails(room);
		
		// Subscribing to unlock MQTT topic with retrieved room number
        mqttClient = new MqttClient(BROKER_URL, clientId);
        mqttClient.setCallback(new MqttSubscriberCallback());
        mqttClient.connect();
        final String topic = userid+"/unlock_" + room.getRoomNo();
        mqttClient.subscribe(topic);
        System.out.println("DEBUG: Subscribing to MQTT topic: " + topic);
	}
	
	/**
	 * Method to gain the corresponding room number to the servo serial number.
	 * Exceptions are thrown to the constructor method.
	 * 
	 * @param lockIdJson
	 * @return room number
	 * @throws IOException
	 */
    private String getRoom(String lockIdJson) 
			throws IOException {
		URL url;
        HttpURLConnection conn;
        BufferedReader br;
        lockIdJson = URLEncoder.encode(lockIdJson, "UTF-8");
        String fullURL = getRoomURL + "?getRoom="+lockIdJson;
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
	
	/**
	 * Main method calls the constructor method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MotorServo();
		} catch (PhidgetException | InterruptedException | MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
