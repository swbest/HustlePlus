/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Student;
import entity.Team;

/**
 *
 * @author dtjldamien
 */
public class RetrieveStudentRsp {

    public Student student;

    public RetrieveStudentRsp() {
    }

    public RetrieveStudentRsp(Student student) {
        this.student = student;
    }

    public RetrieveStudentRsp(Team team) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
