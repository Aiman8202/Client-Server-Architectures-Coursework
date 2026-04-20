package com.aiman.campus.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aiman.campus.exceptions.SensorUnavailableException;
import com.aiman.campus.models.Reading;
import com.aiman.campus.models.Sensor;
import com.aiman.campus.storage.DataStore;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private int sensorId;

    public SensorReadingResource(int sensorId) {
        this.sensorId = sensorId;
    }

    // GET /readings
    @GET
    public List<Reading> getReadings() {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

    // POST /readings
    @POST
    public Response addReading(Reading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        // 🔴 Part 5 requirement (already preparing)
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
        	throw new SensorUnavailableException("Sensor under maintenance");
        }

        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        // Side effect: update current value
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}