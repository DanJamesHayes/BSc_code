package com.example.androidmqtt;

public class RoomDetails {

    private String rfidCardId;
    private int rfidReaderId;
    private String roomNo;
    private int lockId;
    private String username;
    private boolean attemptSuccessful;

    public RoomDetails() {
        this.rfidCardId = "unknown";
        this.rfidReaderId = 0;
        this.roomNo = "unknown";
        this.lockId = 0;
        this.username = "unknown";
        this.attemptSuccessful = false;
    }

    public String getRfidCardId() {
        return rfidCardId;
    }

    public void setRfidCardId(String rfidCardId) {
        this.rfidCardId = rfidCardId;
    }

    public int getRfidReaderId() {
        return rfidReaderId;
    }

    public void setRfidReaderId(int rfidReaderId) {
        this.rfidReaderId = rfidReaderId;
    }

    public boolean isAttemptSuccessful() {
        return attemptSuccessful;
    }

    public void setAttemptSuccessful(boolean attemptSuccessful) {
        this.attemptSuccessful = attemptSuccessful;
    }


    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "{\"rfidCardId\":\"" + rfidCardId + "\",\"rfidReaderId\":" + rfidReaderId
                + ",\"roomNo\":\"" + roomNo + "\",\"lockId\":" + lockId + ",\"username\":\""
                + username + "\",\"attemptSuccessful\":" + attemptSuccessful + "}";
    }
}
