/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Student;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveAllStudentsRsp {

    public List<Student> students;

    public RetrieveAllStudentsRsp() {
    }

    public RetrieveAllStudentsRsp(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
