/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author amand
 */
public class TeamNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TeamNotFoundException</code> without
     * detail message.
     */
    public TeamNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TeamNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TeamNotFoundException(String msg) {
        super(msg);
    }
}
