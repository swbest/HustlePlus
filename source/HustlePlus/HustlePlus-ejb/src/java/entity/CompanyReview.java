/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nurhidayah
 */

//student review Company/Project
@Entity
public class CompanyReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long companyReviewId;
    
    @Column(nullable = false, length = 256)
    @NotNull
    @Size(max = 256)
    private String reviewText;
    
    @Column(nullable = false)
    @NotNull
    private Integer rating;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Project project; //student review project
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Student student; //reviewee 
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Company company; //student review project of company 
    
    public CompanyReview() {
    }

    public CompanyReview(String reviewText, Integer rating) {
        this();
        this.reviewText = reviewText;
        this.rating = rating;
    }


    public Long getCompanyReviewId() {
        return companyReviewId;
    }

    public void setCompanyReviewId(Long companyReviewId) {
        this.companyReviewId = companyReviewId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyReviewId != null ? companyReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyReview)) {
            return false;
        }
        CompanyReview other = (CompanyReview) object;
        if ((this.companyReviewId == null && other.companyReviewId != null) || (this.companyReviewId != null && !this.companyReviewId.equals(other.companyReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompanyReview[ companyReviewId=" + companyReviewId + " ]";
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
}
