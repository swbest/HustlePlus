/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Application;

/**
 *
 * @author sw_be
 */
public class RetrieveApplicationRsp {
    
    public Application application;

    public RetrieveApplicationRsp() {
    }

    public RetrieveApplicationRsp(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void Application(Application application) {
        this.application = application;
    }
    
}
