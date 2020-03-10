/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dtjldamien
 */
@Entity
public class AdminStaff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminStaffId;
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false, unique = true, length = 64)
    private String username;
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false)
    private String password;

    public AdminStaff(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getAdminStaffId() {
        return adminStaffId;
    }

    public void setAdminStaffId(Long adminStaffId) {
        this.adminStaffId = adminStaffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminStaffId != null ? adminStaffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the adminStaffId fields are not set
        if (!(object instanceof AdminStaff)) {
            return false;
        }
        AdminStaff other = (AdminStaff) object;
        if ((this.adminStaffId == null && other.adminStaffId != null) || (this.adminStaffId != null && !this.adminStaffId.equals(other.adminStaffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AdminStaff[ id=" + adminStaffId + " ]";
    }

}
