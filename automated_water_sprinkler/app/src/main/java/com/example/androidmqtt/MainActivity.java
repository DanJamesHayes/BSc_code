package com.example.androidmqtt;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.androidmqttdemoui.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Class which controls the activity of what is shown on the UI and also what is published to mqtt
 * and what happens when a topic that is subscribed to receives a message.
 */
public class MainActivity extends AppCompatActivity {

    public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    String userid = "19003699";
    String clientId = userid + "+sub";

    // The room number here would be set dynamically for different user sign ons but I didn't find
    // time to create a sign in page.  I instead required a pin to be entered to unlock the door
    // as a security check.
    String unlockTopicStr = "unlock_E127";
    String unlockTopicName = userid + "/" + unlockTopicStr;
    String accessDeniedStr = "accessDenied_E127";
    String accessDeniedTopicName = userid + "/" + accessDeniedStr;

    private MqttClient mqttClient;
    private String requestOutcomeJson;

    public final String TOPIC_UNLOCK = userid + "/unlock_E127";

    Button unlockDoorBtn;

    // Username would be set dynamically with user login screen
    private RoomDetails room = new RoomDetails();
    private String username = "User0001";

    // PLEASE SET THIS TO YOUR IP ADDRESS FOR TESTING.  NOT LOCALHOST!!!
    // KEEP PORT NUMBER 8080 UNLESS YOU HAVE ALTERED IT ON THE ECLIPSE SERVER
    private static String accessAttemptURL =
            "http://192.168.0.35:8080/DoorLockServer/DatabaseServer";

    // Notification variables
    private NotificationManager mNotificationManager;
    private static String DEFAULT_CHANNEL_ID = "default_channel";

    /**
     * main method to initiate app setup
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(mNotificationManager);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                DEFAULT_CHANNEL_ID);

        unlockDoorBtn = findViewById(R.id.unlock_door);
        unlockDoorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // call unlock door method in background thread
                new Thread(new Runnable() {
                    public void run() {
                        unlockDoor();
                    }
                }).start();
            }
        });

        // background thread to handle mqtt subscription
        new Thread(new Runnable() {
            public void run() {
                try {
                    mqttClient = new MqttClient(BROKER_URL, clientId,null);
                    mqttClient.setCallback(new MqttCallbackExtended() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            // Unused lost connection method
                        }

                        // method which runs when mqtt message arrives
                        @Override
                        public void messageArrived(String topic, MqttMessage message) {
                            final String topicStr = topic;
                            final String messageStr = message.toString();
                            System.out.println("\nDEBUG: MQTT message recieved");
                            System.out.println("DEBUG: TOPIC: " + topicStr);
                            System.out.println("DEBUG: MESSAGE: " + messageStr);
                            JSONObject messageJson = null;
                            String rfidCard = "";
                            String userAlert;
                            // Rfid card details is parsed from json message
                            try {
                                messageJson = new JSONObject(messageStr);
                                rfidCard = messageJson.get("rfidCardId").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Check if the access attempt is not from the phone app
                            // If not construct notification details detailing access attempt
                            if (!rfidCard.equals("Phone App")) {
                                if (topicStr.equals("19003699/unlock_G.01")) {
                                    userAlert = "Your door has been unlocked by card ID: "
                                            + rfidCard;
                                    builder.setContentTitle("Access Attempt")
                                            .setContentText("Your door has been unlocked by card " +
                                                    "ID: " + rfidCard)
                                            .setSmallIcon(android.R.drawable.ic_menu_view)
                                            .build();
                                } else {
                                    userAlert = "An invalid attempt was made to open your door by" +
                                            " card ID: " + rfidCard;
                                    builder.setContentTitle("Access Attempt")
                                            .setContentText("An invalid attempt was made to open " +
                                                    "your door by card ID: " + rfidCard)
                                            .setSmallIcon(android.R.drawable.ic_menu_view)
                                            .build();
                                }
                                final String finalUserAlert = userAlert;
                                final Notification notification = builder.build();
                                // Run notification and UI update in main thread
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        mNotificationManager.notify(1, notification);
                                        TextView sensorValueTV =
                                                (TextView) findViewById(R.id.unlockMessageValueTV);
                                        sensorValueTV.setText(finalUserAlert);
                                    }
                                });
                                if ((unlockTopicName + "/LWT").equals(topic) ||
                                        (accessDeniedTopicName + "/LWT").equals(topic)) {
                                    System.err.println("DEBUG: RFID reader inactive.");
                                }
                            // If access attempt was made by phone app and was successful, update UI
                            // on main thread.
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                    TextView sensorValueTV =
                                            (TextView) findViewById(R.id.unlockMessageValueTV);
                                    sensorValueTV.setText("Unlock Successful");
                                    EditText unlockET = findViewById(R.id.roomPinValue);
                                    unlockET.setText("");
                                    }
                                });
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {
                            // unused method
                        }

                        @Override
                        public void connectComplete(boolean b , String s) {
                            // unused method
                        }
                    });
                    // Call method to start MQTT subscribing
                    startSubscribing();
                } catch (MqttException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();
    }

    /**
     * Method to subscribe to successful and unsuccessful access attempt MQTT topics
     */
    public void startSubscribing() {
        try {
            mqttClient.connect();
            final String brightTopic = unlockTopicName;
            mqttClient.subscribe(brightTopic);
            System.out.println("\nDEBUG: Subscriber is now listening to "+ unlockTopicStr);
            final String tempTopic = accessDeniedTopicName;
            mqttClient.subscribe(tempTopic);
            System.out.println("DEBUG: Subscriber is now listening to "+ accessDeniedStr);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to call security check and update MQTT topic and UI based on result
     */
    public void unlockDoor() {
        try {
            final MqttTopic unlockTopic = mqttClient.getTopic(TOPIC_UNLOCK);
            EditText unlockET = findViewById(R.id.roomPinValue);
            final int lockId = Integer.parseInt(String.valueOf(unlockET.getText()));
            room.setLockId(lockId);
            room.setRfidCardId("Phone App");
            room.setUsername(username);
            room.setRoomNo("G.01");
            final JSONObject roomJson = new JSONObject(room.toString());

            System.out.println("\nDEBUG: Conducting security check");
            System.out.println("DEBUG: ROOM DETAILS SENT: " + roomJson.toString());
            // Call method to conduct security check
            requestOutcomeJson = sendAttemptToServer(roomJson.toString());
            JSONObject resultJson = new JSONObject(requestOutcomeJson);
            System.out.println("\nDEBUG: Security Check outcome received.");
            System.out.println("DEBUG: JSON received: " + requestOutcomeJson);

            // If security check successful publish to MQTT topic
            if(resultJson.get("attemptSuccessful").toString().equals("true")) {
                System.out.println("\nDEBUG: Publishing to MQTT unlock topic");
                System.out.println("DEBUG: MESSAGE: " + roomJson.toString());
                unlockTopic.publish(new MqttMessage(roomJson.toString().getBytes()));
            // If security check unsuccessful update UI in main thread
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                    TextView sensorValueTV =
                            (TextView) findViewById(R.id.unlockMessageValueTV);
                    sensorValueTV.setText("Pin Incorrect");
                    EditText unlockET = findViewById(R.id.roomPinValue);
                    unlockET.setText("");
                    }
                });
            }
        } catch(JSONException | MqttException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send access attempt details to database server in JSON format
     *
     * @param accessAttemptJson
     * @return json string of results from security check
     * @throws IOException
     */
    public String sendAttemptToServer(String accessAttemptJson)
            throws IOException {
        URL url;
        HttpURLConnection conn;
        BufferedReader br;
        accessAttemptJson = URLEncoder.encode(accessAttemptJson, "UTF-8");
        String fullURL = accessAttemptURL + "?attemptdata=" + accessAttemptJson;
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
     * Method to create notification channel
     *
     * @param notificationManager
     */
    private static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                String DEFAULT_CHANNEL_NAME = "Default";
                notificationManager.createNotificationChannel(new NotificationChannel(
                        DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
        }
    }
}
