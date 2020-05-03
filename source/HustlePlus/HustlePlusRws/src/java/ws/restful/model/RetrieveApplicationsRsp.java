/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Application;
import entity.Student;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveApplicationsRsp {

    public List<Application> applications;

    public RetrieveApplicationsRsp() {
    }

    public RetrieveApplicationsRsp(List<Application> applications) {
        this.applications = applications;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
