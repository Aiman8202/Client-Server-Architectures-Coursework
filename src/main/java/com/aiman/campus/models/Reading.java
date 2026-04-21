// STUDENT ID: w2076700
// STUDENT NAME: AIMAN CHOWDHURY

package com.aiman.campus.models;

import com.aiman.campus.models.Reading;

public class Reading {

    private int id;
    private double value;
    private long timestamp;

    public Reading() {}

    public Reading(int id, double value, long timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}