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
public class UpdateAdminStaffException extends Exception {

    /**
     * Creates a new instance of <code>UpdateAdminStaffException</code> without
     * detail message.
     */
    public UpdateAdminStaffException() {
    }

    /**
     * Constructs an instance of <code>UpdateAdminStaffException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateAdminStaffException(String msg) {
        super(msg);
    }
}
