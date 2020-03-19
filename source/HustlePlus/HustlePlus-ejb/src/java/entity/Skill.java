/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dtjldamien
 */
@Entity
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;
    @Column(nullable = false, length = 16, unique = true)
    @NotNull
    @Size(max = 16)
    private String title;
    
    @ManyToMany
    private List<Student> students;
    @ManyToMany
    private List<Project> projects;

    public Skill() {
        this.students = new ArrayList<Student>();
        this.projects = new ArrayList<Project>();
    }

    public Skill(String title) {
        this();
        this.title = title;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
        }
    }

    public void removeStudent(Student student) {
        if (this.students.contains(student)) {
            this.students.remove(student);
        }
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (!this.projects.contains(project)) {
            this.projects.add(project);
        }
    }

    public void removeProject(Project project) {
        if (this.projects.contains(project)) {
            this.projects.remove(project);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skillId != null ? skillId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the skillId fields are not set
        if (!(object instanceof Skill)) {
            return false;
        }
        Skill other = (Skill) object;
        if ((this.skillId == null && other.skillId != null) || (this.skillId != null && !this.skillId.equals(other.skillId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Skills[ id=" + skillId + " ]";
    }

}
