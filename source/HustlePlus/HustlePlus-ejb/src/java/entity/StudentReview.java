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
//company review student after doing project
//student review student 
@Entity
public class StudentReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentReviewId;
    
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false, length = 64)
    private String username; //username of Student or Company Reviewing the student 
    
    @Column(nullable = false, length = 256)
    @NotNull
    @Size(max = 256)
    private String reviewText;
    
    @Column(nullable = false)
    @NotNull
    private Integer rating;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private Project project; //company reviewing student after this project is done 

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Student studentReviewed; //student reviewed by another student  
   
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private Company company; //company reviewing the student 
    
    public StudentReview() {
    }

    public StudentReview(String reviewText, Integer rating) {
        this();
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public Long getStudentReviewId() {
        return studentReviewId;
    }
    

    public void setStudentReviewId(Long studentReviewId) {
        this.studentReviewId = studentReviewId;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentReviewId != null ? studentReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentReview)) {
            return false;
        }
        StudentReview other = (StudentReview) object;
        if ((this.studentReviewId == null && other.studentReviewId != null) || (this.studentReviewId != null && !this.studentReviewId.equals(other.studentReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StudentReview[ studentReviewId=" + studentReviewId + " ]";
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Student getStudentReviewed() {
        return studentReviewed;
    }

    public void setStudentReviewed(Student studentReviewed) {
        this.studentReviewed = studentReviewed;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
    
}
