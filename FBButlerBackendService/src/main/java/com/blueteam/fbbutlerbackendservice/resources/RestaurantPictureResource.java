package com.blueteam.fbbutlerbackendservice.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */

@Path("RestaurantPicture")
public class RestaurantPictureResource {
    @PersistenceContext(unitName = "FBButlerBackendService")
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FBButlerBackendService");
    private EntityManager em;
    
}
