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
public class StudentSuspendedException extends Exception {

    /**
     * Creates a new instance of <code>StudentSuspendedException</code> without
     * detail message.
     */
    public StudentSuspendedException() {
    }

    /**
     * Constructs an instance of <code>StudentSuspendedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StudentSuspendedException(String msg) {
        super(msg);
    }
}
