
import entity.Project;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nurhidayah
 */
@FacesConverter(value = "projectConverter", forClass = Project.class)
public class projectConverter implements Converter {
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        System.out.println("********** ProjectConverter.getAsObject: " + value);
        
        if (value == null || value.trim().length() == 0 || value.trim().equals("null")) {
            return null;
        } 
        
        try {
            Long objLong = Long.parseLong(value);
            List<Project>projects = (List<Project>)context.getExternalContext().getSessionMap().get("milestonesProject");
            
            for (Project project:projects) {
                if(project.getProjectId().equals(objLong)) {
                    return project ;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Please select a valid project"); 
        }
        return null;
    }
    
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return "";
        }
        
        if (value instanceof Project) {
            Project project = (Project) value;
            
            try {
                System.out.println("********** ProjectConverter.getAsString: " + project.getProjectId().toString());
                return project.getProjectId().toString();
            } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid value"); 
            }
        } else {
            throw new IllegalArgumentException("Invalid value"); 
        }
    }
    
}
