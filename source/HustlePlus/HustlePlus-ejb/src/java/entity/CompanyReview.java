/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author sw_be
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CompanyReview extends Review implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long getCompanyReviewId() {
        return reviewId;
    }

    public void setCompanyReviewId(Long id) {
        this.reviewId = id;
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
        if (!(object instanceof CompanyReview)) {
            return false;
        }
        CompanyReview other = (CompanyReview) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompanyReview[ id=" + reviewId + " ]";
    }
}
