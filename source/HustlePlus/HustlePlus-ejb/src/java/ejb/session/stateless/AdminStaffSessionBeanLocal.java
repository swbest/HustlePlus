/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AdminStaff;
import java.util.List;
import javax.ejb.Local;
import util.exception.AdminStaffNameExistException;
import util.exception.AdminStaffNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAdminStaffException;

/**
 *
 * @author dtjldamien
 */
public interface AdminStaffSessionBeanLocal {

    public Long createNewAdminStaff(AdminStaff newAdminStaff) throws AdminStaffNameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<AdminStaff> retrieveAllAdminStaff();

    public AdminStaff retrieveAdminStaffByAdminStaffId(Long adminStaffId) throws AdminStaffNotFoundException;

    public void deleteAdminStaff(Long adminStafffId) throws AdminStaffNotFoundException;

    public void updateAdminStaff(AdminStaff adminStaff) throws AdminStaffNotFoundException, UpdateAdminStaffException, InputDataValidationException;
    
}
