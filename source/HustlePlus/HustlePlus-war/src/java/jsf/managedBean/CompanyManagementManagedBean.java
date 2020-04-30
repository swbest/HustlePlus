/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Company;
import entity.CompanyReview;
import entity.Project;
import entity.Review;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.SuspendCompanyException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;
import util.exception.VerifyCompanyException;
import util.security.CryptographicHelper;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "companyManagementManagedBean")
@ViewScoped

public class CompanyManagementManagedBean implements Serializable {

    @EJB(name = "CompanyReviewSessionBeanLocal")
    private CompanyReviewSessionBeanLocal companyReviewSessionBeanLocal;
    
    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @Inject
    private ViewCompanyManagedBean viewCompanyManagedBean;

    private List<Company> companies;
    private List<Company> filteredCompanies;

    private Company newCompany;
    private List<Project> projects;
    private List<Review> reviews;
    
    private List<CompanyReview> companyReviews;

    private Company selectedCompanyToUpdate;

    private Company selectedCompanyToVerify;

    private Company selectedCompanyToSuspend;

    private Company selectedCompanyToEmail;

    private Company companyToUpdatePhoto;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String companyUsername;
    
    private String verifiedStatus; 
    private String suspendedStatus; 
    
    private Company companyToView; 

    /**
     * Creates a new instance of companyMangementManagedBean
     */
    public CompanyManagementManagedBean() {

        newCompany = new Company();
    }

    @PostConstruct
    public void postConstruct() {
        setCompanies(companySessionBeanLocal.retrieveAllCompanies());
        setProjects(projectSessionBeanLocal.retrieveAllProjects());
        setReviews(reviewSessionBeanLocal.retrieveAllReviews());
        setCompanyReviews((List<CompanyReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyReviews"));
    }

    public void createNewCompany(ActionEvent event) {

        try {

            System.out.println("createNew1");
            Long companyId = companySessionBeanLocal.createNewCompany(newCompany);
            System.out.println("createNew2");
            companies.add(companySessionBeanLocal.retrieveCompanyByCompanyId(companyId));

            if (getFilteredCompanies() != null) {
                getFilteredCompanies().add(companySessionBeanLocal.retrieveCompanyByCompanyId(companyId));
            }

            newCompany = new Company();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Company created successfully (Company ID: " + companyId + ")", "Please wait for the admin to verify your account"));

        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: The company is not found", null));
        } catch (CompanyNameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: The company name already exist", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: " + ex.getMessage(), null));
        }
    }

    public void doCreateCompany(ActionEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Company created successfully (Company ID: " + newCompany.getUserId() + ")", null));
    }

    public void doUpdateCompany(ActionEvent event) {
        selectedCompanyToUpdate = (Company) event.getComponent().getAttributes().get("selectedCompanyToUpdate");

    }

    public void updateCompany(ActionEvent event) {

        try {
            companySessionBeanLocal.updateCompany(getSelectedCompanyToUpdate());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company updated successfully", null));
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteCompany(ActionEvent event) {
        try {
            Company companyToDelete = (Company) event.getComponent().getAttributes().get("companyToDelete");
            companySessionBeanLocal.deleteCompany(companyToDelete.getUserId());
            companies.remove(companyToDelete);

            if (getFilteredCompanies() != null) {
                getFilteredCompanies().remove(companyToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Company Account deleted successfully", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
        } catch (CompanyNotFoundException | DeleteCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting company account: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void verifyCompany(ActionEvent event) {
        try {  
            Company selectedCompany= (Company) event.getComponent().getAttributes().get("selectedCompanyToVerify");
            if (selectedCompany.getIsVerified() == false) {
            companySessionBeanLocal.verifyCompany(selectedCompany.getUserId());
            selectedCompany.setIsVerified(Boolean.TRUE);
            verifiedStatus = "Yes"; 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company successfully verified!", null));
            } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Company has been verified!", null));
            }
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying company account: " + ex.getMessage(), null));
        } catch (VerifyCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying company account: " + ex.getMessage(), null));
        }

    }

    public void suspendCompany(ActionEvent event) {
        try {
            Company selectedCompany = (Company) event.getComponent().getAttributes().get("selectedCompanyToSuspend");
            if (selectedCompany.getIsSuspended() == false) {
            companySessionBeanLocal.suspendCompany(selectedCompany.getUserId());
            selectedCompany.setIsSuspended(Boolean.TRUE);
            suspendedStatus = "Yes";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company successfully suspended!", null));
            } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Company has been suspended!", null));
            }
        } catch (CompanyNotFoundException | SuspendCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while suspending company account: " + ex.getMessage(), null));
        }

    }

    public void checkIfVerified(ActionEvent event) {

        try {
            Company companyToCheck = (Company) event.getComponent().getAttributes().get("checkCompany");
            System.out.println(companyToCheck.getUserId());

            if (companySessionBeanLocal.checkCompany(companyToCheck)) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/createNewProject.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/verificationError.xhtml");
            }
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doChangePassword(ActionEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/changePassword.xhtml");
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    public void doChangeForgottenPassword(ActionEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/changeForgottenPassword.xhtml");
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void changeForgottenPassword(ActionEvent event) {

        try {
            if (newPassword == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,   "Change Password Error: Password cannot be null", null));  
               
           } else if (confirmPassword == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,   "Change Password Error: Password must be verified", null));  
           } else if (!newPassword.equals(confirmPassword)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password validation Error: Passwords do not match", null));
            } else {

                Company company = companySessionBeanLocal.retrieveCompanyByUsername(companyUsername);
                companySessionBeanLocal.updatePassword(company, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password changed successfully!", null));
                newPassword = null;
                confirmPassword = null;
                companyUsername = null;
            }

        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
        } catch (UpdateCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: " + ex.getMessage(), null));
        }
    }

    public void changePassword(ActionEvent event) {
        //old password is the one i keyed in 
        //password is the original password (in hash) 
        System.out.println("CMMB0");

        selectedCompanyToUpdate = (Company) event.getComponent().getAttributes().get("selectedCompany");

        //Company companyPasswordChange = (Company)event.getComponent().getAttributes().get("selectedCompany") ;
        //String password = companyPasswordChange.getPassword();
        if (oldPassword != null || newPassword != null || confirmPassword != null) {
            oldPassword = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(oldPassword + selectedCompanyToUpdate.getSalt()));
        } else {
     
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password cannot be null", null));
        }

        System.out.println(selectedCompanyToUpdate.getPassword()); //in hash 
        System.out.println(oldPassword);
        if (!oldPassword.equals(selectedCompanyToUpdate.getPassword())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Old Password is invalid", null));
        } else if (!newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password validation Error: Passwords do not match", null));
        } else {

            try {
                companySessionBeanLocal.updatePassword(selectedCompanyToUpdate, newPassword);


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changed successfully!", null));
            } catch (CompanyNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }

            //selectedCompanyToUpdate.setPassword(newPassword);
            System.out.println(newPassword);
            System.out.println(oldPassword);
            System.out.println(selectedCompanyToUpdate.getPassword());
            System.out.println(selectedCompanyToUpdate.getName());
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password changed successfully!", null));
        }

    }

    public void resendEmail(ActionEvent event) {

        Company selectedCompanyToEmail = (Company) event.getComponent().getAttributes().get("selectedCompanyToEmail");
        //companySessionBeanLocal.resendEmail(selectedCompanyToEmail.getUserId());

    }

    public void uploadPicture(FileUploadEvent event) {

        try {
            //Long newCompanyId = companySessionBeanLocal.createNewCompany(newCompany);
            //Company newC = companySessionBeanLocal.retrieveCompanyByCompanyId(newCompanyId);

            String uploadedFileName = event.getFile().getFileName();

            String newFileName = CryptographicHelper.getInstance().generateUUID().toString() + "profile_" + uploadedFileName;
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1")
                    + System.getProperty("file.separator")
                    + newFileName;

            String docRootFilePath = "/uploadedFiles/" + newFileName;
            newCompany.setIcon(newFileName);
            // companySessionBeanLocal.uploadIcon(newCompany.getUserId(), docRootFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Picture successfully uploaded!", null));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
       try {
            //Long newCompanyId = companySessionBeanLocal.createNewCompany(newCompany);
            //Company newC = companySessionBeanLocal.retrieveCompanyByCompanyId(newCompanyId);

//           companyToUpdatePhoto  = (Company) event.getComponent().getAttributes().get("companyToUpdatePhoto");
//           ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToUpdatePhoto", companyToUpdatePhoto); 
            String uploadedFileName = event.getFile().getFileName();

            String newFileName = CryptographicHelper.getInstance().generateUUID().toString() + "profile_" + uploadedFileName;
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1")
                    + System.getProperty("file.separator")
                    + newFileName;

            String docRootFilePath = "/uploadedFiles/" + newFileName;
            selectedCompanyToUpdate.setIcon(newFileName);
            //companySessionBeanLocal.updateCompany(companyToUpdatePhoto);
            // companySessionBeanLocal.uploadIcon(newCompany.getUserId(), docRootFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        } //catch (UpdateCompanyException | CompanyNotFoundException ex) {
           // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        //}
    }
    
    public void retrieveAllReviews(ActionEvent event) {
       try {
        Company company = (Company) event.getComponent().getAttributes().get("selectedCompany");
        companyReviews = companyReviewSessionBeanLocal.retrieveAllCompanyReviewsForCompany(company.getUserId());
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyReviews", companyReviews); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/allReviewsOfCompany.xhtml");
    } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
    }
    }
    
  

    public void setCompanyToUpdatePhoto(ActionEvent event) {
        companyToUpdatePhoto = (Company) event.getComponent().getAttributes().get("companyToUpdatePhoto");
    }

    public ViewCompanyManagedBean getViewCompanyManagedBean() {
        return viewCompanyManagedBean;
    }

    public void setViewCompanyManagedBean(ViewCompanyManagedBean viewCompanyManagedBean) {
        this.viewCompanyManagedBean = viewCompanyManagedBean;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(Company newCompany) {
        this.newCompany = newCompany;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Company getSelectedCompanyToUpdate() {
        return selectedCompanyToUpdate;
    }

    public void setSelectedCompanyToUpdate(Company selectedCompanyToUpdate) {
        this.selectedCompanyToUpdate = selectedCompanyToUpdate;
    }

    public List<Company> getFilteredCompanies() {
        return filteredCompanies;
    }

    public void setFilteredCompanies(List<Company> filteredCompanies) {
        this.filteredCompanies = filteredCompanies;
    }

    public Company getSelectedCompanyToVerify() {
        return selectedCompanyToVerify;
    }

    public void setSelectedCompanyToVerify(Company selectedCompanyToVerify) {
        this.selectedCompanyToVerify = selectedCompanyToVerify;
    }

    public Company getSelectedCompanyToSuspend() {
        return selectedCompanyToSuspend;
    }

    public void setSelectedCompanyToSuspend(Company selectedCompanyToSuspend) {
        this.selectedCompanyToSuspend = selectedCompanyToSuspend;
    }

    public Company getSelectedCompanyToEmail() {
        return selectedCompanyToEmail;
    }

    public void setSelectedCompanyToEmail(Company selectedCompanyToEmail) {
        this.selectedCompanyToEmail = selectedCompanyToEmail;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Company getCompanyToUpdatePhoto() {
        return companyToUpdatePhoto;
    }

    public void setCompanyToUpdatePhoto(Company companyToUpdatePhoto) {
        this.companyToUpdatePhoto = companyToUpdatePhoto;
    }

    public String getCompanyUsername() {
        return companyUsername;
    }

    public void setCompanyUsername(String companyUsername) {
        this.companyUsername = companyUsername;
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public String getSuspendedStatus() {
        return suspendedStatus;
    }

    public void setSuspendedStatus(String suspendedStatus) {
        this.suspendedStatus = suspendedStatus;
    }

    public Company getCompanyToView() {
        return companyToView;
    }

    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }

    public List<CompanyReview> getCompanyReviews() {
        return companyReviews;
    }

    public void setCompanyReviews(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }
    
    
    
    
    
    

}
