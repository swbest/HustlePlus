/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.StudentSessionBeanLocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ws.restful.model.UploadFileRsp;

/**
 * REST Web Service
 *
 * @author dtjldamien
 */
@Path("File")
public class FileResource {

    StudentSessionBeanLocal studentSessionBeanLocal = lookupStudentSessionBeanLocal();

    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;

    /**
     * Creates a new instance of FileResource
     */
    public FileResource() {
    }

    @POST
    @Path("uploadresume/{studentId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@FormDataParam("file") InputStream uploadedFileInputStream,
            @FormDataParam("file") FormDataContentDisposition uploadedFileDetails,
            @PathParam("studentId") Long studentId) {
        try {
            System.err.println("********** FileResource.upload()");

            String outputFilePath = servletContext.getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + uploadedFileDetails.getFileName();
            File file = new File(outputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                a = uploadedFileInputStream.read(buffer);
                if (a < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            uploadedFileInputStream.close();
            studentSessionBeanLocal.uploadResume(studentId, outputFilePath);
            return Response.status(Status.OK).entity(new UploadFileRsp("Resume upload success!")).build();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new UploadFileRsp("Resume upload error!")).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new UploadFileRsp("file processing error")).build();
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
