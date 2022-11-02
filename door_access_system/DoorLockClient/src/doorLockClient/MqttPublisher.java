package doorLockClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * Mqtt publisher class which publishes two topics depending on the outcome of
 * security check when an access attempt is made.
 *  
 * @author Daniel Hayes
 *
 */
public class MqttPublisher {


    public static final String BROKER_URL = 
    		"tcp://broker.mqttdashboard.com:1883";
    public static final String userid = "19003699";
    public static String TOPIC_UNLOCK;
    public static String TOPIC_ACCESSDENIED;
    
    public void setTopic(String roomNo) {
    	TOPIC_UNLOCK = userid + "/unlock" + "_" + roomNo;
    	TOPIC_ACCESSDENIED = userid + "/accessDenied" + "_" + roomNo;
    }
    
    private MqttClient client;
    
    /**
     * Method to create mqtt publisher session
     */
    public MqttPublisher() {

        try {
			client = new MqttClient(BROKER_URL, userid);
	        MqttConnectOptions options = new MqttConnectOptions();
	        options.setCleanSession(false);
	        options.setWill(client.getTopic(userid + "/LWT"), 
	        		"RFID reader disconnected".getBytes(), 0, false);
	        client.connect(options);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Method to publish the unlock topic with the room details as a message.
     * Exceptions thrown back to the RFID reader class.
     *  
     * @param room
     * @throws MqttException
     */
    public void publishUnlock(String room) throws MqttException {
        final MqttTopic unlockTopic = client.getTopic(TOPIC_UNLOCK);
        final String roomDetailStr = room + "";
        unlockTopic.publish(new MqttMessage(roomDetailStr.getBytes()));
        System.out.println("DEBUG: Published data to MQTT.\n		Topic: " 
        		+ unlockTopic.getName() + "\n		Message: " + room);
    }
    
    /**
     * Method to publish the access denied topic with the room details as a 
     * message. Exceptions thrown back to the RFID reader class.
     * 
     * @param room
     * @throws MqttException
     */
    public void publishAccessDenied(String room) throws MqttException {
        final MqttTopic deniedTopic = client.getTopic(TOPIC_ACCESSDENIED);
        final String roomStr = room + "";
        deniedTopic.publish(new MqttMessage(roomStr.getBytes()));
        System.out.println("Published data. Topic: " + deniedTopic.getName() + 
        		"   Message: " + room);
    }
}
