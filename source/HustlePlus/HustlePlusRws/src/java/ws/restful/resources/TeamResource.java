/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.TeamSessionBeanLocal;
import entity.Student;
import entity.Team;
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
import ws.restful.model.CreateNewStudentRsp;
import ws.restful.model.CreateNewTeamReq;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveTeamsByStudentRsp;
import ws.restful.model.RetrieveTeamRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("Team")
public class TeamResource {

    TeamSessionBeanLocal teamSessionBean = lookupTeamSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TeamResource
     */
    public TeamResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.ProjectResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/retrieveTeam/{teamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTeamById(@PathParam("teamId") Long teamId) {
        try {
            Team team = teamSessionBean.retrieveTeamByTeamId(teamId);
            RetrieveTeamRsp retrieveTeamRsp = new RetrieveTeamRsp(team);
            return Response.status(Response.Status.OK).entity(retrieveTeamRsp).build();
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
    @Path("/retrieveTeamsByStudentId/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTeamsByStudentId(@PathParam("studentId") Long studentId) {
        try {
            List<Team> teams = teamSessionBean.retrieveTeamsByStudentId(studentId);
            for (Team team : teams) {
                team.setProject(null);
                List<Student> students = team.getStudents();
                for (Student student : students) {
                    student.getApplications().clear();
                    student.setPassword(null);
                    student.setSalt(null);
                    student.getApplications().clear();
                    student.getSkills().clear();
                    student.getStudentReviews().clear();
                    student.getCompanyReviews().clear();
                    student.getPayments().clear();
                    student.getProjects().clear();
                    student.getTeams().clear();
                }
            }
            RetrieveTeamsByStudentRsp retrieveAllTeamsRsp = new RetrieveTeamsByStudentRsp(teams);
            return Response.status(Response.Status.OK).entity(retrieveAllTeamsRsp).build();
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
    public Response createTeam(CreateNewTeamReq createNewTeamReq) {
        if (createNewTeamReq != null) {
            try {
                Long newTeamId = teamSessionBean.createNewTeam(createNewTeamReq.getNewTeam(), createNewTeamReq.getStudentId());
                CreateNewStudentRsp createNewStudentRsp = new CreateNewStudentRsp(newTeamId);
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

    private TeamSessionBeanLocal lookupTeamSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeamSessionBeanLocal) c.lookup("java:global/HustlePlus/HustlePlus-ejb/TeamSessionBean!ejb.session.stateless.TeamSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
