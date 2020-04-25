/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author dtjldamien
 */
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class AdminStaff extends User implements Serializable {


    public AdminStaff() {
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

}
