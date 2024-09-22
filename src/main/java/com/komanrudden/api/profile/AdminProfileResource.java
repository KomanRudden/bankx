package com.komanrudden.api.profile;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.security.Authenticated;

@Path("/api/admin")
@Authenticated
public class AdminProfileResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String admin() {
        return "granted";
    }
}