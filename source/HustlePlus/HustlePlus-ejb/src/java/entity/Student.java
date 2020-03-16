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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @Column(nullable = true)
    private File resume;
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
    @Column(nullable = false)
    private List<String> skills;

    @OneToMany(mappedBy = "student")
    private List<Team> teams;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private List<CompanyReview> companyReviews;

    public Student() {
        this.skills = new ArrayList<String>();
        this.teams = new ArrayList<Team>();
        this.companyReviews = new ArrayList<CompanyReview>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public File getResume() {
        return resume;
    }

    public void setResume(File resume) {
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

    public List<CompanyReview> getCompanyReviews() {
        return companyReviews;
    }

    public void setCompanyReviews(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
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
