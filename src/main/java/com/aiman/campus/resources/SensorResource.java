package com.aiman.campus.resources;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aiman.campus.exceptions.LinkedResourceNotFoundException;
import com.aiman.campus.models.Sensor;
import com.aiman.campus.storage.DataStore;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    //  POST /sensors
    @POST
    public Response createSensor(Sensor sensor) {

        // Validate if room exists
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
        	throw new LinkedResourceNotFoundException("Room does not exist");
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        return Response.created(
                java.net.URI.create("/api/v1/sensors/" + sensor.getId()))
                .entity(sensor)
                .build();
    }

    //  GET /sensors
    @GET
    public Collection<Sensor> getAllSensors(@QueryParam("type") String type) {

        if (type == null) {
            return DataStore.sensors.values();
        }

        return DataStore.sensors.values()
                .stream()
                .filter(sensor -> sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    //  GET /sensors/{id}
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") int id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    //  Sub-resource locator
    // /sensors/{id}/readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") int sensorId) {
        return new SensorReadingResource(sensorId);
    }
}