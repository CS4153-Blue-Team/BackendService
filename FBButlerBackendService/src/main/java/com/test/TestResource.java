package com.test;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 */
@Path("test")
public class TestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
    }

    /**
     * Retrieves representation of an instance of com.test.TestResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("text")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getText() {
        
        return Response.ok("This is a test").build();
    }

    @Path("json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() throws JSONException {
        JSONObject toReturn = new JSONObject();
        toReturn.put("JSON", "This is a test");
        toReturn.put("Test", "This is also a test");
        toReturn.put("An object", (new JSONObject()).put("123", "456"));
        return Response.ok(toReturn.toString()).build();
    }
    
    /**
     * PUT method for updating or creating an instance of TestResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
