/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.PaymentSessionBeanLocal;
import entity.Milestone;
import entity.Payment;
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
import ws.restful.model.RetrievePaymentsRsp;

/**
 * REST Web Service
 *
 * @author sw_be
 */
@Path("Payment")
public class PaymentResource {

    @Context
    private UriInfo context;

    private PaymentSessionBeanLocal paymentSessionBean = lookupPaymentSessionBeanLocal();

    /**
     * Creates a new instance of PaymentResource
     */
    public PaymentResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.MilestoneResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrievePaymentsByProjectIdAndStudentId/{projectId}/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePaymentsByProjectIdAndStudentId(@PathParam("projectId") Long projectId, @PathParam("studentId") Long studentId) {
        try {
            List<Payment> payments = paymentSessionBean.retrievePaymentsByProjectIdAndStudentId(projectId, studentId);
            for (Payment payment : payments) {
                Milestone milestone = payment.getMilestone();
                milestone.getPayments().clear();
                milestone.setProject(null);
                payment.setStudent(null);
            }
            RetrievePaymentsRsp retrieveAllPaymentsRsp = new RetrievePaymentsRsp(payments);
            return Response.status(Response.Status.OK).entity(retrieveAllPaymentsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private PaymentSessionBeanLocal lookupPaymentSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PaymentSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/PaymentSessionBean!ejb.session.stateless.PaymentSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
