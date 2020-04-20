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
public class VerifyStudentException extends Exception {

    /**
     * Creates a new instance of <code>VerifyStudentException</code> without
     * detail message.
     */
    public VerifyStudentException() {
    }

    /**
     * Constructs an instance of <code>VerifyStudentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VerifyStudentException(String msg) {
        super(msg);
    }
}
