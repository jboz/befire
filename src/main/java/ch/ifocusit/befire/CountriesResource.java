package ch.ifocusit.befire;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
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
import org.reactivestreams.Publisher;
import io.smallrye.mutiny.Multi;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountriesResource {

    private final Map<String, String> repository = new HashMap<>();

    private Publisher<String> countries;

    @PostConstruct
    public void init() {
        repository.put("France", "France");
        repository.put("Italie", "Italie");


    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<String> getAll() {
        return Multi.createFrom().publisher(countries);
    }

    @GET
    @Path("/{country}")
    public String getCountry(@PathParam("country") String country) {
        return repository.get(country);
    }

    @POST
    @Path("/{country}")
    public Response addCountry(@PathParam("country") String country, @Context UriInfo uriInfo) {
        this.repository.put(country, country);
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
    }
}
