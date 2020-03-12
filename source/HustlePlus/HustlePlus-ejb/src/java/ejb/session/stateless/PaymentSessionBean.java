/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Payment;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.PaymentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePaymentException;

/**
 *
 * @author dtjldamien
 */
@Stateless
public class PaymentSessionBean implements PaymentSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PaymentSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Payment createNewPayment(Payment newPayment) throws UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(newPayment);

            if (constraintViolations.isEmpty()) {
                em.persist(newPayment);
                em.flush();

                return newPayment;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Payment> retrieveAllPayment() {
        Query query = em.createQuery("SELECT p FROM Payment p");
        return query.getResultList();
    }

    // to do when relationships are done up
    @Override
    public List<Payment> retrieveAllPaymentByProject() {
        Query query = em.createQuery("SELECT p FROM Payment p");
        return query.getResultList();
    }

    @Override
    public List<Payment> retrieveAllPaymentByCompany() {
        Query query = em.createQuery("SELECT p FROM Payment p");
        return query.getResultList();
    }

    @Override
    public List<Payment> retrieveAllPaymentByStudents() {
        Query query = em.createQuery("SELECT p FROM Payment p");
        return query.getResultList();
    }

    @Override
    public Payment retrievePaymentByPaymentId(Long paymentId) throws PaymentNotFoundException {
        Payment payment = em.find(Payment.class, paymentId);
        if (payment != null) {
            return payment;
        } else {
            throw new PaymentNotFoundException("Payment ID " + paymentId + " does not exist!");
        }
    }

    @Override
    public void updatePayment(Payment payment) throws PaymentNotFoundException, UpdatePaymentException, InputDataValidationException {
        if (payment != null && payment.getPaymentId() != null) {
            Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(payment);

            if (constraintViolations.isEmpty()) {
                Payment paymentToUpdate = retrievePaymentByPaymentId(payment.getPaymentId());
                paymentToUpdate.setAccountName(payment.getAccountName());
                paymentToUpdate.setAccountNumber(payment.getAccountNumber());
                paymentToUpdate.setPaymentDescription(payment.getPaymentDescription());
                paymentToUpdate.setPaid(payment.getPaid());
                paymentToUpdate.setMilestone(payment.getMilestone());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PaymentNotFoundException("Admin Staff Id not provided for admin staff to be updated");
        }
    }

    @Override
    public void deletePayment(Long paymentId) throws PaymentNotFoundException {
        Payment paymentToRemove = retrievePaymentByPaymentId(paymentId);
        em.remove(paymentToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Payment>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
