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
public class UpdateCompanyException extends Exception {

    /**
     * Creates a new instance of <code>UpdateCompanyException</code> without
     * detail message.
     */
    public UpdateCompanyException() {
    }

    /**
     * Constructs an instance of <code>UpdateCompanyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateCompanyException(String msg) {
        super(msg);
    }
}
