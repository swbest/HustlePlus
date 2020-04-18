/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Company;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "fileUploadManagedBean")
@RequestScoped
public class FileUploadManagedBean {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;
    
    private Company currentCompany; 
    

    private UploadedFile file;


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null,message);
        }
    }
    
   
    
    public void handleFileUpload(FileUploadEvent event) {
        
       
        
       currentCompany = (Company)event.getComponent().getAttributes().get("photoToUpdate");

        
    } 
    
     public void doUpdateCompany(ActionEvent event)
    {
        currentCompany = (Company)event.getComponent().getAttributes().get("photoToUpdate");
    }

    /**
     * Creates a new instance of FileUploadManagedBean
     */


    
    
}
