/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteStudentException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StudentNameExistException;
import util.exception.StudentNotFoundException;
import util.exception.SuspendStudentException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStudentException;
import util.exception.VerifyStudentException;

/**
 *
 * @author hiixdayah
 */
@Local
public interface StudentSessionBeanLocal {

    public Long createStudentAccount(Student newStudent) throws StudentNameExistException, UnknownPersistenceException, InputDataValidationException;

    public void updateStudent(Student student)  throws StudentNotFoundException, UpdateStudentException, InputDataValidationException;

    public void deleteStudentAccount(Long studentId) throws StudentNotFoundException, DeleteStudentException;

    public List<Student> retrieveAllStudents();

    public Student retrieveStudentByStudentId(Long studentId) throws StudentNotFoundException;

    public Student retrieveStudentByUsername(String username) throws StudentNotFoundException;

    public Student studentLogin(String username, String password) throws InvalidLoginCredentialException;

    public List<Student> retrieveStudentsBySkills(String skill)throws StudentNotFoundException;

    public List<Student> retrieveStudentsByAvgRating(Double avgRating) throws StudentNotFoundException;

    public List retrieveStudentsByName(String name) throws StudentNotFoundException;

    public List<Student> filterStudentsBySkills(List<Long> skillIds);

    public void verifyStudent(Long studentId) throws StudentNotFoundException, VerifyStudentException;

    public void suspendStudent(Long studentId) throws StudentNotFoundException, SuspendStudentException;

    public List<Student> searchStudentsByName(String searchString);

}