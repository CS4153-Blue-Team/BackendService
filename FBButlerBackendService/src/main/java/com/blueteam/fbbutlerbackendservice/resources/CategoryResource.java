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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("category")
public class CategoryResource{
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;

    public CategoryResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Category entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Category entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Category old = em.find(Category.class, id);
        if(entity.getName() != null || !entity.getName().equals(""))
        {
            old.setName(entity.getName());
        }
        if(entity.getRestaurant() != null)
        {
            old.setRestaurant(entity.getRestaurant());
        }
        em.persist(old);
        em.getTransaction().commit();
        em.close();
        
        return null;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Category old = em.find(Category.class, id);
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
        Category category = em.find(Category.class, id);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(category).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        em = emf.createEntityManager();
        String queryString = "from Category";
        
        em.getTransaction().begin();
        List<Category> toReturn = em.createQuery(queryString, Category.class).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("restaurant/{id}")
    @Produces("application/json")
    public Response findAllForRestaurant(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        String queryString = "select * from Categories where restaurant = ?1";
        
        em.getTransaction().begin();
        List<Category> toReturn = em.createNativeQuery(queryString, Category.class).setParameter(1, id).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }

    @GET
    @Path("count")
    @Produces("application/json")
    public String countREST() {
        return null;
    }
}
