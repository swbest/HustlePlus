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
public class SuspendCompanyException extends Exception {

    /**
     * Creates a new instance of <code>SuspendCompanyException</code> without
     * detail message.
     */
    public SuspendCompanyException() {
    }

    /**
     * Constructs an instance of <code>SuspendCompanyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SuspendCompanyException(String msg) {
        super(msg);
    }
}
