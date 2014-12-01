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

    public CategoryResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Category entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        entity.setRestaurant((Restaurant) session.get(Restaurant.class, entity.getRestaurant().getId()));
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Category entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Category old = (Category) session.get(Category.class, id);
        if(entity.getName() != null && !entity.getName().equals(""))
        {
            old.setName(entity.getName());
        }
        if(entity.getRestaurant() != null)
        {
            old.setRestaurant(entity.getRestaurant());
        }
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Category old = (Category) session.get(Category.class, id);
        session.delete(old);
        session.getTransaction().commit();
        session.close();
        
    }

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

    @GET
    @Path("count")
    @Produces("application/json")
    public String countREST() {
        return null;
    }
}
