/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author dtjldamien
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        resources.add(MultiPartFeature.class);

        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.resources.ApplicationResource.class);
        resources.add(ws.restful.resources.CompanyResource.class);
        resources.add(ws.restful.resources.CompanyReviewResource.class);
        resources.add(ws.restful.resources.CorsFilter.class);
        resources.add(ws.restful.resources.FileResource.class);
        resources.add(ws.restful.resources.MilestoneResource.class);
        resources.add(ws.restful.resources.PaymentResource.class);
        resources.add(ws.restful.resources.ProjectResource.class);
        resources.add(ws.restful.resources.SkillResource.class);
        resources.add(ws.restful.resources.StudentResource.class);
        resources.add(ws.restful.resources.StudentReviewResource.class);
        resources.add(ws.restful.resources.TeamResource.class);
    }
}
