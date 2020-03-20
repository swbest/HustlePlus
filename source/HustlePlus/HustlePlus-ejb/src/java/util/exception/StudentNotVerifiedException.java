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
public class StudentNotVerifiedException extends Exception {

    /**
     * Creates a new instance of <code>StudentNotVerifiedException</code>
     * without detail message.
     */
    public StudentNotVerifiedException() {
    }

    /**
     * Constructs an instance of <code>StudentNotVerifiedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public StudentNotVerifiedException(String msg) {
        super(msg);
    }
}
