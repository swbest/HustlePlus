/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Company;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author amanda
 */
@Named(value = "viewCompanyManagedBean")
@ViewScoped
public class ViewCompanyManagedBean implements Serializable {

    private Company companyToView;
    
    /**
     * Creates a new instance of ViewCompanyManagedBean
     */
    public ViewCompanyManagedBean() {
        companyToView = new Company();
    }
    
    @PostConstruct
    public void postConstruct()
    {
    }

    /**
     * @return the companyToView
     */
    public Company getCompanyToView() {
        return companyToView;
    }

    /**
     * @param companyToView the companyToView to set
     */
    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }
    
}
