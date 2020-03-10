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
public class CompanyNameExistException extends Exception {

    /**
     * Creates a new instance of <code>CompanyNameExistException</code> without
     * detail message.
     */
    public CompanyNameExistException() {
    }

    /**
     * Constructs an instance of <code>CompanyNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CompanyNameExistException(String msg) {
        super(msg);
    }
}
