/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import ejb.session.stateless.StudentReviewSessionBeanLocal;
import entity.CompanyReview;
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
import ws.restful.model.CreateNewCompanyReviewReq;
import ws.restful.model.CreateNewReviewRsp;
import ws.restful.model.CreateNewStudentReviewReq;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllCompanyReviewsRsp;
import ws.restful.model.RetrieveAllStudentReviewsRsp;
import ws.restful.model.RetrieveCompanyReviewRsp;
import ws.restful.model.RetrieveStudentReviewRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("CompanyReview")
public class CompanyReviewResource {

    @Context
    private UriInfo context;

    private CompanyReviewSessionBeanLocal companyReviewSessionBeanLocal = lookupReviewSessionBeanLocal();

    /**
     * Creates a new instance of ReviewResource
     */
    public CompanyReviewResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ReviewResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveCompanyReview/{companyReviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentReviewById(@PathParam("companyReviewId") Long companyReviewId) {
        try {
            CompanyReview companyReview = companyReviewSessionBeanLocal.retrieveCompanyReviewByReviewId(companyReviewId);
            RetrieveCompanyReviewRsp retrieveCompanyReviewRsp = new RetrieveCompanyReviewRsp(companyReview);
            return Response.status(Response.Status.OK).entity(retrieveCompanyReviewRsp).build();
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
    @Path("retrieveAllCompanyReviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCompanyReviews() {
        try {
            List<CompanyReview> companyReviews = companyReviewSessionBeanLocal.retrieveAllCompanyReviews();
            RetrieveAllCompanyReviewsRsp retrieveAllCompanyReviewsRsp = new RetrieveAllCompanyReviewsRsp(companyReviews);
            return Response.status(Response.Status.OK).entity(retrieveAllCompanyReviewsRsp).build();
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
    public Response createStudentReview(CreateNewCompanyReviewReq createNewCompanyReviewReq) {
        if (createNewCompanyReviewReq != null) {
            try {
                Long newReviewId = companyReviewSessionBeanLocal.createCompanyReview(createNewCompanyReviewReq.getNewCompanyReview(), createNewCompanyReviewReq.getStudentId(), createNewCompanyReviewReq.getProjectId(), createNewCompanyReviewReq.getCompanyId());
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

    private CompanyReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CompanyReviewSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/CompanyReviewSessionBean!ejb.session.stateless.CompanyReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
