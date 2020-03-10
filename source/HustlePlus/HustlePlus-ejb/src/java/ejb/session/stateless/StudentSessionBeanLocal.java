/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hiixdayah
 */
@Local
public interface StudentSessionBeanLocal {

    public Long createStudentAccount(Student newStudent);

    public void updateStudentProfile(Student student);

    public void deleteStudentAccount(Long studentId);

    public List<Student> retrieveAllStudents();

    public Student retrieveStudentProfileByStudentId(Long studentId);



}

