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
public class CompanyNotVerifiedException extends Exception {

    /**
     * Creates a new instance of <code>CompanyNotVerifiedException</code>
     * without detail message.
     */
    public CompanyNotVerifiedException() {
    }

    /**
     * Constructs an instance of <code>CompanyNotVerifiedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CompanyNotVerifiedException(String msg) {
        super(msg);
    }
}
