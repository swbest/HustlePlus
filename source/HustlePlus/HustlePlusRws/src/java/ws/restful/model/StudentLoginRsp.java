/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Student;

/**
 *
 * @author sw_be
 */
public class StudentLoginRsp {

    private Student student;

    public StudentLoginRsp() {
    }

    public StudentLoginRsp(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
