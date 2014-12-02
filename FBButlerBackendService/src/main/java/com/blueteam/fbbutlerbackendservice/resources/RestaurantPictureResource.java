//package com.blueteam.fbbutlerbackendservice.resources;
//
//import com.blueteam.fbbutlerbackendservice.HibernateUtil;
//import com.blueteam.fbbutlerbackendservice.pojos.RestaurantPicture;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//
///**
// * @author Ian Stansell <ian.stansell@okstate.edu>
// */
//
//@Path("restaurantPicture")
//public class RestaurantPictureResource {
//    @PersistenceContext(unitName = "FBButlerBackendService")
//    
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
//    private EntityManager em;
//    
//    private SessionFactory sf = HibernateUtil.getSessionFactory();
//    private Session session = sf.openSession();
//    
//    @POST
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response create(RestaurantPicture entity) {
//        em = emf.createEntityManager();
//        
//        em.getTransaction().begin();
//        em.persist(entity);
//        em.getTransaction().commit();
//        em.close();
//        
//        return Response.ok(entity).build();
//    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response edit(@PathParam("id") Integer id, RestaurantPicture entity) {
//        em = emf.createEntityManager();
//        
//        em.getTransaction().begin();
//        RestaurantPicture old = em.find(RestaurantPicture.class, id);
//        
//        if (entity.getPictureLocation() != null && !entity.getPictureLocation().equals(""))
//        {
//            old.setPictureLocation(entity.getPictureLocation());
//        }
//        if (entity.getRestaurant() != null)
//        {
//            old.setRestaurant(entity.getRestaurant());
//        }
//        em.merge(old);
//        em.getTransaction().commit();
//        em.close();
//        
//        return Response.ok(old).build();
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
//        
//        em.getTransaction().begin();
//        RestaurantPicture ingredient = em.find(RestaurantPicture.class, id);
//        em.remove(ingredient);
//        em.getTransaction().commit();
//        em.close();
//        
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response find(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
//        
//        em.getTransaction().begin();
//        RestaurantPicture picture = em.find(RestaurantPicture.class, id);
//        em.getTransaction().commit();
//        em.close();
//        
//        return Response.ok(picture).build();
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findAll() {
//        em = emf.createEntityManager();
//        String queryString = "from RestaurantPicture";
//        
//        em.getTransaction().begin();
//        List<RestaurantPicture> toReturn = em.createQuery(queryString, RestaurantPicture.class).getResultList();
//        em.getTransaction().commit();
//        em.close();
//        
//        return Response.ok(toReturn).build();
//    }
//    
//    @GET
//    @Path("restaurant/{id}")
//    @Produces("application/json")
//    public Response findAllForRestaurant(@PathParam("id") Integer id) {
//        em = emf.createEntityManager();
//        String queryString = "select * from RestaurantPictures where restaurant = ?1";
//        
//        em.getTransaction().begin();
//        List<RestaurantPicture> toReturn = em.createNativeQuery(queryString, RestaurantPicture.class).setParameter(1, id).getResultList();
//        em.getTransaction().commit();
//        em.close();
//        
//        return Response.ok(toReturn).build();
//    }
//
//    @GET
//    @Path("count")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String countREST() {
//        return null;
//    }
//}
