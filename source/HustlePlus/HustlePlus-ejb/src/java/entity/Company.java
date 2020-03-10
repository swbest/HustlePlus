/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.File;
import java.io.Serializable;
//import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;

/**
 *
 * @author amanda
 */
@Entity
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    //@NotNull
    //@Size(max = 30, message = "Name should be at most 30 characters")
    @Column(nullable = false, length = 30)
    private String name;
    //@NotNull
    @Column(nullable = false, length = 256)
    private String password;
    //@NotNull
    @Column(nullable = false)
    private File icon;
    //@NotNull
    //@Size(max = 256, message = "Company description should be at most 256 characters")
    @Column(nullable = false, length = 256)
    private String description;
    //@NotNull
    //@Email(message = "Email should be valid")
    @Column(nullable = false, length = 30)
    private String email;
    //@NotNull
    //@Min(value = 0.0, message = "Average rating should not be less than 0.0")
    //@Max(value = 5.0, message = "Average rating should not be more than 5.0")
    @Column(nullable = false)
    private double avgRating;
    //@OneToMany(mappedBy = "company")
    //private List<Project> projects;
    //@OneToOne
    //private StudentReview studentReview;

    public Company() {
    }

    public Company(String name, String password, File icon, String description, String email) {
        this.name = name;
        this.password = password;
        this.icon = icon;
        this.description = description;
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the companyId fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Company[ id=" + companyId + " ]";
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the icon
     */
    public File getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(File icon) {
        this.icon = icon;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the avgRating
     */
    public double getAvgRating() {
        return avgRating;
    }

    /**
     * @param avgRating the avgRating to set
     */
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
