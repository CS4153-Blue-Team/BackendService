package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Ingredient;
import com.blueteam.fbbutlerbackendservice.pojos.MenuItem;
import java.util.ArrayList;
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

@Path("ingredient")
public class IngredientResource{
//    @PersistenceContext(unitName = "FBButlerBackendService")
//    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
    
    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private Session session = sf.openSession();

    public IngredientResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Ingredient entity) {
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
    public Response edit(@PathParam("id") Integer id, Ingredient entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient old = (Ingredient) session.get(Ingredient.class, id);
        
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
        
        session.saveOrUpdate(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    @PUT
    @Path("menuItem/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIngredient(@PathParam("id") Integer id, MenuItem menuItem) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient ingredient = (Ingredient) session.get(Ingredient.class, id);
        
        
        ingredient.addMenuItem(menuItem);
        
        session.merge(ingredient);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(ingredient).build();
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient ingredient = (Ingredient) session.get(Ingredient.class, id);
        session.delete(ingredient);
        session.getTransaction().commit();
        session.close();
        
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient ingredient = (Ingredient) session.get(Ingredient.class, id);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(ingredient).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
//        em = emf.createEntityManager();
        String queryString = "from Ingredient";
        
        session.getTransaction().begin();
        List<Ingredient> toReturn = session.createQuery(queryString).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("restaurant/{id}")
    @Produces("application/json")
    public Response findAllForRestaurant(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from Ingredient where restaurant.id = :id";
        
        session.getTransaction().begin();
        List<Ingredient> toReturn = session.createQuery(queryString).setParameter("id", id).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("menuItem/{id}")
    @Produces("application/json")
    public Response findAllForMenuItem(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from Ingredient";
        
        session.getTransaction().begin();
        List<Ingredient> allIngredients = session.createQuery(queryString).list();
        MenuItem mi = (MenuItem) session.get(MenuItem.class, id);
        session.getTransaction().commit();
        session.close();
        
        List<Ingredient> toReturn = new ArrayList<Ingredient>();
        for (Ingredient ingred : allIngredients)
        {
            if(ingred.getMenuItems().contains(mi))
            {
                toReturn.add(ingred);
            }
        }
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return null;
    }
    
}
