package doorLockClient;

import com.phidget22.PhidgetException;
import com.phidget22.RCServo;

public class UnlockSingleton {

	private static RCServo servo = null;
	private static RoomDetails room = new RoomDetails();

	public static RCServo getInstance() throws PhidgetException {
		if(servo == null) {
			new UnlockSingleton();
		}
		return servo;
	}
	
	private UnlockSingleton() throws PhidgetException {
		servo = new RCServo();
		servo.open(2000);
	}
	
	/**
	 * Method to set the room number of the room details object.
	 * 
	 * @param r
	 */
	public static void setLockDetails(RoomDetails r) {
		room.setLockId(r.getLockId());
	}
	
	/**
	 * This method compares the lockId from the motorservo and the
	 * messaged published to the mqtt topic and if they match the door is 
	 * unlocked.  Excpetions are thrown to the mqtt callback class.
	 * 
	 * @param roomNo
	 * @param lockId
	 * @throws PhidgetException
	 * @throws InterruptedException
	 */
	public static void unlock(int lockId) 
			throws PhidgetException, InterruptedException {
        System.out.println("DEBUG: Security check comparing device serial "
        		+ "number: " + room.getLockId() + " with passed argument: " 
        		+ lockId);
		if (room.getLockId() == lockId) {
			UnlockSingleton.getInstance();
			servo.setTargetPosition(180);
			servo.setEngaged(true);
	        Thread.sleep(5000);
	        servo.setTargetPosition(0);
	        Thread.sleep(1500);
		}
	}
}
