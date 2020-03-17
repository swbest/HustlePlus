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
public class DeleteSkillException extends Exception {

    /**
     * Creates a new instance of <code>DeleteSkillException</code> without
     * detail message.
     */
    public DeleteSkillException() {
    }

    /**
     * Constructs an instance of <code>DeleteSkillException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteSkillException(String msg) {
        super(msg);
    }
}
