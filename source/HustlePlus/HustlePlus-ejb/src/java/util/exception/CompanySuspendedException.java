/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author sw_be
 */
public class CompanySuspendedException extends Exception {

    /**
     * Creates a new instance of <code>CompanySuspendedException</code> without
     * detail message.
     */
    public CompanySuspendedException() {
    }

    /**
     * Constructs an instance of <code>CompanySuspendedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CompanySuspendedException(String msg) {
        super(msg);
    }
}
