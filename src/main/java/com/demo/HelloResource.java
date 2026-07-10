package com.demo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("hello")
@Produces("application/json")
public class HelloResource {

    @GET
    public MyEntity personalisedHello(@RestQuery("name") String name, @RestQuery("name") String age) {
        return MyEntity.find("name = ?1 and age =?2", name, age).firstResult();
    }
}
