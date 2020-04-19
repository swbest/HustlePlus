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
public class SuspendStudentException extends Exception {

    /**
     * Creates a new instance of <code>SuspendStudentException</code> without
     * detail message.
     */
    public SuspendStudentException() {
    }

    /**
     * Constructs an instance of <code>SuspendStudentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SuspendStudentException(String msg) {
        super(msg);
    }
}
