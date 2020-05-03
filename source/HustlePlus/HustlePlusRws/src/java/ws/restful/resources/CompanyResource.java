/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Company;
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
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllCompaniesRsp;
import ws.restful.model.RetrieveCompanyRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("Company")
public class CompanyResource {

    CompanySessionBeanLocal companySessionBean = lookupCompanySessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyResource
     */
    public CompanyResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveCompany/{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCompanyById(@PathParam("companyId") Long companyId) {
        try {
            Company company = companySessionBean.retrieveCompanyByCompanyId(companyId);
            company.getStudentReviews().clear();
            company.getCompanyReviews().clear();
            company.getProjects().clear();
            RetrieveCompanyRsp retrieveProjectRsp = new RetrieveCompanyRsp(company);
            return Response.status(Status.OK).entity(retrieveProjectRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllCompanies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCompanies() {
        try {
            List<Company> companies = companySessionBean.retrieveAllCompanies();
            for (Company c : companies) {
                c.getStudentReviews().clear();
                c.getCompanyReviews().clear();
                c.getProjects().clear();
            }
            RetrieveAllCompaniesRsp retrieveAllCompaniesRsp = new RetrieveAllCompaniesRsp(companies);
            return Response.status(Status.OK).entity(retrieveAllCompaniesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private CompanySessionBeanLocal lookupCompanySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CompanySessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/CompanySessionBean!ejb.session.stateless.CompanySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
