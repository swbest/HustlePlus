/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hiixdayah
 */
@Entity
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = true)
    private String resume;
    @NotNull
    @Size(max = 256, message = "Description should be at most 256 characters")
    @Column(nullable = false)
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
    @NotNull
    @Size(max = 30, message = "Account name should be at most 30 characters")
    @Column(nullable = false, length = 30)
    private String bankAccountName;
    @Column(nullable = false)
    private Long bankAccountNumber;

    @ManyToMany(mappedBy = "students")
    @JoinColumn(nullable = false)
    private List<Skill> skills;
    @ManyToMany(mappedBy = "students")
    private List<Team> teams;
    @OneToMany(mappedBy = "student")
    private List<CompanyReview> companyReviews; // reviews by this.student on company  
    @OneToMany(mappedBy = "studentReviewed")
    private List<StudentReview> studentReviews; // reviews on this.student by company/another student   
    @OneToMany(mappedBy = "student")
    private List<Payment> payments;
    @OneToMany(mappedBy = "student")
    private List<Application> applications;
    @ManyToMany
    private List<Project> projects;

    public Student() {
        this.skills = new ArrayList<Skill>();
        this.teams = new ArrayList<Team>();
        this.companyReviews = new ArrayList<CompanyReview>();
        this.payments = new ArrayList<Payment>();
        this.applications = new ArrayList<Application>();
        this.projects = new ArrayList<Project>();
        this.isVerified = Boolean.FALSE;
        this.isSuspended = Boolean.FALSE;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
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

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public Long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(Long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
    }

    public void removeSkill(Skill skill) {
        if (this.skills.contains(skill)) {
            this.skills.remove(skill);
        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        if (!this.teams.contains(team)) {
            this.teams.add(team);
        }
    }

    public void removeTeam(Team team) {
        if (this.teams.contains(team)) {
            this.teams.remove(team);
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

    public List<StudentReview> getStudentReviews() {
        return studentReviews;
    }

    public void setStudentReviews(List<StudentReview> studentReviews) {
        this.studentReviews = studentReviews;
    }

    public void addStudentReview(StudentReview studentReview) {
        if (!this.studentReviews.contains(studentReview)) {
            this.getStudentReviews().add(studentReview);
        }
    }

    public void removeStudentReview(StudentReview studentReview) {
        if (this.getStudentReviews().contains(studentReview)) {
            this.getStudentReviews().remove(studentReview);
        }
    }

    public List<CompanyReview> getCompanyReviews() {
        return companyReviews;
    }

    public void setCompanyReviews(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }

    public void addCompanyReview(CompanyReview companyReview) {
        if (!this.companyReviews.contains(companyReview)) {
            this.getCompanyReviews().add(companyReview);
        }
    }

    public void removeCompanyReview(CompanyReview companyReview) {
        if (this.getCompanyReviews().contains(companyReview)) {
            this.getCompanyReviews().remove(companyReview);
        }
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        if (!this.payments.contains(payment)) {
            this.payments.add(payment);
        }
    }

    public void removePayment(Payment payment) {
        if (this.payments.contains(payment)) {
            this.payments.remove(payment);
        }
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        if (!this.applications.contains(application)) {
            this.applications.add(application);
        }
    }

    public void removeApplication(Application application) {
        if (this.applications.contains(application)) {
            this.applications.remove(application);
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
        // TODO: Warning - this method won't work in the case the studentId fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ id=" + userId + " ]";
    }
}
