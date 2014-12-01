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

    /**
     *
     */
    public MenuItemResource() {
        
    }

    /**
     * This needs the JSON header
     *
     * @param menuItem the parameters of the new MenuItem as JSON
     * @return the created MenuItem as JSON
     */
    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(MenuItem menuItem) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        menuItem.setCategory((Category) session.get(Category.class, menuItem.getCategory().getId()));
        session.persist(menuItem);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(menuItem).build();
        
    }

    /**
     * This needs the JSON header
     *
     * @param id the id of the MenuItem to edit
     * @param menuItem the new parameters as JSON
     * @return the edited MenuItem as JSON
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, MenuItem menuItem) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        MenuItem old = (MenuItem) session.get(MenuItem.class, id);
        
        if (menuItem.getCategory() != null)
        {
            old.setCategory(menuItem.getCategory());
        }
        if (menuItem.getDescription() != null)
        {
            old.setDescription(menuItem.getDescription());
        }
        if (menuItem.getName() != null && !menuItem.getName().equals(""))
        {
            old.setName(menuItem.getName());
        }
        if (menuItem.getPictureFile() != null && !menuItem.getPictureFile().equals(""))
        {
            old.setPictureFile(menuItem.getPictureFile());
        }
        if (menuItem.getPrice() != null)
        {
            old.setPrice(menuItem.getPrice());
        }
        if (menuItem.getReviewImageLocation() != null && !menuItem.getReviewImageLocation().equals(""))
        {
            old.setReviewImageLocation(menuItem.getReviewImageLocation());
        }
        
        if (menuItem.getIngredients() != null)
        {
            old.setIngredient(menuItem.getIngredients());
        }
        
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }
    
    /**
     * This needs the JSON header
     *
     * @param id the id of the MenuItem to add an Ingredient to
     * @param ingredient the Ingredient to add as JSON
     * @return the changed MenuItem as JSON
     */
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

    /**
     *
     * @param id the id of the MenuItem to delete
     */
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

    /**
     *
     * @param id the id of the MenuItem to retrieve
     * @return the retrieved MenuItem as JSON
     */
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

    /**
     *
     * @return all MenuItems as a JSON array of JSOn
     */
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
    
    /**
     *
     * @param id the id of the Category to get MenuItems for
     * @return the MenuItems for the Category as a JSON array of JSON
     */
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
    
    /**
     *
     * @param id the id of the Ingredient to get MenuItems for
     * @return the MenuItems for the Ingredient as a JSON array of JSON
     */
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
