package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Category;
import com.blueteam.fbbutlerbackendservice.pojos.Restaurant;
import java.util.List;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("category")
public class CategoryResource{
//    @PersistenceContext(unitName = "FBButlerBackendService")
//    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
    
    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private Session session = sf.openSession();

    /**
     *
     */
    public CategoryResource() {
        
    }

    /**
     * This needs the JSON header
     *
     * @param category the parameters as JSON
     * @return the created Category as JSON
     */
    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Category category) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        category.setRestaurant((Restaurant) session.get(Restaurant.class, category.getRestaurant().getId()));
        session.persist(category);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(category).build();
    }

    /**
     * This needs the JSON header
     *
     * @param id the id of the Category to update
     * @param category the new parameters of the Category as JSOn
     * @return the edited Category as JSOn
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Category category) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Category old = (Category) session.get(Category.class, id);
        if(category.getName() != null && !category.getName().equals(""))
        {
            old.setName(category.getName());
        }
        if(category.getRestaurant() != null)
        {
            old.setRestaurant(category.getRestaurant());
        }
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    /**
     *
     * @param id the id of the Category to delete
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Category old = (Category) session.get(Category.class, id);
        session.delete(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok().build();
    }

    /**
     *
     * @param id the id of the Category to retrieve
     * @return the Category as JSON
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Category category = (Category) session.get(Category.class, id);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(category).build();
    }

    /**
     *
     * @return all Categories as a JSON array of JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
//        em = emf.createEntityManager();
        String queryString = "from Category";
        
        session.getTransaction().begin();
        List<Category> toReturn = session.createQuery(queryString).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    /**
     *
     * @param id the id of the Restaurant to find Categories for
     * @return the Categories for the Restaurant as a JSON array of JSON
     */
    @GET
    @Path("restaurant/{id}")
    @Produces("application/json")
    public Response findAllForRestaurant(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from Category where restaurant.id = :id";
        
        session.getTransaction().begin();
        List<Category> toReturn = session.createQuery(queryString).setParameter("id", id).list();
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
    public String countREST() {
        return null;
    }
}
