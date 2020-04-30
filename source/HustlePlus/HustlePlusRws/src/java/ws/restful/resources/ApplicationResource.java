/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import entity.Application;
import entity.Project;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.ApplicationNotFoundException;
import util.exception.DeleteApplicationException;
import ws.restful.model.CreateNewApplicationReq;
import ws.restful.model.CreateNewApplicationRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllApplicationsRsp;
import ws.restful.model.RetrieveApplicationRsp;

/**
 * REST Web Service
 *
 * @author sw_be
 */
@Path("Application")
public class ApplicationResource {

    @Context
    private UriInfo context;

    private ApplicationSessionBeanLocal applicationSessionBean = lookupApplicationSessionBeanLocal();

    /**
     * Creates a new instance of ApplicationResource
     */
    public ApplicationResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ApplicationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveApplication/{applicationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveApplicationById(@PathParam("applicationId") Long applicationId) {
        try {
            Application application = applicationSessionBean.retrieveApplicationByApplicationId(applicationId);
            application.getProject().getStudents().clear();
            application.getProject().getApplications().clear();
            application.getStudent().getApplications().clear();
            application.getStudent().getProjects().clear();
            RetrieveApplicationRsp retrieveApplicationRsp = new RetrieveApplicationRsp(application);
            return Response.status(Response.Status.OK).entity(retrieveApplicationRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ApplicationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllApplications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllApplications() {
        try {
            List<Application> applications = applicationSessionBean.retrieveAllApplications();
            for (Application application : applications) {

                application.setProject(null);
                application.setStudent(null);

//                Project project = application.getProject();
//                List<Skill> skills = project.getSkills();
//                for (Skill s : skills) {
//                    s.getProjects().clear();
//                }
//                project.getMilestones().clear();
//                project.getCompany().getProjects().clear();
//                project.getCompanyReviews().clear();
//                project.getStudentReviews().clear();
//                project.getApplications().clear();
//                List<Student> students = project.getStudents();
//                for (Student s : students) {
//                    s.getProjects().clear();
//                    s.getProjects().clear();
//                }
            }
            RetrieveAllApplicationsRsp retrieveAllApplicationsRsp = new RetrieveAllApplicationsRsp(applications);
            return Response.status(Response.Status.OK).entity(retrieveAllApplicationsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ApplicationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveApplicationsByStudentId/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveApplicationsByStudentId(@PathParam("studentId") Long studentId) {
        try {
            System.out.println("Retrieving applications for student id: " + studentId);
            List<Application> applications = applicationSessionBean.retrieveApplicationByStudent(studentId);
            for (Application application : applications) {
                application.setStudent(null);
                Project project = application.getProject();
                project.getApplications().clear();
                project.setCompany(null);
                project.getCompanyReviews().clear();
                project.getMilestones().clear();
                project.getSkills().clear();
                project.getStudentReviews().clear();
                project.getStudents().clear();
            }
            RetrieveAllApplicationsRsp retrieveAllApplicationsRsp = new RetrieveAllApplicationsRsp(applications);
            return Response.status(Response.Status.OK).entity(retrieveAllApplicationsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of ApplicationResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createApplication(CreateNewApplicationReq createNewApplicationReq) {
        System.out.println("Creating new application for student: " + createNewApplicationReq.getStudentId() + " project: " + createNewApplicationReq.getProjectId());
        if (createNewApplicationReq != null) {
            try {
                Long newApplicationId = applicationSessionBean.createApplication(createNewApplicationReq.getNewApplication(), createNewApplicationReq.getProjectId(), createNewApplicationReq.getStudentId());
                CreateNewApplicationRsp createNewApplicationRsp = new CreateNewApplicationRsp(newApplicationId);
                return Response.status(Response.Status.OK).entity(createNewApplicationRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                System.out.println(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private ApplicationSessionBeanLocal lookupApplicationSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ApplicationSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/ApplicationSessionBean!ejb.session.stateless.ApplicationSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    @Path("/deleteApplication/{applicationId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteApplication(@PathParam("applicationId") Long applicationId) {
        try {
            System.out.println("Deleteing application Id: " + applicationId);
            applicationSessionBean.deleteApplication(applicationId);
            return Response.status(Status.OK).build();
        } catch (ApplicationNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (DeleteApplicationException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            Logger.getLogger(ApplicationResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
    }
}
