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
public class CreateNewApplicationReq {
    
    private Application newApplication;
    private Long projectId;
    private Long studentId;

    public CreateNewApplicationReq() {
    }

    public CreateNewApplicationReq(Application newApplication, Long projectId, Long studentId) {
        this.newApplication = newApplication;
        this.projectId = projectId;
        this.studentId = studentId;
    }

    public Application getNewApplication() {
        return newApplication;
    }

    public void setNewApplication(Application newApplication) {
        this.newApplication = newApplication;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long applicationId) {
        this.projectId = applicationId;
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
}
