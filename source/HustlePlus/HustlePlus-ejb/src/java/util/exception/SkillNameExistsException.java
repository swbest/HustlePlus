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
public class SkillNameExistsException extends Exception {

    /**
     * Creates a new instance of <code>SkillNameExistException</code> without
     * detail message.
     */
    public SkillNameExistsException() {
    }

    /**
     * Constructs an instance of <code>SkillNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SkillNameExistsException(String msg) {
        super(msg);
    }
}
