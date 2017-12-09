package com.GemCrmTickets.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.metier.IAdmin;
import com.GemCrmTickets.metier.IAuthenticationFacade;


@Controller

public class SecurityController {
	
	
	@Autowired
	private IAuthenticationFacade iAuthenticationFacade;

	@Autowired
	private IAdmin iAdmin;
	
	
	
	
	
	
	@RequestMapping(value = "/login")
	
	public String displayLoginPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if(request.getParameter("logout") != null) {
			
		    CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		    
		    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		    
		    cookieClearingLogoutHandler.logout(request, response, null);
		    
		    securityContextLogoutHandler.logout(request, response, null);
		    
		}
		
		return "login";
		
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/")
	
	public String displayHomePage(HttpServletRequest request) {
		
		Authentication authentication = iAuthenticationFacade.getAuthentication();
		
		String username = authentication.getName();
		
		System.out.println("user : " + username + " from security controller line 72");
		
		Agent agentProfile = iAdmin.profile(username);
		
		request.getSession().setAttribute("agentProfile", agentProfile); /*anonymousUser*/
		
		if (agentProfile != null) {
			
			return "redirect:/agent/" + agentProfile.getId() + "/show/tickets";
			
		}
		
		return "login";
		
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/403")
	
	public String displayAccessDeniedPage() {
		
		return "/403";
		
	}
}
