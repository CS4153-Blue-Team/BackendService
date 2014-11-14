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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("hotel")
public class HotelResource {
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;
    
    public HotelResource() {
        
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Hotel entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(entity, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Hotel entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Hotel old = em.find(Hotel.class, id);
        old.setName(entity.getName());
        old.setPictureLocation(entity.getPictureLocation());
        em.persist(old);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(old, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Hotel old = em.find(Hotel.class, id);
        em.remove(old);
        em.getTransaction().commit();
        em.close();   
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Hotel hotel = em.find(Hotel.class, id);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(hotel).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        em = emf.createEntityManager();
        String queryString = "from Hotel";
        
        
        em.getTransaction().begin();
        List<Hotel> toReturn = em.createQuery(queryString, Hotel.class).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return null;
    }
    
}
