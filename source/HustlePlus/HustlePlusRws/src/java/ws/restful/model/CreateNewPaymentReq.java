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
public class CreateNewPaymentReq {
    
    private Payment newPayment;
    private Long milestoneId;
    private Long studentId;

    public CreateNewPaymentReq() {
    }

    public CreateNewPaymentReq(Payment newPayment, Long milestoneId, Long studentId) {
        this.newPayment = newPayment;
        this.milestoneId = milestoneId;
        this.studentId = studentId;
    }

    public Payment getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(Payment newPayment) {
        this.newPayment = newPayment;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
