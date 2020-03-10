/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Payment;
import java.util.List;
import util.exception.InputDataValidationException;
import util.exception.PaymentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePaymentException;

/**
 *
 * @author dtjldamien
 */
public interface PaymentSessionBeanLocal {

    public Long createNewPayment(Payment newPayment) throws UnknownPersistenceException, InputDataValidationException;

    public void updatePayment(Payment payment) throws PaymentNotFoundException, UpdatePaymentException, InputDataValidationException;

    public void deletePayment(Long paymentId) throws PaymentNotFoundException;

    public Payment retrievePaymentByPaymentId(Long paymentId) throws PaymentNotFoundException;

    public List<Payment> retrieveAllPaymentByStudents();

    public List<Payment> retrieveAllPaymentByCompany();

    public List<Payment> retrieveAllPaymentByProject();

    public List<Payment> retrieveAllPayment();
    
}
