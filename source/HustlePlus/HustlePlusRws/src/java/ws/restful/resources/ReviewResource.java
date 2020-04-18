/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Review;
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
import ws.restful.model.CreateNewReviewReq;
import ws.restful.model.CreateNewReviewRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllReviewsRsp;
import ws.restful.model.RetrieveReviewRsp;

/**
 * REST Web Service
 *
 * @author sw_be
 */
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;

    private ReviewSessionBeanLocal reviewSessionBean = lookupReviewSessionBeanLocal();

    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ReviewResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveReview/{reviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveReviewById(@PathParam("id") Long reviewId) {
        try {
            Review review = reviewSessionBean.retrieveReviewByReviewId(reviewId);
            RetrieveReviewRsp retrieveReviewRsp = new RetrieveReviewRsp(review);
            return Response.status(Response.Status.OK).entity(retrieveReviewRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ReviewResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllReviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviews() {
        try {
            List<Review> reviews = reviewSessionBean.retrieveAllReviews();
            RetrieveAllReviewsRsp retrieveAllReviewsRsp = new RetrieveAllReviewsRsp(reviews);
            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of ReviewResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(CreateNewReviewReq createNewReviewReq) {
        if (createNewReviewReq != null) {
            try {
                Long newReviewId = reviewSessionBean.createNewReview(createNewReviewReq.getNewReview(), createNewReviewReq.getProjectId(), createNewReviewReq.getStudentId(), createNewReviewReq.getCompanyId());
                CreateNewReviewRsp createNewReviewRsp = new CreateNewReviewRsp(newReviewId);
                return Response.status(Response.Status.OK).entity(createNewReviewRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private ReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/ReviewSessionBean!ejb.session.stateless.ReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
