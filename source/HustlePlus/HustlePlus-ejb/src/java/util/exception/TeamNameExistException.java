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
public class TeamNameExistException extends Exception {

    /**
     * Creates a new instance of <code>TeamNameExistException</code> without
     * detail message.
     */
    public TeamNameExistException() {
    }

    /**
     * Constructs an instance of <code>TeamNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TeamNameExistException(String msg) {
        super(msg);
    }
}
