// STUDENT ID: w2076700
// STUDENT NAME: AIMAN CHOWDHURY

package com.aiman.campus.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aiman.campus.models.Reading;
import com.aiman.campus.models.Room;
import com.aiman.campus.models.Sensor;
import java.util.List;

public class DataStore {

    public static Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    public static Map<Integer, Sensor> sensors = new ConcurrentHashMap<>();
    public static Map<Integer, List<Reading>> readings = new ConcurrentHashMap<>();
}