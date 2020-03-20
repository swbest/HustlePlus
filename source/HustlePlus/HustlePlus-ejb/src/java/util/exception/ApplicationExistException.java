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
public class ApplicationExistException extends Exception {

    /**
     * Creates a new instance of <code>ApplicationExistException</code> without
     * detail message.
     */
    public ApplicationExistException() {
    }

    /**
     * Constructs an instance of <code>ApplicationExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ApplicationExistException(String msg) {
        super(msg);
    }
}
