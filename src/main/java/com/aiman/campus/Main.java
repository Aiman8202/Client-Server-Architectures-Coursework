package com.aiman.campus;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.aiman.campus.resources.DiscoveryResource;
import com.aiman.campus.resources.SensorResource;
import com.aiman.campus.resources.SensorRoomResource;

public class Main {

    public static void main(String[] args) {

        ResourceConfig config = new ResourceConfig();
        config.register(DiscoveryResource.class);
        config.register(SensorRoomResource.class);
        config.register(SensorResource.class);
        config.packages("com.aiman.campus");
        
        URI baseUri = URI.create("http://localhost:8080/api/v1/");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        System.out.println("======================================");
        System.out.println(" Campus Sensor API Started");
        System.out.println(" Base URL: http://localhost:8080/api/v1");
        System.out.println(" Rooms:   http://localhost:8080/api/v1/rooms");
        System.out.println("======================================");

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        
    }
    
}