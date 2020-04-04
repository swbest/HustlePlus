

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
            /*if () {
                return true ; 
            } else {
                return false ;
            }*/
        } else if (accessRight.equals(AccessRightEnum.COMPANY)) {
            /*if ()  {
                return true ; 
            } else {
                return false ;
            }*/
    } 
            return false ;
        }
    
    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml") ||
            path.equals("/admin/verifyCompany.xhtml") ||
            path.equals("/admin/suspendCompany.xhtml") ||
            path.equals("/companies/createNewCompany.xhtml") ||
            path.equals("/accessRightError.xhtml") ||
                path.equals("/companies/profilePage.xhtml") ||
                path.equals("/companies/filterStudentsBySkills.xhtml") ||
                path.equals("/companies/searchStudentsByName.xhtml") ||
                path.equals("/companies/searchCompaniesByName.xhtml") ||
                path.equals("/companies/filterCompaniesByRating.xhtml") ||
                path.equals("/companies/filterProjectsByCompany.xhtml") ||
                path.equals("/companies/searchProjectsByName.xhtml") ||
                path.equals("/admin/suspendCompany.xhtml") ||
                path.equals("/companies/milestoneManagement.xhtml") ||
                path.equals("/companies/createNewProject.xhtml") ||
                path.equals("/companies/createNewCompany.xhtml") ||
                path.equals("/companies/profilePage.xhtml") ||
            path.startsWith("/javax.faces.resource")
                ) {
            return true ;
        } else {
            return false ;
        }
    }
   


    @Override
    public void destroy() {
    }

    
}
