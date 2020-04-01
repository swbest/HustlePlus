/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AdminStaff;
import java.util.List;
import util.exception.AdminStaffNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAdminStaffException;
import util.exception.UserEmailExistsException;

/**
 *
 * @author dtjldamien
 */
public interface AdminStaffSessionBeanLocal {

    public Long createNewAdminStaff(AdminStaff newAdminStaff) throws UserEmailExistsException, UnknownPersistenceException, InputDataValidationException;

    public List<AdminStaff> retrieveAllAdminStaff();

    public AdminStaff retrieveAdminStaffByAdminStaffId(Long adminStaffId) throws AdminStaffNotFoundException;

    public void deleteAdminStaff(Long adminStafffId) throws AdminStaffNotFoundException;

    public void updateAdminStaff(AdminStaff adminStaff) throws AdminStaffNotFoundException, UpdateAdminStaffException, InputDataValidationException;

    public AdminStaff retrieveAdminStaffByUsername(String username) throws AdminStaffNotFoundException;

    public AdminStaff adminStaffLogin(String username, String password) throws InvalidLoginCredentialException;

}
