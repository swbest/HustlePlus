/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.StudentReviewSessionBeanLocal;
import entity.StudentReview;
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
import ws.restful.model.CreateNewReviewRsp;
import ws.restful.model.CreateNewStudentReviewReq;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllStudentReviewsRsp;
import ws.restful.model.RetrieveStudentReviewRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("StudentReview")
public class StudentReviewResource {

    @Context
    private UriInfo context;

    private StudentReviewSessionBeanLocal studentReviewSessionBeanLocal = lookupReviewSessionBeanLocal();

    /**
     * Creates a new instance of ReviewResource
     */
    public StudentReviewResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ReviewResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveStudentReview/{studentReviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentReviewById(@PathParam("studentReviewId") Long studentReviewId) {
        try {
            StudentReview studentReview = studentReviewSessionBeanLocal.retrieveStudentReviewByReviewId(studentReviewId);
            RetrieveStudentReviewRsp retrieveStudentReviewRsp = new RetrieveStudentReviewRsp(studentReview);
            return Response.status(Response.Status.OK).entity(retrieveStudentReviewRsp).build();
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
    @Path("retrieveAllStudentReviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviews() {
        try {
            List<StudentReview> studentReviews = studentReviewSessionBeanLocal.retrieveAllStudentReviews();
            RetrieveAllStudentReviewsRsp retrieveAllStudentReviewsRsp = new RetrieveAllStudentReviewsRsp(studentReviews);
            return Response.status(Response.Status.OK).entity(retrieveAllStudentReviewsRsp).build();
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
    public Response createStudentReview(CreateNewStudentReviewReq createNewStudentReviewReq) {
        System.out.println("Creating new student review review text: " + createNewStudentReviewReq.getNewStudentReview().getReviewText() + " of rating: " + createNewStudentReviewReq.getNewStudentReview().getRating() + "with project id:" + createNewStudentReviewReq.getProjectId() + " student id: " + createNewStudentReviewReq.getStudentId());
        if (createNewStudentReviewReq != null) {
            try {
                Long newReviewId = studentReviewSessionBeanLocal.createStudentReviewByStudent(createNewStudentReviewReq.getNewStudentReview(), createNewStudentReviewReq.getProjectId(), createNewStudentReviewReq.getStudentId());
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

    private StudentReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StudentReviewSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/StudentReviewSessionBean!ejb.session.stateless.StudentReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
