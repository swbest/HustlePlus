/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author sw_be
 */
public class CreateNewPaymentRsp {
    
    private Long newPaymentId;

    public CreateNewPaymentRsp() {
    }

    public CreateNewPaymentRsp(Long newPaymentId) {
        this.newPaymentId = newPaymentId;
    }

    public Long getNewPaymentId() {
        return newPaymentId;
    }

    public void setNewPaymentId(Long newPaymentId) {
        this.newPaymentId = newPaymentId;
    }
}
