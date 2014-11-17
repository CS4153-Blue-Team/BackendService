package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.pojos.Hotel;
import com.blueteam.fbbutlerbackendservice.pojos.Restaurant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("restaurant")
public class RestaurantResource{
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;

    public RestaurantResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Restaurant entity) {
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
    public Response edit(@PathParam("id") Integer id, Restaurant entity) {
        em = emf.createEntityManager();
        String queryString = "from Hotel";
        
        em.getTransaction().begin();
        List<Hotel> hotelList = em.createQuery(queryString, Hotel.class).getResultList();
        Restaurant old = em.find(Restaurant.class, id);
        
        if (entity.getAdvertisingImage() != null)
        {
            old.setAdvertisingImage(entity.getAdvertisingImage());
        }
        if (entity.getButtonImage() != null)
        {
            old.setButtonImage(entity.getButtonImage());
        }
        if (entity.getDescription() != null)
        {
            old.setDescription(entity.getDescription());
        }
        if (entity.getHotel() != null || hotelList.contains(entity.getHotel()))
        {
            old.setHotel(entity.getHotel());
        }
        if (entity.getRestaurantName() != null || !entity.getRestaurantName().equals(""))
        {
            old.setRestaurantName(entity.getRestaurantName());
        }
        em.persist(old);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(old).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        Restaurant old;
        
        em.getTransaction().begin();
        old = em.find(Restaurant.class, id);
        em.remove(old);
        em.getTransaction().commit();
        em.close();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response find(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        Restaurant restaurant;
        
        em.getTransaction().begin();
        restaurant = em.find(Restaurant.class, id);
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(restaurant).build();
    }

    @GET
    @Produces("application/json")
    public Response findAll() {
        em = emf.createEntityManager();
        String queryString = "from Restaurant";
        
        em.getTransaction().begin();
        List<Restaurant> toReturn = em.createQuery(queryString, Restaurant.class).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("hotel/{id}")
    @Produces("application/json")
    public Response findAllForHotel(@PathParam("id") Integer id) {
        em = emf.createEntityManager();
        String queryString = "select * from Restaurants where hotel = ?1";
        
        em.getTransaction().begin();
        List<Restaurant> toReturn = em.createNativeQuery(queryString, Restaurant.class).setParameter(1, id).getResultList();
        em.getTransaction().commit();
        em.close();
        
        return Response.ok(toReturn).build();
    }

    @GET
    @Path("count")
    @Produces("application/json")
    public Response countREST() {
        em = emf.createEntityManager();
        String queryString = "from Restaurant";
        JSONObject toReturn = new JSONObject();
        try {
            em.getTransaction().begin();
            List<Restaurant> restuarantQuery = em.createQuery(queryString, Restaurant.class).getResultList();
            em.getTransaction().commit();
            em.close();
            
            toReturn.put("count", restuarantQuery.size());
        } catch (JSONException ex) {
            Logger.getLogger(RestaurantResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(toReturn).build();
    }
    
}
