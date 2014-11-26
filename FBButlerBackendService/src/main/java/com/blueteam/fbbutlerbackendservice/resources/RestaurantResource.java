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

    public RestaurantResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Restaurant entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Restaurant entity) {
//        em = emf.createEntityManager();
        String queryString = "from Hotel";
        
        session.getTransaction().begin();
        List<Hotel> hotelList = session.createQuery(queryString).list();
        Restaurant old = (Restaurant) session.get(Restaurant.class, id);
        
        if (entity.getAdvertisingImage() != null)
        {
            old.setAdvertisingImage(entity.getAdvertisingImage());
        }
        if (entity.getType() != null)
        {
            old.setType(entity.getType());
        }
        if (entity.getHours() != null)
        {
            old.setHours(entity.getHours());
        }
        if (entity.getHotel() != null && hotelList.contains(entity.getHotel()))
        {
            old.setHotel(entity.getHotel());
        }
        if (entity.getRestaurantName() != null && !entity.getRestaurantName().equals(""))
        {
            old.setRestaurantName(entity.getRestaurantName());
        }
        session.saveOrUpdate(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

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
