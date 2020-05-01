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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.CreateNewPaymentReq;
import ws.restful.model.CreateNewPaymentRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllPaymentsRsp;
import ws.restful.model.RetrievePaymentRsp;

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
            RetrieveAllPaymentsRsp retrieveAllPaymentsRsp = new RetrieveAllPaymentsRsp(payments);
            return Response.status(Response.Status.OK).entity(retrieveAllPaymentsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.PaymentResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrievePayment/{paymentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePaymentById(@PathParam("paymentId") Long paymentId) {
        try {
            Payment payment = paymentSessionBean.retrievePaymentByPaymentId(paymentId);
            Milestone milestone = payment.getMilestone();
            milestone.getPayments().clear();
            milestone.setProject(null);
            payment.setStudent(null);
            RetrievePaymentRsp retrievePaymentRsp = new RetrievePaymentRsp(payment);
            return Response.status(Response.Status.OK).entity(retrievePaymentRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.PaymentResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPayments() {
        try {
            List<Payment> payments = paymentSessionBean.retrieveAllPayment();
            for (Payment payment : payments) {
                Milestone milestone = payment.getMilestone();
                milestone.getPayments().clear();
                milestone.setProject(null);
                payment.setStudent(null);
            }
            RetrieveAllPaymentsRsp retrieveAllPaymentsRsp = new RetrieveAllPaymentsRsp(payments);
            return Response.status(Response.Status.OK).entity(retrieveAllPaymentsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of PaymentResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(CreateNewPaymentReq createNewPaymentReq) {
        if (createNewPaymentReq != null) {
            try {
                Long newPaymentId = paymentSessionBean.createNewPayment(createNewPaymentReq.getNewPayment(), createNewPaymentReq.getMilestoneId(), createNewPaymentReq.getStudentId());
                CreateNewPaymentRsp createNewPaymentRsp = new CreateNewPaymentRsp(newPaymentId);
                return Response.status(Response.Status.OK).entity(createNewPaymentRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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
