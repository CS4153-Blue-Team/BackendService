package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Category;
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

@Path("menuItem")
public class MenuItemResource{
//    @PersistenceContext(unitName = "FBButlerBackendService")
//    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
    
    private SessionFactory sf = HibernateUtil.getSessionFactory();
    private Session session = sf.openSession();

    public MenuItemResource() {
        
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(MenuItem entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        entity.setCategory((Category) session.get(Category.class, entity.getCategory().getId()));
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(entity).build();
        
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, MenuItem entity) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        MenuItem old = (MenuItem) session.get(MenuItem.class, id);
        
        if (entity.getCategory() != null)
        {
            old.setCategory(entity.getCategory());
        }
        if (entity.getDescription() != null)
        {
            old.setDescription(entity.getDescription());
        }
        if (entity.getName() != null && !entity.getName().equals(""))
        {
            old.setName(entity.getName());
        }
        if (entity.getPictureFile() != null && !entity.getPictureFile().equals(""))
        {
            old.setPictureFile(entity.getPictureFile());
        }
        if (entity.getPrice() != null)
        {
            old.setPrice(entity.getPrice());
        }
        if (entity.getReviewImageLocation() != null && !entity.getReviewImageLocation().equals(""))
        {
            old.setReviewImageLocation(entity.getReviewImageLocation());
        }
        
        if (entity.getIngredients() != null)
        {
            old.setIngredient(entity.getIngredients());
        }
        
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }
    
    @PUT
    @Path("ingredient/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIngredient(@PathParam("id") Integer id, Ingredient ingredient) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        MenuItem menuItem = (MenuItem) session.get(MenuItem.class, id);
        Ingredient ingred = (Ingredient) session.get(Ingredient.class, ingredient.getId());
        menuItem.addIngredient(ingred);
        
        session.merge(menuItem);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(menuItem).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        MenuItem old = (MenuItem) session.get(MenuItem.class, id);
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
        MenuItem menuItem = (MenuItem) session.get(MenuItem.class, id);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(menuItem).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
//        em = emf.createEntityManager();
        String queryString = "from MenuItem";
        
        session.getTransaction().begin();
        List<MenuItem> toReturn = session.createQuery(queryString).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("category/{id}")
    @Produces("application/json")
    public Response findAllForCategory(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from MenuItem where category.id = :id";
        
        session.getTransaction().begin();
        List<MenuItem> toReturn = session.createQuery(queryString).setParameter("id", id).list();
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(toReturn).build();
    }
    
    @GET
    @Path("ingredient/{id}")
    @Produces("application/json")
    public Response findAllForIngredient(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        String queryString = "from MenuItem";
        
        session.getTransaction().begin();
        List<MenuItem> allMenuItems = session.createQuery(queryString).list();
        Ingredient ingredient = (Ingredient) session.get(Ingredient.class, id);
        session.getTransaction().commit();
        session.close();
        
        List<MenuItem> toReturn = new ArrayList<MenuItem>();
        for (MenuItem mi : allMenuItems)
        {
            if(mi.getIngredients().contains(ingredient))
            {
                toReturn.add(mi);
            }
        }
        
        return Response.ok(toReturn).build();
    }

    @GET
    @Path("count")
    @Produces("application/json")
    public String countREST() {
        return null;
    }
    
}
