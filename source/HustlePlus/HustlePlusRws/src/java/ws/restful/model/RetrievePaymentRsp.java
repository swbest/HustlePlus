/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Payment;

/**
 *
 * @author sw_be
 */
public class RetrievePaymentRsp {
    
    public Payment payment;

    public RetrievePaymentRsp() {
    }

    public RetrievePaymentRsp(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
