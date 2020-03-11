
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

public class StudentNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>StudentNotFoundException</code>
     * without detail message.
     */
    public StudentNotFoundException() {
    }

    /**
     * Constructs an instance of <code>StudentNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}

