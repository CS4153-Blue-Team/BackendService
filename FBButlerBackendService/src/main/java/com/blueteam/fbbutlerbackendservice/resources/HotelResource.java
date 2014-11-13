package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Hotel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("hotels")
public class HotelResource {
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;
    
    public HotelResource() {
        
    }

    @POST
    @Consumes("application/json")
    public void create(Hotel entity) {
        
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void edit(@PathParam("id") Integer id, Hotel entity) {
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Hotel find(@PathParam("id") Integer id) {
        return null;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Hotel> findAll() {
        em = emf.createEntityManager();
        String queryString = "from Hotels";
        
        
        em.getTransaction().begin();
        List<Hotel> toReturn = em.createQuery(queryString, Hotel.class).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return toReturn;
    }

    @GET
    @Path("count")
    @Produces("application/json")
    public String countREST() {
        return null;
    }
    
}
