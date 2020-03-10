/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private Integer noStudentsRequired;
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
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private Company company;
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Team team;
    @OneToMany(mappedBy = "milestone")
    private List<Milestone> milestones;
    @OneToMany(mappedBy = "review")
    private List<Review> reviews;
    

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
