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
public class VerifyCompanyException extends Exception {

    /**
     * Creates a new instance of <code>VerifyCompanyException</code> without
     * detail message.
     */
    public VerifyCompanyException() {
    }

    /**
     * Constructs an instance of <code>VerifyCompanyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VerifyCompanyException(String msg) {
        super(msg);
    }
}
