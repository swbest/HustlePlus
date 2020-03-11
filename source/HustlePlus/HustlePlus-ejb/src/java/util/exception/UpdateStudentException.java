
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Nurhidayah
 */

public class UpdateStudentException extends Exception {

    /**
     * Creates a new instance of <code>UpdateStudentException</code>
     * without detail message.
     */
    public UpdateStudentException() {
    }

    /**
     * Constructs an instance of <code>UpdateStudentException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateStudentException(String msg) {
        super(msg);
    }
}

