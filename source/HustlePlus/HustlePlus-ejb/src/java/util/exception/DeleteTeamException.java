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
public class DeleteTeamException extends Exception {

    /**
     * Creates a new instance of <code>DeleteTeamException</code> without detail
     * message.
     */
    public DeleteTeamException() {
    }

    /**
     * Constructs an instance of <code>DeleteTeamException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteTeamException(String msg) {
        super(msg);
    }
}
