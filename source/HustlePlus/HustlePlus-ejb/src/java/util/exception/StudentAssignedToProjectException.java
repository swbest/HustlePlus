      
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author dtjldamien
 */
public class StudentAssignedToProjectException extends Exception {
    /**
     * Creates a new instance of <code>StudentNameExistException</code> without
     * detail message.
     */
    public StudentAssignedToProjectException() {
    }

    /**
     * Constructs an instance of <code>StudentNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StudentAssignedToProjectException(String msg) {
        super(msg);
    }
    
    
}


