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
public class CreateNewApplicationRsp {
    
    private Long newApplicationId;

    public CreateNewApplicationRsp() {
    }

    public CreateNewApplicationRsp(Long newApplicationId) {
        this.newApplicationId = newApplicationId;
    }

    public Long getNewApplicationId() {
        return newApplicationId;
    }

    public void setNewApplicationId(Long newApplicationId) {
        this.newApplicationId = newApplicationId;
    }
}
