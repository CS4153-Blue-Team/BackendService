package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Hotel;
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

@Path("hotel")
public class HotelResource {
//    @PersistenceContext(unitName = "FBButlerBackendService")
    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
    
    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private Session session = sf.openSession();
    
    /**
     *
     */
    public HotelResource() {
        
    }

    /**
     * This needs the JSON header
     * 
     * @param hotel the parameters as JSON
     * @return the created Hotel as JSON
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Hotel hotel) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        session.persist(hotel);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(hotel, MediaType.APPLICATION_JSON).build();
    }

    /**
     * This needs the JSON header
     * 
     * @param id of the Hotel to edit
     * @param hotel the new parameters of the Hotel as JSON
     * @return the edited Hotel as JSON
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Hotel hotel) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Hotel old = (Hotel) session.get(Hotel.class, id);
        if (hotel.getName() != null && !hotel.getName().equals(""))
        {
            old.setName(hotel.getName());
        }
        if (hotel.getPictureLocation() != null)
        {
            old.setPictureLocation(hotel.getPictureLocation());
        }
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    /**
     *
     * @param id of Hotel to delete
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Hotel old = (Hotel) session.get(Hotel.class, id);
        session.delete(old);
        session.getTransaction().commit();
        session.close();  
        
        return Response.ok().build();
    }

    /**
     *
     * @param id of Hotel to retrieve
     * @return the Hotel as JSON
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Hotel hotel = (Hotel) session.get(Hotel.class, id);
        session.getTransaction().commit();
        session.close(); 
        
        return Response.ok(hotel).build();
    }

    /**
     *
     * @return all Hotels as a JSON array of JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
//        em = emf.createEntityManager();
        String queryString = "from Hotel";
        
        
        session.getTransaction().begin();
        List<Hotel> toReturn = session.createQuery(queryString).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    /**
     * UNUSED
     * @return
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countREST() {
//        em = emf.createEntityManager();
        String queryString = "from Hotel";
        JSONObject toReturn = new JSONObject();
        
        try {
            session.getTransaction().begin();
            List<Hotel> hotelQuery = session.createQuery(queryString).list();
            session.getTransaction().commit();
            session.close();
            toReturn.put("count", hotelQuery.size());
        } 
        catch (JSONException ex) {
            Logger.getLogger(HotelResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(toReturn).build();
    }
    
}
