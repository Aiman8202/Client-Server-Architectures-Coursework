package com.aiman.campus.resources;

import java.util.Collection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aiman.campus.exceptions.RoomNotEmptyException;
import com.aiman.campus.models.Room;
import com.aiman.campus.storage.DataStore;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    @POST
    public Response createRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);

        return Response.created(
                java.net.URI.create("/api/v1/rooms/" + room.getId()))
                .entity(room)
                .build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") int roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") int roomId) {

        boolean hasSensors = DataStore.sensors.values()
                .stream()
                .anyMatch(sensor -> sensor.getRoomId() == roomId);

        if (hasSensors) {
        	throw new RoomNotEmptyException("Room has active sensors");
        }
        
        Room removed = DataStore.rooms.remove(roomId);

        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok("Room deleted successfully").build();
    
    }
}   