/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class StudentSessionBean implements StudentSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    public Long createStudentAccount(Student newStudent) {
        em.persist(newStudent);
        em.flush();
        return newStudent.getStudentId();
    }

    public Student retrieveStudentProfileByStudentId(Long studentId) { // throws MilestoneNotFoundException
        Student student = em.find(Student.class, studentId);

        if (student != null) {
            return student;
        }

        return student ;
        //else {
          //  throw new MilestoneNotFoundException("Milestone ID " + milestoneId + " does not exist!");
        }

    public void updateStudentProfile(Student student) {
        em.merge(student);
    }

    public void deleteStudentAccount(Long studentId)  { //throws MilestoneNotFoundException
        Student studentToRemove = retrieveStudentProfileByStudentId(studentId);
        em.remove(studentToRemove);
    }


    public List<Student> retrieveAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");

        return query.getResultList();

    }

}

