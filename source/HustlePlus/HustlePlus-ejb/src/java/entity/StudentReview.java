/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/**
 *
 * @author sw_be
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class StudentReview extends Review implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    private Company company;

    public Long getStudentReviewId() {
        return reviewId;
    }

    public void setStudentReviewId(Long id) {
        this.reviewId = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentReview)) {
            return false;
        }
        StudentReview other = (StudentReview) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StudentReview[ id=" + reviewId + " ]";
    }
    
}
