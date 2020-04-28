/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.PaymentSessionBeanLocal;
import entity.Milestone;
import entity.Payment;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.PaymentNotFoundException;
import util.exception.UpdatePaymentException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "paymentManagementManagedBean")
@ViewScoped
public class PaymentManagementManagedBean implements Serializable {

    @EJB(name = "PaymentSessionBeanLocal")
    private PaymentSessionBeanLocal paymentSessionBeanLocal;
    
    private Payment paymentToView ; 
    private String paymentStatus; 
    
    
    
    

    /**
     * Creates a new instance of PaymentManagementManagedBean
     */
    public PaymentManagementManagedBean()  {
    }
    
    @PostConstruct 
    public void PostConstruct() {
        
    }
    
    public void viewPaymentDetails(ActionEvent event) {
        Milestone milestoneForPayment = (Milestone) event.getComponent().getAttributes().get("milestoneForPayment");
        paymentToView = paymentSessionBeanLocal.retrieveAllPaymentByMilestone(milestoneForPayment.getMilestoneId()); 
        if (paymentToView.getIsPaid() == true) {
             setPaymentStatus("Payment Released");  
        } else {
             setPaymentStatus("Payment Unreleased");  
        }
    }
    
    public void releasePayment(ActionEvent event) {
       try {
        Payment p = (Payment) event.getComponent().getAttributes().get("payment");
        if (p.getIsPaid() == true) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment has been released",null));
        } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Payment for milestone successfully released!",null));
        p.setIsPaid(Boolean.TRUE);
        setPaymentStatus("Payment Released");  
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
    
    
    
    
    
    
    
}
