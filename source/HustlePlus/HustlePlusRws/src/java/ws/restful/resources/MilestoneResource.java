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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveMilestonesRsp;

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
    @Path("retrieveMilestonesByProjectId/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMilestonesByProjectId(@PathParam("projectId") Long projectId) {
        try {
            List<Milestone> milestones = milestoneSessionBean.retrieveMilestonesByProject(projectId);
            for (Milestone milestone : milestones) {
                milestone.getPayments().clear();
                milestone.setProject(null);
            }
            RetrieveMilestonesRsp retrieveMilestonesRsp = new RetrieveMilestonesRsp(milestones);
            return Response.status(Response.Status.OK).entity(retrieveMilestonesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
