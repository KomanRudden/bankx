package com.komanrudden.api.profile;

import com.komanrudden.model.data.User;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.NoCache;

@Path("/api/users")
public class MeProfileResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    @NoCache
    public User me() {
        return new User(identity);
    }
}
