/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sw_be
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false, length = 32, unique = true)
    @NotNull
    @Size(max = 32)
    private String projectName;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal jobValue;
    @Column(nullable = false)
    @NotNull
    private Integer numStudentsRequired;
    @Column(nullable = false, length = 256)
    @NotNull
    @Size(max = 256)
    private String projectDescription;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    @NotNull
    private Boolean isFinished;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "projects")
    @JoinColumn(nullable = false)
    private List<Skill> skills;
    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones;
    @OneToMany(mappedBy = "project")
    private List<CompanyReview> companyReviews; //reviews by Student 
    @OneToMany(mappedBy = "project")
    private List<StudentReview> studentReviews; //reviews by company on students after this project is done
    @OneToMany(mappedBy = "project")
    private List<Application> applications;
    @ManyToMany(mappedBy = "projects")
    @JoinColumn(nullable = false)
    private List<Student> students;

    public Project() {
        this.skills = new ArrayList<>();
        this.milestones = new ArrayList<>();
        this.companyReviews = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.students = new ArrayList<>();
        this.isFinished = Boolean.FALSE;
    }

    public Project(String projectName, BigDecimal jobValue, String projectDescription, Date startDate, Date endDate) {
        this();
        this.projectName = projectName;
        this.jobValue = jobValue;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getJobValue() {
        return jobValue;
    }

    public void setJobValue(BigDecimal jobValue) {
        this.jobValue = jobValue;
    }

    public Integer getNumStudentsRequired() {
        return numStudentsRequired;
    }

    public void setNumStudentsRequired(Integer numStudentsRequired) {
        this.numStudentsRequired = numStudentsRequired;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public void addMilestone(Milestone milestone) {
        if (!this.milestones.contains(milestone)) {
            this.milestones.add(milestone);
        }
    }

    public void removeMilestone(Milestone milestone) {
        if (this.milestones.contains(milestone)) {
            this.milestones.remove(milestone);
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
            this.studentReviews.add(studentReview);
        }
    }

    public void removeStudentReview(StudentReview studentReview) {
        if (this.studentReviews.contains(studentReview)) {
            this.studentReviews.remove(studentReview);
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
            this.companyReviews.add(companyReview);
        }
    }

    public void removeCompanyReview(CompanyReview companyReview) {
        if (this.companyReviews.contains(companyReview)) {
            this.companyReviews.remove(companyReview);
        }
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the projectId fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Project[ id=" + projectId + " ]";
    }

    

    

}
