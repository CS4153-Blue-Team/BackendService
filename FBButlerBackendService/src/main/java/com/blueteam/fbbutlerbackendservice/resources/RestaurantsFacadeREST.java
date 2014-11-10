package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Restaurants;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */
@Stateless
@Path("com.blueteam.fbbutlerbackendservice.resources.restaurants")
public class RestaurantsFacadeREST extends AbstractFacade<Restaurants> {
    @PersistenceContext(unitName = "com.BlueTeam_FBButlerBackendService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public RestaurantsFacadeREST() {
        super(Restaurants.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Restaurants entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Restaurants entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Restaurants find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Restaurants> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Restaurants> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
