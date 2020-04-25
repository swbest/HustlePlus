/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "fileDownloadManagedBean")
@SessionScoped
public class FileDownloadManagedBean implements Serializable {

    /**
     * Creates a new instance of FileDownloadManagedBean
     */
    public FileDownloadManagedBean() {
    }
    
    public void download() {
        try {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        
        externalContext.responseReset();
        externalContext.setResponseContentType("resume/pdf");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\resume.pdf\"");
        
        FileInputStream inputStream = new FileInputStream(new File(""));
        OutputStream outputStream = externalContext.getResponseOutputStream();
        
        byte[]buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer))>0) {
            outputStream.write(buffer,0,length);
        }
        inputStream.close(); 
        context.responseComplete();
        
        
    } catch(Exception e) {
        e.printStackTrace(System.out);

    }    
}
    
}
