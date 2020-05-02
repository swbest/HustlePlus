/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.PaymentSessionBeanLocal;
import entity.Milestone;
import entity.Payment;
import entity.Project;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.InputDataValidationException;
import util.exception.MilestoneNotFoundException;
import util.exception.PaymentNotFoundException;
import util.exception.UpdatePaymentException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "paymentManagementManagedBean")
@ViewScoped
public class PaymentManagementManagedBean implements Serializable {

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;

    @EJB(name = "PaymentSessionBeanLocal")
    private PaymentSessionBeanLocal paymentSessionBeanLocal;

    
    private Payment paymentToView ; 
    private String paymentStatus; 
    private List<Payment> payments; 
    
    
    
    

    /**
     * Creates a new instance of PaymentManagementManagedBean
     */
    public PaymentManagementManagedBean()  {
    }
    
    @PostConstruct 
    public void PostConstruct() {
        setPayments((List<Payment>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("payments"));
        
    }
    
    public void viewPaymentStatus(ActionEvent event) {
        Payment paymentToCheck = (Payment) event.getComponent().getAttributes().get("paymentToCheck");
        if (paymentToCheck.getIsPaid() == true) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment has been released",null));
    } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment unreleased",null));
        }
    }
    
 
    
    public void retrievePayments(ActionEvent event) {
        try {
            
            if (payments == null) {
                payments = new ArrayList();
            }
        Project p = (Project) event.getComponent().getAttributes().get("projectToView");
        System.out.println(p.getProjectId());
        List <Milestone> milestones = milestoneSessionBeanLocal.retrieveMilestonesByProject(p.getProjectId());
        System.out.println(p.getProjectId());

        if (milestones != null) {
        for (Milestone ms:milestones) {
            List<Payment> pm = paymentSessionBeanLocal.retrieveAllPaymentByMilestone(ms.getMilestoneId());
            if (pm != null) {
            for (Payment pay:pm) {
                getPayments().add(pay);
            } 
            }
        } 
        }
  
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("payments", getPayments()); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/paymentsForProject.xhtml");
    } catch (IOException | MilestoneNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving payments: " + ex.getMessage(), null));
    }
    }
    
    public void releasePayment(ActionEvent event) {
       try {
        Payment p = (Payment) event.getComponent().getAttributes().get("payment");
        if (p.getIsPaid() == true) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment has been released",null));
        } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment for student successfully released!",null));
        p.setIsPaid(Boolean.TRUE);
        paymentSessionBeanLocal.updatePayment(p);
        }
    } catch (PaymentNotFoundException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Payment for milestone not found!",null));
    } catch (UpdatePaymentException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Payment for milestone cannot be updated!",null));
    } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while releasing payment: " + ex.getMessage(), null));
        }
    }

    public Payment getPaymentToView() {
        return paymentToView;
    }

    public void setPaymentToView(Payment paymentToView) {
        this.paymentToView = paymentToView;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    
    
    
    
    
    
    
    
}
