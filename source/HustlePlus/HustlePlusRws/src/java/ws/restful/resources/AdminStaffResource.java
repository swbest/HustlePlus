/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.AdminStaffSessionBeanLocal;
import entity.AdminStaff;
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
import javax.ws.rs.core.Response.Status;
import ws.restful.model.CreateNewAdminStaffReq;
import ws.restful.model.CreateNewAdminStaffRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllAdminStaffRsp;
import ws.restful.model.RetrieveAdminStaffRsp;

/**
 * REST Web Service
 *
 * @author sw_be
 */
@Path("AdminStaff")
public class AdminStaffResource {

    @Context
    private UriInfo context;

    private AdminStaffSessionBeanLocal adminStaffSessionBean = lookupAdminStaffSessionBeanLocal();

    /**
     * Creates a new instance of AdminStaffResource
     */
    public AdminStaffResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.AdminStaffResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAdminStaff/{adminStaffId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAdminStaffById(@PathParam("adminStaffId") Long adminStaffId) {
        try {
            AdminStaff adminStaff = adminStaffSessionBean.retrieveAdminStaffByAdminStaffId(adminStaffId);
            RetrieveAdminStaffRsp retrieveAdminStaffRsp = new RetrieveAdminStaffRsp(adminStaff);
            return Response.status(Status.OK).entity(retrieveAdminStaffRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.AdminStaffResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllAdminStaffs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAdminStaffs() {
        try {
            List<AdminStaff> adminStaffs = adminStaffSessionBean.retrieveAllAdminStaff();
            RetrieveAllAdminStaffRsp retrieveAllAdminStaffsRsp = new RetrieveAllAdminStaffRsp(adminStaffs);
            return Response.status(Status.OK).entity(retrieveAllAdminStaffsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of AdminStaffResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAdminStaff(CreateNewAdminStaffReq createNewAdminStaffReq) {
        if (createNewAdminStaffReq != null) {
            try {
                Long newAdminStaffId = adminStaffSessionBean.createNewAdminStaff(createNewAdminStaffReq.getNewAdminStaff());
                CreateNewAdminStaffRsp createNewAdminStaffRsp = new CreateNewAdminStaffRsp(newAdminStaffId);
                return Response.status(Status.OK).entity(createNewAdminStaffRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private AdminStaffSessionBeanLocal lookupAdminStaffSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdminStaffSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/AdminStaffSessionBean!ejb.session.stateless.AdminStaffSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
