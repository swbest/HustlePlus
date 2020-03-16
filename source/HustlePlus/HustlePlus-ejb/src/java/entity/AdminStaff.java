/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dtjldamien
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AdminStaff extends User implements Serializable {

    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false, length = 64)
    private String name;

    public AdminStaff() {
    }

    public AdminStaff(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the adminStaffId fields are not set
        if (!(object instanceof AdminStaff)) {
            return false;
        }
        AdminStaff other = (AdminStaff) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AdminStaff[ id=" + userId + " ]";
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
