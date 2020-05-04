/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.PaymentSessionBeanLocal;
import entity.Application;
import entity.Milestone;
import entity.Payment;
import entity.Project;
import entity.Student;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.exception.ApplicationNotFoundException;
import util.exception.ApproveApplicationException;
import util.exception.InputDataValidationException;
import util.exception.MilestoneNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "applicationManagementManagedBean")
@ViewScoped
public class ApplicationManagementManagedBean implements Serializable {

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;

    @EJB(name = "PaymentSessionBeanLocal")
    private PaymentSessionBeanLocal paymentSessionBeanLocal;

    @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;

    private List<Application> applications;
    private Project viewProjectApplication;
    private Application newApplication;

    private StreamedContent file;
    private String fileName;

    /**
     * Creates a new instance of ApplicationManagementManagedBean
     */
    public ApplicationManagementManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        setApplications(applicationSessionBeanLocal.retrieveAllApplications());

    }

    /**
     * @param event
     */
    public void viewApplications(ActionEvent event) {
        setViewProjectApplication((Project) event.getComponent().getAttributes().get("viewProjectApplication"));
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewApplications.xhtml");
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
        }
    }

    public void approveApplication(ActionEvent event) {

        try {
            Application applicationToApprove = (Application) event.getComponent().getAttributes().get("applicationToApprove");
            applicationSessionBeanLocal.approveApplication(applicationToApprove.getApplicationId());
            applicationToApprove.setIsApproved(Boolean.TRUE);
            applicationToApprove.setIsPending(Boolean.FALSE);

            List<Milestone> msList = milestoneSessionBeanLocal.retrieveMilestonesByProject(applicationToApprove.getProject().getProjectId());
            for (Milestone ms : msList) {
                System.out.println("REACHEDPAYMENTCREATION");
                Payment newPayment = new Payment();
                newPayment.setIsPaid(Boolean.FALSE);
                newPayment.setPaymentDescription(ms.getTitle());
                paymentSessionBeanLocal.createNewPayment(newPayment, ms.getMilestoneId(), applicationToApprove.getStudent().getUserId());
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Application has been successfully approved!", null));
        } catch (ApproveApplicationException | ApplicationNotFoundException | UnknownPersistenceException | InputDataValidationException | MilestoneNotFoundException | StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while approving application: " + ex.getMessage(), null));
        }
    }

    public void rejectApplication(ActionEvent event) {

        try {
            Application applicationToReject = (Application) event.getComponent().getAttributes().get("applicationToReject");
            applicationSessionBeanLocal.rejectApplication(applicationToReject.getApplicationId());
            applicationToReject.setIsApproved(Boolean.FALSE);
            applicationToReject.setIsPending(Boolean.FALSE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Application has been successfully rejected!", null));
        } catch (ApproveApplicationException | ApplicationNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while rejecting application: " + ex.getMessage(), null));
        }
    }

    public void viewApplicationStatus(ActionEvent event) {
        Application applicationToApprove = (Application) event.getComponent().getAttributes().get("applicationToCheck");
        if (applicationToApprove.getIsApproved() == false && applicationToApprove.getIsPending() == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Application Pending", null));
        } else if (applicationToApprove.getIsApproved() == true && applicationToApprove.getIsPending() == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Application Approved", null));
        } else if (applicationToApprove.getIsApproved() == false && applicationToApprove.getIsPending() == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Application Rejected", null));
        }
    }

    public void downloadFile(ActionEvent event) {
        Student studentResume = (Student) event.getComponent().getAttributes().get("studentResume");
        String resumeFileName = studentResume.getResume();
        if (studentResume.getResume() == null) {
            System.out.println("NULLSTRING");
        }
        setFileName(resumeFileName);
        InputStream stream = this.getClass().getResourceAsStream(fileName);
        file = new DefaultStreamedContent(stream, "application/pdf", "downloaded_file.pdf");
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Project getViewProjectApplication() {
        return viewProjectApplication;
    }

    public void setViewProjectApplication(Project viewProjectApplication) {
        this.viewProjectApplication = viewProjectApplication;
    }

    public Application getNewApplication() {
        return newApplication;
    }

    public void setNewApplication(Application newApplication) {
        this.newApplication = newApplication;
    }

    public StreamedContent getFile() {
        return this.file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
