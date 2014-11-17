package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Ingredient;
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

@Path("ingredient")
public class IngredientResource{
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;

    public IngredientResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Ingredient entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
        
        return null;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Ingredient entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
        
        return null;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
        
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
        
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        em.getTransaction().commit();
        em.close();
        
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return null;
    }
    
}
