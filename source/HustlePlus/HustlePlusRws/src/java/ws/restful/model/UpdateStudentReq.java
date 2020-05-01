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
public class UpdateStudentReq {
    
    private String username;
    private String password;
    private Student student;
    
    public UpdateStudentReq(){        
    }
   
    public UpdateStudentReq(String username, String password, Student student) {
        this.username = username;
        this.password = password;
        this.student = student;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}