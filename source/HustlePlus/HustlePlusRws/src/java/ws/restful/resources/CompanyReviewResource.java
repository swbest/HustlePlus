/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import entity.Company;
import entity.CompanyReview;
import entity.Project;
import entity.Skill;
import entity.Student;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.model.CreateNewCompanyReviewReq;
import ws.restful.model.CreateNewCompanyReviewRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveCompanyReviewsRsp;

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
    @Path("retrieveMyCompanyReviews/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMyCompanyReviews(@PathParam("studentId") Long studentId) { // company rating this user
        try {
            List<CompanyReview> companyReviews = companyReviewSessionBeanLocal.retrieveAllCompanyReviewsForStudent(studentId);
            for (CompanyReview companyReview : companyReviews) {
                Company company = companyReview.getCompany();
                company.getStudentReviews().clear();
                company.getCompanyReviews().clear();
                company.getProjects().clear();

                Project p = companyReview.getProject();
                p.getApplications().clear();
                p.getCompany().getProjects().clear();
                p.getCompanyReviews().clear();
                p.getMilestones().clear();
                List<Skill> skills = p.getSkills();
                for (Skill s : skills) {
                    s.getProjects().clear();
                }
                p.getStudentReviews().clear();
                List<Student> students = p.getStudents();
                for (Student s : students) {
                    s.getProjects().clear();
                }

                companyReview.setStudent(null);
            }
            RetrieveCompanyReviewsRsp retrieveAllCompanyReviewsRsp = new RetrieveCompanyReviewsRsp(companyReviews);
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
    public Response createCompanyReview(CreateNewCompanyReviewReq createNewCompanyReviewReq) {
        if (createNewCompanyReviewReq != null) {
            System.out.println(createNewCompanyReviewReq.getNewCompanyReview());
            System.out.println("Creating new company review review text: " + createNewCompanyReviewReq.getNewCompanyReview().getReviewText() + " of rating: " + createNewCompanyReviewReq.getNewCompanyReview().getRating() + "with project id:" + createNewCompanyReviewReq.getProjectId() + " made by student id: " + createNewCompanyReviewReq.getStudentId());
            try {
                Long newReviewId = companyReviewSessionBeanLocal.createCompanyReview(createNewCompanyReviewReq.getNewCompanyReview(), createNewCompanyReviewReq.getCompanyId(), createNewCompanyReviewReq.getProjectId(), createNewCompanyReviewReq.getStudentId());
                CreateNewCompanyReviewRsp createNewReviewRsp = new CreateNewCompanyReviewRsp(newReviewId);
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
