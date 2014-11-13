package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Category;
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

@Path("categories")
public class CategoryResource{
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;

    public CategoryResource() {
        
    }

    @POST
    @Consumes("application/json")
    public void create(Category entity) {
        
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void edit(@PathParam("id") Integer id, Category entity) {
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Category find(@PathParam("id") Integer id) {
        return null;
    }

    @GET
    @Produces("application/json")
    public List<Category> findAll() {
        return null;
    }

    @GET
    @Path("count")
    @Produces("application/json")
    public String countREST() {
        return null;
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
