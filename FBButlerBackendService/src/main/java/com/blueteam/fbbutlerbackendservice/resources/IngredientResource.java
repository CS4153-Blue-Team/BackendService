package com.blueteam.fbbutlerbackendservice.resources;

import com.blueteam.fbbutlerbackendservice.HibernateUtil;
import com.blueteam.fbbutlerbackendservice.pojos.Ingredient;
import com.blueteam.fbbutlerbackendservice.pojos.MenuItem;
import com.blueteam.fbbutlerbackendservice.pojos.Restaurant;
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

    /**
     *
     */
    public IngredientResource() {
        
    }

    /**
     * This needs the JSON header
     *
     * @param ingredient the parameters as JSON
     * @return the created Ingredient as JSON
     */
    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Ingredient ingredient) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        ingredient.setRestaurant((Restaurant) session.get(Restaurant.class, ingredient.getRestaurant().getId()));
        session.persist(ingredient);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(ingredient).build();
    }

    /**
     * This needs the JSON header
     *
     * @param id the id of the Ingredient to edit
     * @param ingredient the new parameters as JSON
     * @return the edited Ingredient as JSON
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Integer id, Ingredient ingredient) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient old = (Ingredient) session.get(Ingredient.class, id);
        
        if (ingredient.getName() != null && !ingredient.getName().equals(""))
        {
            old.setName(ingredient.getName());
        }
        if (ingredient.getRestaurant() != null)
        {
            old.setRestaurant(ingredient.getRestaurant());
        }
        if (ingredient.getInStock() != null)
        {
            old.setInStock(ingredient.getInStock());
        }
        
        session.merge(old);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok(old).build();
    }

    /**
     * This needs the JSON header
     *
     * @param id the id of the Ingredient to add MenuItems to
     * @param menuItem the MenuItem to add as JSON
     * @return the edited Ingredient as JSON
     */
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
    
    /**
     *
     * @param id the id of the Ingredient to delete
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
        
        session.getTransaction().begin();
        Ingredient ingredient = (Ingredient) session.get(Ingredient.class, id);
        session.delete(ingredient);
        session.getTransaction().commit();
        session.close();
        
        return Response.ok().build();
    }

    /**
     *
     * @param id the id of the Ingredient to retrieve
     * @return the retrieved Ingredient as JSON
     */
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

    /**
     *
     * @return all Ingredients as a JSON array of JSON
     */
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
    
    /**
     *
     * @param id the id of the Restaurant to get Ingredients for
     * @return the Ingredients for the Restaurant as a JSON array of JSON
     */
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
    
    /**
     *
     * @param id the id of the MenuItem to get Ingredients for
     * @return the Ingredients for the MenuItem as a JSON array of JSON
     */
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
    
    /**
     * UNUSED
     * 
     * @return
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        return null;
    }
    
}
