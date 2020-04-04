/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Project;

/**
 *
 * @author dtjldamien
 */
public class RetrieveProjectRsp {

    public Project project;

    public RetrieveProjectRsp() {
    }

    public RetrieveProjectRsp(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
