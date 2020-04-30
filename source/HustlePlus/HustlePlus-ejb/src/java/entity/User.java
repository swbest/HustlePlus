/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import util.enumeration.AccessRightEnum;
import util.security.CryptographicHelper;

/**
 *
 * @author dtjldamien
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false, length = 64)
    protected String name;
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false, unique = true, length = 64)
    protected String username;
    @NotNull
    @Size(min = 6, max = 64)
    @Column(nullable = false)
    protected String password;
    @Column(nullable = true)
    protected String icon;
    @NotNull
    @Column(nullable = false, length = 64, unique = true)
    @Size(max = 64)
    protected String email;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    protected String salt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true) // changed to true for students
    protected AccessRightEnum accessRightEnum;

    public User() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public User(String name, String username, String password, String email, AccessRightEnum accessRightEnum) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accessRightEnum = accessRightEnum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        } else {
            this.password = null;
        }
    }
   
   @XmlTransient
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User.java[ id=" + userId + " ]";
    }

    public AccessRightEnum getAccessRightEnum() {
        return accessRightEnum;
    }

    public void setAccessRightEnum(AccessRightEnum accessRightEnum) {
        this.accessRightEnum = accessRightEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
