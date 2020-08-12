package ch.ifocusit.befire;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.SseElementType;

import io.smallrye.mutiny.Multi;

@Path("/countries")
public class CountriesResource {

    private final Map<String, String> countries = new HashMap<>();

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<String> getAll() {
        return Multi.createFrom().items("France", "Italie");
    }

    @GET
    @Path("/{country}")
    public String getCountry(@PathParam("country") String country) {
        return countries.get(country);
    }

    @POST
    @Path("/{country}")
    public Response addCountry(@PathParam("country") String country, @Context UriInfo uriInfo) {
        this.countries.put(country, country);
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
    }
}