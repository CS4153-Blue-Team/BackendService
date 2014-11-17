package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Ian Stansell <ian.stansell@okstate.edu>
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.blueteam.fbbutlerbackendservice.resources.CategoryResource.class);
        resources.add(com.blueteam.fbbutlerbackendservice.resources.HotelResource.class);
        resources.add(com.blueteam.fbbutlerbackendservice.resources.IngredientResource.class);
        resources.add(com.blueteam.fbbutlerbackendservice.resources.MenuItemResource.class);
        resources.add(com.blueteam.fbbutlerbackendservice.resources.RestaurantPictureResource.class);
        resources.add(com.blueteam.fbbutlerbackendservice.resources.RestaurantResource.class);
        resources.add(com.test.TestResource.class);
    }
    
}
