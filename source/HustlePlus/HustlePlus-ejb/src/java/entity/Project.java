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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private List<String> skills;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Company company;
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    private Team team;
    @OneToMany(mappedBy = "milestone")
    private List<Milestone> milestones;
    @OneToMany(mappedBy = "review")
    private List<Review> reviews;

    public Project() {
        this.skills = new ArrayList<String>();
        this.milestones = new ArrayList<Milestone>();
        this.reviews = new ArrayList<>();
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
    }

    public void removeSkill(String skill) {
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        if (!this.reviews.contains(review)) {
            this.reviews.add(review);
        }
    }

    public void removeReview(Review review) {
        if (this.reviews.contains(review)) {
            this.reviews.remove(review);
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
