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
public class CreateNewStudentReq {

    private Student newStudent;

    public CreateNewStudentReq() {
    }

    public CreateNewStudentReq(Student newStudent) {
        this.newStudent = newStudent;
    }

    public Student getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(Student newStudent) {
        this.newStudent = newStudent;
    }

}
