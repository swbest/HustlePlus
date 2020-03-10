/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author hiixdayah
 */
@Entity
public class Milestone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;
    private String description;
    private Boolean hasCleared;

    //@OneToMany(mappedBy="milestone", fetch = FetchType.LAZY)
    //private List<PaymentEntity> payments;

    //@OneToOne(optional = false)
    //private ProjectEntity projectId;

    public Milestone() {
    }

    public Milestone(String description, Boolean hasCleared) {
        this.description = description;
        this.hasCleared = hasCleared;
    }


    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (milestoneId != null ? milestoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the milestoneId fields are not set
        if (!(object instanceof Milestone)) {
            return false;
        }
        Milestone other = (Milestone) object;
        if ((this.milestoneId == null && other.milestoneId != null) || (this.milestoneId != null && !this.milestoneId.equals(other.milestoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Milestone[ id=" + milestoneId + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasCleared() {
        return hasCleared;
    }

    public void setHasCleared(Boolean hasCleared) {
        this.hasCleared = hasCleared;
    }

}
