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
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author amanda
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Company extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String companyName;
    @NotNull
    @Size(max = 256, message = "Company description should be at most 256 characters")
    @Column(nullable = false, length = 256)
    private String description;
    @Min(value = 0, message = "Average rating should not be less than 0")
    @Max(value = 5, message = "Average rating should not be more than 5")
    @Column(nullable = true)
    private Double avgRating;
    @NotNull
    @Column(nullable = false)
    private Boolean isVerified;
    @NotNull
    @Column(nullable = false)
    private Boolean isSuspended;

    @OneToMany(mappedBy = "company")
    private List<Project> projects;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private List<StudentReview> studentReviews;

    public Company() {
        this.projects = new ArrayList<Project>();
        this.studentReviews = new ArrayList<StudentReview>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<StudentReview> getStudentReviews() {
        return studentReviews;
    }

    public void setStudentReviews(List<StudentReview> studentReviews) {
        this.studentReviews = studentReviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
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

    public void addStudentReview(StudentReview studentReview) {
        if (!this.studentReviews.contains(studentReview)) {
            this.studentReviews.add(studentReview);
        }
    }

    public void removeStudentReview(StudentReview studentReview) {
        if (this.studentReviews.contains(studentReview)) {
            this.studentReviews.remove(studentReview);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the companyId fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Company[ id=" + userId + " ]";
    }
}
