/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
import entity.Payment;
import entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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
import util.exception.MilestoneNotFoundException;
import util.exception.PaymentNotFoundException;
import util.exception.StudentNotFoundException;
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

    @EJB
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;
    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;

    public PaymentSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<Payment> retrievePaymentsByProjectIdAndStudentId(Long projectId, Long studentId) throws PaymentNotFoundException, MilestoneNotFoundException {
        System.out.println("Getting payments for project: " + projectId + " and student id: " + studentId);
        List<Payment> payments = new ArrayList<>();
        try {
            List<Milestone> milestones = milestoneSessionBeanLocal.retrieveMilestonesByProject(projectId);
            for (Milestone milestone : milestones) {
                Query query = em.createQuery("SELECT DISTINCT p FROM Payment p WHERE p.milestone.milestoneId = :inMilestoneId AND p.student.userId = :inStudentId");
                query.setParameter("inMilestoneId", milestone.getMilestoneId());
                query.setParameter("inStudentId", studentId);
                payments.addAll(query.getResultList());
            }
            if (payments.size() == 0) {
                throw new PaymentNotFoundException("No payments available for this project!");
            } else {
                return payments;
            }
        } catch (MilestoneNotFoundException ex) {
            throw new MilestoneNotFoundException("No milestones available for this project!");
    }
    }
    
  
    @Override
    public Long createNewPaymentForMilestone(Payment newPayment, Long milestoneId) throws UnknownPersistenceException, InputDataValidationException, MilestoneNotFoundException {
        try {
            Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(newPayment);

            if (constraintViolations.isEmpty()) {
                try {
                    Milestone milestone = milestoneSessionBeanLocal.retrieveMilestoneByMilestoneId(milestoneId);
                    //Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    newPayment.setMilestone(milestone);
                    // newPayment.setStudent(student);
                    milestone.addPayment(newPayment);
                    // student.addPayment(newPayment);
                    em.persist(newPayment);
                    em.flush();

                    return newPayment.getPaymentId();
                } catch (MilestoneNotFoundException ex) {
                    throw new MilestoneNotFoundException("Milestone Not Found for ID: " + milestoneId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public Long createNewPayment(Payment newPayment, Long milestoneId,
            Long studentId) throws UnknownPersistenceException, InputDataValidationException, MilestoneNotFoundException, StudentNotFoundException {
        try {
            Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(newPayment);

            if (constraintViolations.isEmpty()) {
                try {
                    Milestone milestone = milestoneSessionBeanLocal.retrieveMilestoneByMilestoneId(milestoneId);
                    Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    newPayment.setMilestone(milestone);
                    newPayment.setStudent(student);
                    milestone.addPayment(newPayment);
                    student.addPayment(newPayment);
                    em.persist(newPayment);
                    em.flush();

                    return newPayment.getPaymentId();
                } catch (MilestoneNotFoundException ex) {
                    throw new MilestoneNotFoundException("Milestone Not Found for ID: " + milestoneId);
                } catch (StudentNotFoundException ex) {
                    throw new StudentNotFoundException("Student Not Found for ID: " + studentId);
                }
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
    public List<Payment> retrieveAllPaymentByMilestone(Long milestoneId
    ) {
        Query query = em.createQuery("SELECT p FROM Payment p WHERE p.milestone.milestoneId =:mid");
        query.setParameter("mid", milestoneId);
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
        Payment payment = em.find(Payment.class,
                 paymentId);
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
                paymentToUpdate.setPaymentDescription(payment.getPaymentDescription());
                paymentToUpdate.setIsPaid(payment.getIsPaid());
                paymentToUpdate.setMilestone(payment.getMilestone());
                paymentToUpdate.setStudent(payment.getStudent());
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
