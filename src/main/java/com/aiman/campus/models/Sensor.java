package com.aiman.campus.models;

public class Sensor {

    private int id;
    private String type;
    private String status;
    private int roomId;
    private double currentValue;

    public Sensor() {
    }

    public Sensor(int id, String type, String status, int roomId, double currentValue) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.roomId = roomId;
        this.currentValue = currentValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
}