/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.SkillSessionBeanLocal;
import entity.Skill;
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
import util.exception.DeleteSkillException;
import util.exception.SkillNotFoundException;
import ws.restful.model.CreateNewSkillReq;
import ws.restful.model.CreateNewSkillRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllSkillsRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("Skill")
public class SkillResource {

    SkillSessionBeanLocal skillSessionBean = lookupSkillSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SkillResource
     */
    public SkillResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.SkillResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllSkills")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSkills() {
        try {
            List<Skill> skills = skillSessionBean.retrieveAllSkills();
            for (Skill s : skills) {
                s.getProjects().clear();
                s.getStudents().clear();
            }
            RetrieveAllSkillsRsp retrieveAllSkillsRsp = new RetrieveAllSkillsRsp(skills);
            return Response.status(Response.Status.OK).entity(retrieveAllSkillsRsp).build();
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
    @Path("/retrieveSkillsByStudentId/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSkillsByStudentId(@PathParam("studentId") Long studentId) {
        try {
            List<Skill> skills = skillSessionBean.retrieveSkillsByStudentId(studentId);
            for (Skill s : skills) {
                s.getProjects().clear();
                s.getStudents().clear();
            }
            RetrieveAllSkillsRsp retrieveAllSkillsRsp = new RetrieveAllSkillsRsp(skills);
            return Response.status(Response.Status.OK).entity(retrieveAllSkillsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of SkillResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSkill(CreateNewSkillReq createNewSkillReq) {
        if (createNewSkillReq != null) {
            try {
                Long newSkillId = skillSessionBean.studentAddSkill(createNewSkillReq.getNewSkill(), createNewSkillReq.getStudentId());
                CreateNewSkillRsp createNewSkillRsp = new CreateNewSkillRsp(newSkillId);
                return Response.status(Status.OK).entity(createNewSkillRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }

    }
    
    @Path("/deleteSkill/{skillId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSkill(@PathParam("skillId") Long skillId) {
        try {
            System.out.println("********** SkillResource.deleteSkill(): Skill " + skillId + " remotely via web service");

            skillSessionBean.deleteSkill(skillId);

            return Response.status(Status.OK).build();
        } catch (SkillNotFoundException | DeleteSkillException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private SkillSessionBeanLocal lookupSkillSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SkillSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/SkillSessionBean!ejb.session.stateless.SkillSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
