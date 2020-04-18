/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Student;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
