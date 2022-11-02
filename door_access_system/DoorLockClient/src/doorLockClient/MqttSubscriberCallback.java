
package doorLockClient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.phidget22.PhidgetException;

/**
 * Subscriber callback class which is called when the servo motor subscriber is
 * triggered.
 * 
 * @author Daniel Hayes
 *
 */
public class MqttSubscriberCallback implements MqttCallback {

	private static final String userid = "19003699";
    private Gson gson = new Gson();
    private RoomDetails room = new RoomDetails();
    
	@Override
	public void connectionLost(Throwable arg0) {
		// Unused method
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// Unused method
		
	}

	/**
	 * Method reacts to messages being published to the unlock room topic.
	 * The method is parsed to json and then used to create a RoomDetails
	 * object. The lockId is retrieved from the object and passed as
	 * arguments to the UnlockSingleton unlock method.
	 */
	@Override
	public void messageArrived(String arg0, MqttMessage arg1) {
		String messageStr = arg1.toString();
        System.out.println("\n\nDEBUG: MQTT message recieved: " + messageStr);
		room = gson.fromJson(messageStr, RoomDetails.class);
		int lockId = room.getLockId();
		
		// Calling unlock method with lock id taken from MQTT message
		try {
			UnlockSingleton.unlock(lockId);
		} catch (PhidgetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ((userid+"/LWT").equals(arg0)) {
            System.err.println("RFID reader disconnected");
        }
	}

}
