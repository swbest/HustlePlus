/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Project;
import entity.Skill;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllProjectsRsp;
import ws.restful.model.RetrieveProjectRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("Project")
public class ProjectResource {

    @Context
    private UriInfo context;

    private ProjectSessionBeanLocal projectSessionBean = lookupProjectSessionBeanLocal();

    /**
     * Creates a new instance of ProjectResource
     */
    public ProjectResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveProject/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProjectById(@PathParam("projectId") Long projectId) {
        try {
            Project p = projectSessionBean.retrieveProjectByProjectId(projectId);
            p.getApplications().clear();
            p.setCompany(null);
            p.getCompanyReviews().clear();
            p.getMilestones().clear();
            p.getStudentReviews().clear();
            p.getStudents().clear();
            List<Skill> skills = p.getSkills();
            for (Skill skill : skills) {
                skill.getProjects().clear();
                skill.getStudents().clear();
            }
            RetrieveProjectRsp retrieveProjectRsp = new RetrieveProjectRsp(p);
            return Response.status(Status.OK).entity(retrieveProjectRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllProjects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProjects() {
        try {
            List<Project> projects = projectSessionBean.retrieveAllProjects();

            for (Project p : projects) {
                // clear bidirectional mappings or set the inverse side to null
                p.getApplications().clear();
                p.setCompany(null);
                p.getCompanyReviews().clear();
                p.getMilestones().clear();
                p.getSkills().clear();
                p.getStudentReviews().clear();
                p.getStudents().clear();
            }
            RetrieveAllProjectsRsp retrieveAllProjectsRsp = new RetrieveAllProjectsRsp(projects);
            return Response.status(Status.OK).entity(retrieveAllProjectsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/retrieveProjectsByStudentId/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProjectsByStudentId(@PathParam("studentId") Long studentId) {
        try {
            List<Project> projects = projectSessionBean.retrieveProjectsByStudentId(studentId);
            for (Project p : projects) {
                // clear bidirectional mappings or set the inverse side to null
                // skills - not bidirectional
                // milestones not needed
                p.getApplications().clear();
                p.setCompany(null);
                p.getCompanyReviews().clear();
                p.getMilestones().clear();
                p.getSkills().clear();
                p.getStudentReviews().clear();
                p.getStudents().clear();
            }
            RetrieveAllProjectsRsp retrieveAllProjectsRsp = new RetrieveAllProjectsRsp(projects);
            return Response.status(Status.OK).entity(retrieveAllProjectsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/retrieveProjectsByCompanyId/{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProjectsByCompanyId(@PathParam("companyId") Long companyId) {
        try {
            List<Project> projects = projectSessionBean.retrieveProjectsByCompany(companyId);
            for (Project p : projects) {
                // clear bidirectional mappings or set the inverse side to null
                // skills - not bidirectional
                // milestones not needed
                p.getApplications().clear();
                p.setCompany(null);
                p.getCompanyReviews().clear();
                p.getMilestones().clear();
                p.getSkills().clear();
                p.getStudentReviews().clear();
                p.getStudents().clear();
            }
            RetrieveAllProjectsRsp retrieveAllProjectsRsp = new RetrieveAllProjectsRsp(projects);
            return Response.status(Status.OK).entity(retrieveAllProjectsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    private ProjectSessionBeanLocal lookupProjectSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProjectSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/ProjectSessionBean!ejb.session.stateless.ProjectSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
