

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import entity.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.enumeration.AccessRightEnum;

/**
 *
 * @author Nurhidayah
 */

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})

public class SecurityFilter implements Filter {
    
    FilterConfig filterConfig;
    
    private static final String CONTEXT_ROOT = "/HustlePlus-war";
    
    @Override
        public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();
        
        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }
        
        Boolean isLogin = (Boolean)httpSession.getAttribute("isLogin");
        
        if(!excludeLoginCheck(requestServletPath)) {
            if(isLogin == true) {
                User userEntity = (User)httpSession.getAttribute("userEntity");
            
            
            if (checkAccessRight(requestServletPath, userEntity.getAccessRightEnum())) {
                chain.doFilter(request,response);
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
            }
        } else {
            httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
        }
    } else {
            chain.doFilter(request,response);
        }
    }
    
    public Boolean checkAccessRight(String path, AccessRightEnum accessRight) {
        if(accessRight.equals(AccessRightEnum.ADMIN)) {
            if (path.equals("/admin/verifyCompany.xhtml") ||
            path.equals("/admin/verifyStudent.xhtml") ||
            path.equals("/admin/sendEmailToUser.xhtml") ||
            path.equals("/companies/allReviewsOfStudent.xhtml") ||
            path.equals("/companies/allReviewsOfCompany.xhtml") ||
            path.equals("/companies/filterStudentsBySkills.xhtml") ||
            path.equals("/companies/searchStudentsByName.xhtml") ||
            path.equals("/companies/searchCompaniesByName.xhtml") ||
            path.equals("/companies/filterCompaniesByRating.xhtml") ||
            path.equals("/companies/filterProjectsByCompany.xhtml") ||
            path.equals("/companies/searchCompaniesByRating.xhtml") ||
            path.equals("/companies/searchProjectsByCompany.xhtml") ||
            path.equals("/companies/searchProjectsByName.xhtml")) {
                return true ; 
            } else {
                return false ;
            }
        } else if (accessRight.equals(AccessRightEnum.COMPANY)) {
            if (path.equals("/verificationError.xhtml") ||
            path.equals("/changePassword.xhtml") ||
            path.equals("/companies/allReviewsOfStudent.xhtml") ||
            path.equals("/companies/reviewStudentInProject.xhtml") ||
            path.equals("/companies/companyReviews.xhtml") ||
            path.equals("/companies/projectReviews.xhtml") ||
            path.equals("/companies/emailPage.xhtml") ||
            path.equals("/companies/paymentsForProject.xhtml") ||
            path.equals("/companies/emailAdmin.xhtml") ||
            path.equals("/companies/emailStudent.xhtml") ||
            path.equals("/companies/resendEmail.xhtml") ||
            path.equals("/companies/milestoneForProject.xhtml") ||
            path.equals("/companies/skillsOfProject.xhtml") ||
            path.equals("/companies/reviewsByCompany.xhtml") ||
            path.equals("/companies/projectManagement.xhtml") ||
            path.equals("/companies/reviewsOfStudentInOtherProjects.xhtml") ||
            path.equals("/companies/myProject.xhtml") ||
            path.equals("/companies/projectReviewsForOtherProjects.xhtml") ||      
            path.equals("/companies/createProjectForProjectManagement.xhtml") ||
            path.equals("/companies/addMilestoneToProject.xhtml") ||
            path.equals("/companies/createNewMilestone.xhtml") ||
            path.equals("/companies/addASkill.xhtml") ||
            path.equals("/companies/allReviewsOfCompany.xhtml") ||
            path.equals("/companies/addASkillProjectManagement.xhtml") ||
            path.equals("/companies/profilePage.xhtml") ||
            path.equals("/companies/filterStudentsBySkills.xhtml") ||
            path.equals("/companies/searchStudentsByName.xhtml") ||
            path.equals("/companies/searchCompaniesByRating.xhtml") ||
            path.equals("/companies/searchCompaniesByName.xhtml") ||
            path.equals("/companies/filterCompaniesByRating.xhtml") ||
            path.equals("/companies/filterProjectsByCompany.xhtml") ||
            path.equals("/companies/searchProjectsByName.xhtml") ||
            path.equals("/companies/searchProjectsByCompany.xhtml") ||
            path.equals("/companies/milestoneManagement.xhtml") ||
            path.equals("/companies/createNewProject.xhtml") ||
            path.equals("/companies/viewApplications.xhtml") ||
            path.equals("/companies/viewMilestones.xhtml") ||
            path.equals("/companies/viewTeam.xhtml") ||
            path.equals("/companies/reviewsOfStudentInProject.xhtml") ||
            path.equals("/companies/reviewStudent.xhtml") ||
            path.equals("/companies/profilePage.xhtml")  ||
            path.startsWith("/uploadedFiles"))
            {
                return true ; 
            } else {
                return false ;
            }
    } 
            return false ;
        }
    
    private Boolean excludeLoginCheck(String path) {
        
        if (path.equals("/index.xhtml") ||
            path.equals("/accessRightError.xhtml") ||
            path.equals("/changeForgottenPassword.xhtml") ||
            path.equals("/companies/createNewCompany.xhtml") ||
            path.equals("/login.xhtml") ||
            path.equals("/companies/emailVerification.xhtml") ||
            path.startsWith("/javax.faces.resource") ||
            path.startsWith("/resources")
            )  {
                return true ; 
            } else {
                return false ;
            }
    } 

    @Override
    public void destroy() {
    }

    
}
