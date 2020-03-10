/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
//import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;

/**
 *
 * @author amanda
 */
@Entity
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    //@NotNull
    //@Size(max = 30, message = "Team name should be less than 30 characters")
    @Column(nullable = false, length = 30)
    private String teamName;
    //@NotNull
    @Column(nullable = false)
    private int noStudents;
    //@OneToMany(mappedBy = "team")
    //private List<Student> students;
    //@OneToOne
    //private Project project;

    public Team() {
    }

    public Team(String teamName, int noStudents) {
        this.teamName = teamName;
        this.noStudents = noStudents;
    }
    
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the teamId fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Team[ id=" + teamId + " ]";
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the noStudents
     */
    public int getNoStudents() {
        return noStudents;
    }

    /**
     * @param noStudents the noStudents to set
     */
    public void setNoStudents(int noStudents) {
        this.noStudents = noStudents;
    }

    /**
     * @return the students
    
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    */
}
