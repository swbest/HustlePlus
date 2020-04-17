/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.StudentSessionBeanLocal;
import entity.Student;
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
import ws.restful.model.CreateNewStudentReq;
import ws.restful.model.CreateNewStudentRsp;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllStudentsRsp;
import ws.restful.model.RetrieveStudentRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("Student")
public class StudentResource {

    @Context
    private UriInfo context;

    StudentSessionBeanLocal studentSessionBean = lookupStudentSessionBeanLocal();

    /**
     * Creates a new instance of StudentResource
     */
    public StudentResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveStudent/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentByStudentId(@PathParam("userId") Long userId) {
        try {
            Student student = studentSessionBean.retrieveStudentByStudentId(userId);
            RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(student);
            return Response.status(Response.Status.OK).entity(retrieveStudentRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("retrieveAllStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllStudents() {
        try {
            List<Student> students = studentSessionBean.retrieveAllStudents();
            RetrieveAllStudentsRsp retrieveAllStudentsRsp = new RetrieveAllStudentsRsp(students);
            return Response.status(Response.Status.OK).entity(retrieveAllStudentsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of ProjectResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(CreateNewStudentReq createNewStudentReq) {
        if (createNewStudentReq != null) {
            try {
                Long newStudentId = studentSessionBean.createStudentAccount(createNewStudentReq.getNewStudent());
                CreateNewStudentRsp createNewStudentRsp = new CreateNewStudentRsp(newStudentId);
                return Response.status(Response.Status.OK).entity(createNewStudentRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private StudentSessionBeanLocal lookupStudentSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StudentSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/StudentSessionBean!ejb.session.stateless.StudentSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
