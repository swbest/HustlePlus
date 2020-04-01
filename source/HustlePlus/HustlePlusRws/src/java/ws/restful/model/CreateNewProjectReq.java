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
public class CreateNewProjectReq {
    
    private Project newProject;
    private Long companyId;

    public CreateNewProjectReq() {
    }

    public CreateNewProjectReq(Project newProject, Long companyId) {
        this.newProject = newProject;
        this.companyId = companyId;
    }

    public Project getNewProject() {
        return newProject;
    }

    public void setNewProject(Project newProject) {
        this.newProject = newProject;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
}
