package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Hotel;
import com.blueteam.fbbutlerbackendservice.pojos.Restaurant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("restaurant")
public class RestaurantResource{
//    @PersistenceContext(unitName = "FBButlerBackendService")
//    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
    
    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private Session session = sf.openSession();

    /**
     *
     */
    public RestaurantResource() {
        
    }

    /**
     * This needs the JSON header
     * 
     * @param restaurant the parameters as JSON
     * @return the created Restaurant as JSON
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Restaurant restaurant) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        restaurant.setHotel((Hotel) session.get(Hotel.class, restaurant.getHotel().getId()));
        session.persist(restaurant);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(restaurant).build();
    }

    /**
     * This needs the JSON header
     * 
     * @param id the id of the Restaurant to edit
     * @param restaurant the new parameters of the Restaurant as JSON
     * @return the edited Restaurant as JSON
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Restaurant restaurant) {
//        em = emf.createEntityManager();
        String queryString = "from Hotel";
        
        session.getTransaction().begin();
        List<Hotel> hotelList = session.createQuery(queryString).list();
        Restaurant old = (Restaurant) session.get(Restaurant.class, id);
        
        if (restaurant.getAdvertisingImage() != null)
        {
            old.setAdvertisingImage(restaurant.getAdvertisingImage());
        }
        if (restaurant.getType() != null)
        {
            old.setType(restaurant.getType());
        }
        if (restaurant.getHours() != null)
        {
            old.setHours(restaurant.getHours());
        }
        if (restaurant.getHotel() != null && hotelList.contains(restaurant.getHotel()))
        {
            old.setHotel(restaurant.getHotel());
        }
        if (restaurant.getRestaurantName() != null && !restaurant.getRestaurantName().equals(""))
        {
            old.setRestaurantName(restaurant.getRestaurantName());
        }
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    /**
     *
     * @param id the id of the Restaurant to delete
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Restaurant old = (Restaurant) session.get(Restaurant.class, id);
        session.delete(old);
        session.getTransaction().commit();
        session.close();
    }

    /**
     *
     * @param id the id of the Restaurant to retrieve
     * @return the Restaurant as JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response find(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Restaurant restaurant = (Restaurant) session.get(Restaurant.class, id);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(restaurant).build();
    }

    /**
     *
     * @return all Restaurants as a JSON array of JSON
     */
    @GET
    @Produces("application/json")
    public Response findAll() {
//        em = emf.createEntityManager();
        String queryString = "from Restaurant";
        
        session.getTransaction().begin();
        List<Restaurant> toReturn = session.createQuery(queryString).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    /**
     *
     * @param id the id of the Hotel to get Restaurants for
     * @return the list of Restaurants for Hotel as JSON array
     */
    @GET
    @Path("hotel/{id}")
    @Produces("application/json")
    public Response findAllForHotel(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from Restaurant where hotel.id = :id";
        
        session.getTransaction().begin();
        List<Restaurant> toReturn = session.createQuery(queryString).setParameter("id", id).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }

    /**
     * UNUSED
     * 
     * @return
     */
    @GET
    @Path("count")
    @Produces("application/json")
    public Response countREST() {
//        em = emf.createEntityManager();
        String queryString = "from Restaurant";
        JSONObject toReturn = new JSONObject();
        try {
            session.getTransaction().begin();
            List<Restaurant> restuarantQuery = session.createQuery(queryString).list();
            session.getTransaction().commit();
            session.close();
            
            toReturn.put("count", restuarantQuery.size());
        } catch (JSONException ex) {
            Logger.getLogger(RestaurantResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(toReturn).build();
    }
    
}
