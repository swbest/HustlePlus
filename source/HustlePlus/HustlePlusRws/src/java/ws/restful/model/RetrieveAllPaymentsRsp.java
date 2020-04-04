/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Payment;
import java.util.List;

/**
 *
 * @author sw_be
 */
public class RetrieveAllPaymentsRsp {
    
    public List<Payment> payments;

    public RetrieveAllPaymentsRsp() {
    }

    public RetrieveAllPaymentsRsp(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
