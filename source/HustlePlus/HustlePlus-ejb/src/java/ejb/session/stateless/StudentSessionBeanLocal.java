/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StudentNameExistException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStudentException;

/**
 *
 * @author hiixdayah
 */
@Local
public interface StudentSessionBeanLocal {

    public Student createStudentAccount(Student newStudent) throws StudentNameExistException, UnknownPersistenceException, InputDataValidationException;

    public void updateStudent(Student student)  throws StudentNotFoundException, UpdateStudentException, InputDataValidationException;

    public void deleteStudentAccount(Long studentId) throws StudentNotFoundException;

    public List<Student> retrieveAllStudents();

    public Student retrieveStudentByStudentId(Long studentId) throws StudentNotFoundException;

    public Student retrieveStudentByUsername(String username) throws StudentNotFoundException;

    public Student studentLogin(String username, String password) throws InvalidLoginCredentialException;



}

