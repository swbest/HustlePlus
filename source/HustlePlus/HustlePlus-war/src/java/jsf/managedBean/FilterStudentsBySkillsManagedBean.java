/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.SkillSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.Skill;
import entity.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author amanda
 */
@Named(value = "filterStudentsBySkillsManagedBean")
@RequestScoped
public class FilterStudentsBySkillsManagedBean {

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;
    
    @Inject
    private ViewStudentManagedBean viewStudentManagedBean;
    
    private String condition;
    private List<Long> selectedSkillIds;
    private List<SelectItem> selectItems;
    private List<Student> students;
    private Student studentToView; 

    /**
     * Creates a new instance of FilterStudentsBySkillsManagedBean
     */
    public FilterStudentsBySkillsManagedBean() {
        condition = "OR";
    }
    
    @PostConstruct
    public void postConstruct()
    {

        List<Skill> skills = skillSessionBeanLocal.retrieveAllSkills();
        selectItems = new ArrayList<>();
        
        for(Skill s:skills)
        {
            selectItems.add(new SelectItem(s.getSkillId(), s.getTitle(), s.getTitle()));
        }
        
        
        condition = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("studentFilterCondition");        
        selectedSkillIds = (List<Long>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("studentFilterSkills");
        
        filterStudent();
    }
    
    @PreDestroy
    public void preDestroy()
    {
        
    }
    
    public void filterStudent()
    {        

        if(selectedSkillIds != null && selectedSkillIds.size() > 0)
        {
            students = studentSessionBeanLocal.filterStudentsBySkills(selectedSkillIds);
        }
        else
        {
            students = studentSessionBeanLocal.retrieveAllStudents();
        }

    }
    
    public void viewStudentDetails(ActionEvent event) throws IOException
    {
        Long studentIdToView = (Long)event.getComponent().getAttributes().get("studentId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("studentIdToView", studentIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterStudentBySkills");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewStudent.xhtml");
    }

    /**
     * @return the selectedSkillIds
     */
    public List<Long> getSelectedSkillIds() {
        return selectedSkillIds;
    }

    /**
     * @param selectedSkillIds the selectedSkillIds to set
     */
    public void setSelectedSkillIds(List<Long> selectedSkillIds) {
        this.selectedSkillIds = selectedSkillIds;
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the viewStudentManagedBean
     */
    public ViewStudentManagedBean getViewStudentManagedBean() {
        return viewStudentManagedBean;
    }

    /**
     * @param viewStudentManagedBean the viewStudentManagedBean to set
     */
    public void setViewStudentManagedBean(ViewStudentManagedBean viewStudentManagedBean) {
        this.viewStudentManagedBean = viewStudentManagedBean;
    }

    public Student getStudentToView() {
        return studentToView;
    }

    public void setStudentToView(Student studentToView) {
        this.studentToView = studentToView;
    }
    
    
    
}
