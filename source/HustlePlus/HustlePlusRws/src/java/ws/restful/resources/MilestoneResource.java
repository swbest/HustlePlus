/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import entity.Milestone;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.CreateNewMilestoneReq;
import ws.restful.model.CreateNewMilestoneRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllMilestonesRsp;
import ws.restful.model.RetrieveMilestoneRsp;

/**
 * REST Web Service
 *
 * @author sw_be
 */
@Path("Milestone")
public class MilestoneResource {

    @Context
    private UriInfo context;

    private MilestoneSessionBeanLocal milestoneSessionBean = lookupMilestoneSessionBeanLocal();

    /**
     * Creates a new instance of MilestoneResource
     */
    public MilestoneResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.MilestoneResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMilestoneById(@PathParam("id") Long milestoneId) {
        try {
            Milestone milestone = milestoneSessionBean.retrieveMilestoneByMilestoneId(milestoneId);
            RetrieveMilestoneRsp retrieveMilestoneRsp = new RetrieveMilestoneRsp(milestone);
            return Response.status(Response.Status.OK).entity(retrieveMilestoneRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.MilestoneResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllMilestones() {
        try {
            List<Milestone> milestones = milestoneSessionBean.retrieveAllMilestones();
            RetrieveAllMilestonesRsp retrieveAllMilestonesRsp = new RetrieveAllMilestonesRsp(milestones);
            return Response.status(Response.Status.OK).entity(retrieveAllMilestonesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of MilestoneResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMilestone(CreateNewMilestoneReq createNewMilestoneReq) {
        if (createNewMilestoneReq != null) {
            try {
                Long newMilestoneId = milestoneSessionBean.createNewMilestone(createNewMilestoneReq.getNewMilestone(), createNewMilestoneReq.getProjectId());
                CreateNewMilestoneRsp createNewMilestoneRsp = new CreateNewMilestoneRsp(newMilestoneId);
                return Response.status(Response.Status.OK).entity(createNewMilestoneRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private MilestoneSessionBeanLocal lookupMilestoneSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MilestoneSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/MilestoneSessionBean!ejb.session.stateless.MilestoneSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
