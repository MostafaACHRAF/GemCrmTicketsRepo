package com.GemCrmTickets.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.GemCrmTickets.entities.Admin;
import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Company;
import com.GemCrmTickets.entities.Developer;
import com.GemCrmTickets.entities.Support;
import com.GemCrmTickets.metier.IAdmin;
import com.GemCrmTickets.metier.IAuthenticationFacade;
import com.GemCrmTickets.pagination.IPagination;
import com.GemCrmTickets.validators.AgentValidator;
import com.GemCrmTickets.validators.CompanyValidator;


@Controller

public class AdminController {
	
	
	/*------- attributs -------*/
	
	
	@Autowired
	@Resource(name = "admin")
	private IAdmin iAdmin;
	
	
	@Autowired
	private AgentValidator agentValidator;
	
	
	@Autowired
	private CompanyValidator companyValidator;
	
	
	@Autowired
	private IAuthenticationFacade iAuthenticationFacade;
	
	
	@Autowired
	private IPagination pagination;
	
	
	private final int pas = 5;
	
	
	/* func_num = 1 */
	/* get all agents */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{id}/show/agents", method = RequestMethod.GET)
	
	public String listAgents(Model model, @PathVariable int id, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "createSuccess", defaultValue = "") String createSuccess,
			@RequestParam(name = "updateSuccess", defaultValue = "") String updateSuccess, HttpServletRequest request) {
		
		Page<Agent> listAgents = iAdmin.listAgents(page, size);
		
		enableSuperPaginationSystem(model,  new int[listAgents.getTotalPages()], pas, page, size);
		
		
		model.addAttribute("listAgents", listAgents);
		
		if (!createSuccess.equals("")) {
			
			model.addAttribute("m_AgSuccessMsg", "Agent number " + createSuccess + " has been successfully created.");
			
		} else if (!updateSuccess.equals("")) {
			
			model.addAttribute("m_AgSuccessMsg", "Agent number " + updateSuccess + " has been successfully updated.");
			
		}
		
		initSessionAgentProfile(request);
		
		return "agents";
		
	}
	
	
	
	
	
	
	
	/* func_num = 2 */
	/* get all companies */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{id}/show/companies", method = RequestMethod.GET)
	
	public String listCompanies(Model model, @PathVariable int id, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "createSuccess", defaultValue = "") String createSuccess,
			@RequestParam(name = "updateSuccess", defaultValue = "") String updateSuccess, HttpServletRequest request) {
		
		Page<Company> companies = iAdmin.listCompanies(page, size);
		
		enableSuperPaginationSystem(model, new int[companies.getTotalPages()], pas, page, size);
		
		model.addAttribute("listCompanies", companies);
		
		if (!createSuccess.equals("")) {
			
			model.addAttribute("m_CompSuccess", "The company number " + createSuccess + " has been successfully created.");
			
		} else if (!updateSuccess.equals("")) {
			
			model.addAttribute("m_CompSuccess", "The company number " + updateSuccess + " has been successfully updated.");
			
		}
		
		initSessionAgentProfile(request);
		
		return "companies";
		
	}
	
	
	
	
	
	
	
	/* func_num = 3 */
	/* get all operations performed by an agent X */
	//verified
	
	/*@RequestMapping(value = "/admin/show/agentOperations", method = RequestMethod.GET)
	
	public String listAgentOperations(Model model, @RequestParam(name = "id") int agent_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		Page<Operation> agentOperations = iAdmin.listAgentOperations(agent_id, page, size);
		
		model.addAttribute("agent", iAdmin.findOneAgent(agent_id));
		
		model.addAttribute("listAgentOperations", agentOperations);
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("size", size);
		
		return "agents";
		
	}*/
	
	
	
	
	
	
	
	/* func_num = 4 */
	/* display agents form to update it */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{admin_id}/edit/{agent}/{agent_id}", method = RequestMethod.GET)
	
	public String showEditAgentForm(Model model, @PathVariable int admin_id, @PathVariable String agent,
			@PathVariable int agent_id, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		
		Page<Agent> listAgents = iAdmin.listAgents(page, size);
		
		enableSuperPaginationSystem(model, new int[listAgents.getTotalPages()], pas, page, size);
		
		model.addAttribute("listAgents", listAgents);
		
		model.addAttribute("agent", iAdmin.findOneAgent(agent_id));
		
		model.addAttribute("role", agent);
		
		model.addAttribute("action", "update");
		
		initSessionAgentProfile(request);

		return "agents";
		
	}
	
	
	
	
	
	
	/* edit agent to create a new one */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{admin_id}/edit/{agent}", method = RequestMethod.GET)
	
	public String showAddNewAgentForm(Model model, @PathVariable int admin_id, @PathVariable String agent,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		try {
			
			Agent a = (Agent) Class.forName("com.GemCrmTickets.entities." + StringUtils.capitalize(agent)).newInstance();
			
			Page<Agent> listAgents = iAdmin.listAgents(page, size);
			
			enableSuperPaginationSystem(model, new int [listAgents.getTotalPages()], pas, page, size);
			
			model.addAttribute("listAgents", listAgents);
			
			model.addAttribute("agent", a);
			
			model.addAttribute("role", agent);
			
			model.addAttribute("action", "create");
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			
			e.printStackTrace();
			
		}
		
		initSessionAgentProfile(request);
			
		return "agents";
		
	}
	
	
	
	
	
	
	
	/* func_num = 5 */
	/* create new developer account */
	/* update developer account */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/save/developer", method = RequestMethod.POST)
	
	public String saveDeveloperAccount(Model model, @PathVariable String admin_id, String confirmPassword, String action, String role,
			@ModelAttribute("agent") Developer developer, BindingResult result, int page, int size) {

		return saveAgentAccount(model, admin_id, confirmPassword, action, role, developer, result, page, size);
		
	}
	
	
	
	
	
	
	
	/* func_num = 6 */
	/* create new support account */
	/* update support account */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/save/support", method = RequestMethod.POST)
	
	public String saveSupportAccount(Model model, @PathVariable String admin_id, String confirmPassword, String action, 
			String role, @ModelAttribute("agent") Support support, BindingResult result, int page, int size) {
		
		return saveAgentAccount(model, admin_id, confirmPassword, action, role, support, result, page, size);
		
	}
	
	
	
	
	
	
	
	/* save administrator account */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/save/admin", method = RequestMethod.POST)
	
	public String saveAdminAccount(Model model, @PathVariable String admin_id, String confirmPassword, String action, String role, 
			@ModelAttribute("agent") Admin admin, BindingResult result, int page, int size) {
		
		return saveAgentAccount(model, admin_id, confirmPassword, action, role, admin, result, page, size);
		
	}
	
	
	
	
	
	
	/* This function create or update the agent account no matter how is an administrator, developer or support */
	//verified
	
	public String saveAgentAccount(Model model, String admin_id, String confirmPassword, String action, String role, Agent agent, 
			BindingResult result, int page, int size) {
		
		Agent admin = iAdmin.profile(Integer.parseInt(admin_id));
		
		String actionResult = "";
		
		agent.setConfirmPassword(confirmPassword);
		
		agentValidator.validate(agent, result);
		
		Page<Agent> listAgents = iAdmin.listAgents(page, size);
		
		enableSuperPaginationSystem(model, new int[listAgents.getTotalPages()], pas, page, size);
				
		if (result.hasErrors()) {
			
			model.addAttribute("listAgents", listAgents);
			
			model.addAttribute("role", role);
			
			model.addAttribute("action", action);
			
			return "agents";
			
		}
		
		if (action.equals("create")) {
			
			Agent a = iAdmin.createAgent(admin, agent);
			
			actionResult = "createSuccess=" + a.getId(); 
			
		} else {
			
			iAdmin.updateAgent(admin, agent);
			
			actionResult = "updateSuccess=" + agent.getId();
			
		}
		
		return "redirect:/admin/" + admin_id + "/show/agents?" + actionResult;
		
	}
	
	
	
	
	
	
	
	/* func_num = 7 */
	/* delete agent account */
	//verified
						
	@RequestMapping(value = "/admin/{admin_id}/delete/agent/{agent_id}", method = RequestMethod.GET)
	
	public String deleteAgentAccount(Model model, @PathVariable String admin_id, @PathVariable String agent_id) {
		
		try {
			
			iAdmin.deleteAgentAccount(iAdmin.profile(Integer.parseInt(admin_id)), Integer.parseInt(agent_id));
			
		} catch(Exception e) {
			
			model.addAttribute("error", e);
			
			return "agents";
			
		}
		
		return "redirect:/admin/" + admin_id + "/show/agents";
		
	}
	
	
	
	
	
	
	/* edit company to update it */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/edit/company/{company_id}", method = RequestMethod.GET)
	
	public String showEditCompanyForm(Model model, @PathVariable int admin_id, @PathVariable int company_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		Page<Company> listCompanies = iAdmin.listCompanies(page, size);
		
		model.addAttribute("listCompanies", listCompanies);
		
		enableSuperPaginationSystem(model, new int[listCompanies.getTotalPages()], pas, page, size);
		
		model.addAttribute("company", iAdmin.findOneCompany(company_id));

		model.addAttribute("action", "update");
		
		initSessionAgentProfile(request);
		
		return "companies";
	
	}
	
	
	
	
	
	
	
	/* edit company to create a new one */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{admin_id}/edit/company", method = RequestMethod.GET)
	
	
	public String showAddCompanyForm(Model model, @PathVariable String admin_id, 
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request){
		
		
		Page<Company> listCompanies = iAdmin.listCompanies(page, size);
		
		enableSuperPaginationSystem(model, new int[listCompanies.getTotalPages()], pas, page, size);
		
		model.addAttribute("company", new Company());
		
		model.addAttribute("listCompanies", listCompanies);
		
		model.addAttribute("action", "create");
		
		initSessionAgentProfile(request);
		
		return "companies";
		
	}
	
	
	
	
	
	
	
	/* save company create or update */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/save/company", method = RequestMethod.POST)
	
	public String saveCompany(Model model, @PathVariable String admin_id, String action,
			@ModelAttribute("company") Company company, BindingResult result,
			@RequestParam(name = "page", defaultValue="0") int page,
			@RequestParam(name = "size" , defaultValue="5") int size) {
		
		Agent byAdmin = iAdmin.profile(Integer.parseInt(admin_id));
		
		String actionResult = "";
		
		Page<Company> listCompanies = iAdmin.listCompanies(page, size);
		
		enableSuperPaginationSystem(model, new int[listCompanies.getTotalPages()], pas, page, size);
		
		companyValidator.validate(company, result);
		
		if (result.hasErrors()) {
			
			model.addAttribute("company", company);
			
			model.addAttribute("action", action);
			
			model.addAttribute("listCompanies", listCompanies);
			
			return "companies";
			
		}
		
		if (action.equals("create")) {
			
			Company c = iAdmin.createCompany(byAdmin, company);
			
			actionResult = "createSuccess=" + c.getId();
			
		} else {
			
			iAdmin.updateCompany(byAdmin, company);
			
			actionResult = "updateSuccess=" + company.getId();
			
		}
		
		return "redirect:/admin/" + byAdmin.getId() + "/show/companies?" + actionResult;
		
	}
	
	
	
	
	
	
	
	/* func_num = 10 */
	/* delete component company  or ticket */
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/delete/{component}/{component_id}", method = RequestMethod.GET)
	
	String deleteCompany(Model model, @PathVariable String admin_id, @PathVariable String component, @PathVariable String component_id,
			@RequestParam(name = "page", defaultValue = "0") String page,
			@RequestParam(name = "size", defaultValue = "7") String size) {
		
		if (component.equals("ticket")) {
			
			iAdmin.deleteTicket(iAdmin.profile(Integer.parseInt(admin_id)), Integer.parseInt(component_id));
			
			return "redirect:/agent/" + admin_id + "/show/tickets";
			
		}
			
		iAdmin.deleteCompany(iAdmin.profile(Integer.parseInt(admin_id)), Integer.parseInt(component_id));
		
		return "redirect:/admin/" + admin_id + "/show/companies";
		
	}
	
	
	
	
	
	
	
	/* func_num = 12*/
	/* search agents */
	//
	
	@RequestMapping(value = "/search/agents", method = RequestMethod.GET)
	
	public String searchAgents(Model model, @RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		

		Page<Agent> listAgents = iAdmin.searchAgents(name, page, size);
		
		enableSuperPaginationSystem(model, new int [listAgents.getTotalPages()], pas, page, size);
			
		model.addAttribute("listAgents", listAgents);
	
		initSessionAgentProfile(request);
		
		return "agents";
		
	}
	
	
	
	
	
	
	
	/* func_num = 13 */
	/* search company */
	//verified
	//used
	
	@RequestMapping(value = "/search/companies", method = RequestMethod.GET)
	
	public String searchCompanies(Model model, @RequestParam(name = "name", defaultValue = "") String companyName,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		Page<Company> listCompanies = iAdmin.searchCompanies(companyName, page, size);
		
		enableSuperPaginationSystem(model, new int[listCompanies.getTotalPages()], pas, page, size);
		
		model.addAttribute("listCompanies", listCompanies);
		 	
		initSessionAgentProfile(request);
				
		return "companies";
		
	}
	
	
	
	
	
	
	
	/* func_num = 14 */
	/* consult an agent profile */
	//
	
	@RequestMapping(value = "/admin/{admin_id}/show/agent/{agent_id}", method = RequestMethod.GET)
	
	public String showAgentProfile(Model model, @PathVariable String admin_id, @PathVariable String agent_id) {
		
		try {
			
			model.addAttribute("agent", iAdmin.findOneAgent(Integer.parseInt(agent_id)));
			
		} catch(Exception e) {
			
			model.addAttribute("error", e);
		}
		
		return "agents";
		
	}
	
	
	
	
	
	
	
	/* func_num = 15 */
	/* consult a company details*/
	//verified
	
	@RequestMapping(value = "/admin/{admin_id}/show/company/{company_id}/details", method = RequestMethod.GET)
	
	public String showCompanyDetails(Model model, @PathVariable int admin_id, @PathVariable int company_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		
		Page<Company> listCompanies = iAdmin.listCompanies(page, size);
		
		enableSuperPaginationSystem(model, new int[listCompanies.getTotalPages()], pas, page, size);
		
		model.addAttribute("company", iAdmin.findOneCompany(company_id));
		
		model.addAttribute("listCompanies", listCompanies);

		model.addAttribute("action", "show Details of");
		
		initSessionAgentProfile(request);
		
		return "companies";
		
	}
	
	
	
	
	
	
	
	/* show agent X details */
	//verified
	//used
	
	@RequestMapping(value = "/admin/{admin_id}/show/agent/{agent_id}/details", method = RequestMethod.GET)
	
	public String showAgentDetails(Model model, @PathVariable int admin_id, @PathVariable int agent_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request) {
		
		Page<Agent> listAgents = iAdmin.listAgents(page, size);
		
		enableSuperPaginationSystem(model, new int[listAgents.getTotalPages()], pas, page, size);
		
		model.addAttribute("listAgents", listAgents);
		
		model.addAttribute("agent", iAdmin.findOneAgent(agent_id));
		
		model.addAttribute("agentOperations", iAdmin.listAgentOperations(agent_id, page, size));
		
		model.addAttribute("action", "show Details of");
		
		initSessionAgentProfile(request);
		
		return "agents";
		
	}
	
	
	
	
	
	
	
	/* enable pagination */
	//verified
	//used
	
	public void enableSuperPaginationSystem(Model model, int[] pages, int pas, int page, int size) {
		
		int[] subPages = pagination.getPaginationPages(pages, pas, page);
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("size", size);
		
		model.addAttribute("pages", subPages);
		
		model.addAttribute("taille", pages.length);
		
	}
	
	
	
	
	
	
	
	public void initSessionAgentProfile(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("agentProfile") == null) {
			
			System.out.println("agentProfile null form agentController line 694");
			
			Authentication authentication = iAuthenticationFacade.getAuthentication();
			
			Agent agentProfile = iAdmin.profile(authentication.getName());
			
			request.getSession().setAttribute("agentProfile", agentProfile);
		}
		
	}
	
	
}
