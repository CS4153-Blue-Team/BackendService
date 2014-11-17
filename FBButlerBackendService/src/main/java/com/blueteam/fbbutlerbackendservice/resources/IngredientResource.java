package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Ingredient;
import com.blueteam.fbbutlerbackendservice.pojos.MenuItem;
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
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Ingredient entity) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Ingredient old = em.find(Ingredient.class, id);
        
        if (entity.getName() != null && !entity.getName().equals(""))
        {
            old.setName(entity.getName());
        }
        if (entity.getRestaurant() != null)
        {
            old.setRestaurant(entity.getRestaurant());
        }
        if (entity.getInStock() != null)
        {
            old.setInStock(entity.getInStock());
        }
        em.persist(old);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(old).build();
    }

    @PUT
    @Path("{id}/menuItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIngredient(@PathParam("id") Integer id, MenuItem menuItem) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Ingredient ingredient = em.find(Ingredient.class, id);
        ingredient.addMenuItem(menuItem);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(ingredient).build();
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Ingredient ingredient = em.find(Ingredient.class, id);
        em.remove(ingredient);
        em.getTransaction().commit();
        em.close();
        
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Ingredient ingredient = em.find(Ingredient.class, id);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(ingredient).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        em = emf.createEntityManager();
        String queryString = "from Ingredient";
        
        em.getTransaction().begin();
        List<Ingredient> toReturn = em.createQuery(queryString, Ingredient.class).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("restaurant/{id}")
    @Produces("application/json")
    public Response findAllForRestaurant(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        String queryString = "select * from Ingredients where restaurant = ?1";
        
        em.getTransaction().begin();
        List<Ingredient> toReturn = em.createNativeQuery(queryString, Ingredient.class).setParameter(1, id).getResultList();
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
