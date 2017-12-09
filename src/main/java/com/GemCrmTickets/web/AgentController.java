package com.GemCrmTickets.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.GemCrmTickets.dao.AgentRepository;
import com.GemCrmTickets.dao.CompanyRepository;
import com.GemCrmTickets.entities.Admin;
import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Developer;
import com.GemCrmTickets.entities.Support;
import com.GemCrmTickets.entities.Ticket;
import com.GemCrmTickets.metier.IAgent;
import com.GemCrmTickets.metier.IAuthenticationFacade;
import com.GemCrmTickets.pagination.IPagination;
import com.GemCrmTickets.validators.AgentValidator;
import com.GemCrmTickets.validators.TicketValidator;

@Controller

public class AgentController {

	
	/*------- attributs -------*/
	
	
	@Autowired
	@Resource(name = "agent")
	private IAgent iAgent;
	
	
	@Autowired AgentRepository agentRepository;
	
	
	@Autowired
	CompanyRepository companyRepository;
	
	
	@Autowired
	TicketValidator ticketValidator;
	
	
	@Autowired
	private AgentValidator agentValidator;
	
	
	@Autowired
	private IAuthenticationFacade iAuthenticationFacade;
	
	
	@Autowired
	private IPagination pagination;
	
	
	private final int pas = 5;
	
	
	
	/* func_num = 1 */
	/* get all tickets */
	//verified
	//used
	//improved
	
	@RequestMapping(value = "/agent/{id}/show/tickets", method = RequestMethod.GET)
	
	public String getlistTickets(Model model, @PathVariable int id,
			@RequestParam(name = "createSuccess", defaultValue = "") String createSuccess,
			@RequestParam(name = "updateSuccess", defaultValue = "") String updateSuccess,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		enableSuperPaginationSystem(model, new int [listTickets.getTotalPages()], pas, page, size);
		
		model.addAttribute("listTickets", listTickets);
		
		if (!createSuccess.equals("")) {
			
			model.addAttribute("m_tickSuccessMsg", "Ticket number " + createSuccess + " has been successfully created !");
			
		} else if (!updateSuccess.equals("")) {
			
			model.addAttribute("m_tickSuccessMsg", "Ticket number " + updateSuccess + " has been successfully updated !");
			
		}
		
		
		initSessionAgentProfile(request);
		
		model.addAttribute("agent", iAgent.profile(id));
		
		return "tickets";
		
	}
	
	
	
	
	
	
	
	/* func_num = 2 */
	/* get profile informations */
	
	@RequestMapping(value = "/agent/{id}/profile", method = RequestMethod.GET)
	
	public String getProfile(Model model, @PathVariable(name = "id") int id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			HttpServletRequest request) {

		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		enableSuperPaginationSystem(model, new int[listTickets.getTotalPages()], pas, page, size);
		
		model.addAttribute("agent", iAgent.profile(id));
		
		model.addAttribute("listTickets", listTickets);
		
		model.addAttribute("action", "Show Profile of");
		
		initSessionAgentProfile(request);
		
		return "tickets";
		
	}
	
	
	
	
	
	
	
	/* func_num = 3 */
	/* update developer profile */
	
	@RequestMapping(value = "/agent/{id}/save/developer/profile", method = RequestMethod.POST)
	
	public String saveDeveloperProfile(Model model, @PathVariable int id, @ModelAttribute("agent") Developer developer, BindingResult result, int page, int size, RedirectAttributes redirectAttributes) {
		
		return saveAgentProfile(model, "developer", developer, result, page, size, redirectAttributes);
		
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/agent/{id}/save/support/profile", method = RequestMethod.POST)
	
	public String saveSupportProfile(Model model, @PathVariable int id, @ModelAttribute("agent") Support support, BindingResult result, int page, int size, RedirectAttributes redirectAttributes) {
		
		return saveAgentProfile(model, "support", support, result, page, size, redirectAttributes);
		
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/agent/{id}/save/admin/profile", method = RequestMethod.POST)
	
	public String saveAdminProfile(Model model, @PathVariable int id, @ModelAttribute("agent") Admin admin, BindingResult result, int page, int size, RedirectAttributes redirectAttributes) {
		
		return saveAgentProfile(model, "admin", admin, result, page, size, redirectAttributes);
		
	}


	
	
	
	

	/* save agent profile */
	public String saveAgentProfile(Model model, String role, Agent agent, BindingResult result, int page, int size, RedirectAttributes redirectAttributes) {
	
		agentValidator.validate(agent, result);
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		enableSuperPaginationSystem(model, new int[listTickets.getTotalPages()], pas, page, size);
		
		if (result.hasErrors()) {
			
			model.addAttribute("listTickets", listTickets);
			
			model.addAttribute("role", role);
			
			model.addAttribute("action", "Show Profile of");
			
			return "tickets";
			
		}
		
		Agent a = iAgent.updateProfil(agent);
		
		model.addAttribute("agent", a);
		
		redirectAttributes.addFlashAttribute("profileUpdated", "Your profile has been successfully updated");
		
		return "redirect:/agent/" + agent.getId() + "/show/tickets";
		
	}
	
	
	
	
	
	
	
	
	
	
	/* func_num = 4 */
	/* get assigned ticket to an agent X */
	/* get opened assigned tickets to an agent X*/
	/* get in progress assigned tickets to an agent X*/
	/* get closed assigned tickets to an agent X*/
	//not verified
	//used
	
	@RequestMapping(value = "/agent/{id}/show/assignedTickets", method = RequestMethod.GET)

	public String myTickets(Model model, @PathVariable int id,
			@RequestParam(name = "status", defaultValue="") String status,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			HttpServletRequest request, @RequestParam(name = "updateSuccess", defaultValue = "") String updateSuccess) {
		
			
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
			
		int[] pages = new int [listTickets.getTotalPages()];
			
		enableSuperPaginationSystem(model, pages, pas, page, size);
		
		model.addAttribute("listTickets", listTickets);
			
		model.addAttribute("listAssignedTickets", iAgent.assignedTickets(id, page, size)); /* must show all assigned tickets !!!!*/
			
		model.addAttribute("action2", "assignedTickets");
		
		if (!updateSuccess.equals("")) {
			
			model.addAttribute("m_tickSuccessMsg", "Ticket number " + updateSuccess + " has been successfully updated !");
			
		}
			
		initSessionAgentProfile(request);
		
		return "tickets";
		
	}

	
	
	
	
	
	
	/* func_num = 5 */
	/* get notifications */
	
	/*...*/
	
	
	
	

	
	
	/* func_num = 6 */
	/* search tickets by key word */
	//verified
	//used
	
	@RequestMapping(value = "/search/tickets", method = RequestMethod.GET)
	
	public String searchTicketsByAjaxRequest(Model model, @RequestParam(name = "keyWord", defaultValue="") String keyWord,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "status", defaultValue="") String status,
			HttpServletRequest request) {
		
		if (status.equals("") || status.equals("all")) {
			
			Page<Ticket> listTickets = iAgent.searchTickets(keyWord, page, size);
			
			int[] pages = new int [listTickets.getTotalPages()];
			
			int[] subPages = pagination.getPaginationPages(pages, pas, page);
			
			model.addAttribute("listTickets", listTickets);
			
			model.addAttribute("pages", subPages);
			
			model.addAttribute("taille", pages.length);
			
		} else {
			
			model.addAttribute("listTickets", iAgent.listTicketsByStatus(status));
			
		}
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("size", size);
		
		initSessionAgentProfile(request);
		
		return "tickets";
		
	}
	
	
	
	
	
	
	/* show ticket details in bootstrap modal */
	//verified
	//used
	
	@RequestMapping(value = "/agent/{id}/show/ticket/{ticket_id}/details")
	
	public String showTicketDetailsForm(Model model, @PathVariable int id, @PathVariable int ticket_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			HttpServletRequest request, @RequestParam(name = "from", defaultValue = "") String from) {
		
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);

		enableSuperPaginationSystem(model, new int [listTickets.getTotalPages()], pas, page, size);
		
		model.addAttribute("ticket", iAgent.findOneTicket(ticket_id));
		
		model.addAttribute("ticketHistoric", iAgent.getTicketHistoric(ticket_id, 0, 7));
		
		model.addAttribute("ticketAssignedTo", iAgent.getAssignTicketToOperations(ticket_id));
		
		model.addAttribute("agentRepository", agentRepository);
		
		model.addAttribute("listTickets", listTickets);
		
		model.addAttribute("listAssignedTickets", iAgent.assignedTickets(id, page, size));
		
		model.addAttribute("action", "show Details of");
		
		if (from.equals("assignedTickets")) {
			
			model.addAttribute("action2", from);
			
		}
		
		initSessionAgentProfile(request);
		
		return "tickets";
		
	}
	
	
	
	
	
	
	
	
	/* edit ticket to create a new one */
	//verified
	//used
	
	@RequestMapping(value = "/agent/{id}/edit/ticket")
	
	public String showAddTicketForm(Model model, @PathVariable String id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		int[] pages = new int [listTickets.getTotalPages()];
		
		int[] subPages = pagination.getPaginationPages(pages, pas, page);
		
		model.addAttribute("ticket", new Ticket());
		
		model.addAttribute("listTickets", listTickets);
		
		model.addAttribute("listCompanies", companyRepository.getAllCompanies(null));
		
		model.addAttribute("action", "create");
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("size", size);
		
		model.addAttribute("pages", subPages);
		
		model.addAttribute("taille", pages.length);
		
		return "tickets";
	
	}
	
	
	
	
	
	/* edit ticket to update it */
	
	@RequestMapping(value = "/agent/{id}/edit/ticket/{ticket_id}")
	
	public String showEditTicketForm(Model model, @PathVariable int id, @PathVariable int ticket_id,
			@RequestParam(name = "from", defaultValue = "") String from,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		int[] pages = new int [listTickets.getTotalPages()];
		
		enableSuperPaginationSystem(model, pages, pas, page, size);
		
		model.addAttribute("ticket", iAgent.findOneTicket(ticket_id));
		
		model.addAttribute("listTickets", listTickets);
		
		model.addAttribute("listCompanies", companyRepository.getAllCompanies(null));
		
		model.addAttribute("action", "update");
		
		if (from != null && from.equals("assignedTickets")) {
			
			model.addAttribute("listAssignedTickets", iAgent.assignedTickets(id, page, size));
			
			model.addAttribute("action2", "assignedTickets");
		}
		
		return "tickets";
	
	}
	
	
	
	
	
	
	
	/* display assign ticket form to assign it to [agents, team, all] */
	
	@RequestMapping(value = "/agent/{id}/assign/ticket/{ticket_id}/to", method = RequestMethod.GET)
	
	public String showAssignTicketForm(Model model, @PathVariable int id, @PathVariable int ticket_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, String from) {
		
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		model.addAttribute("ticket", iAgent.findOneTicket(ticket_id));
		
		model.addAttribute("listTickets", listTickets);
		
		model.addAttribute("listAgents", agentRepository.getAllAgents(null));
		
		model.addAttribute("ticketAssignedTo", iAgent.getAssignTicketToOperations(ticket_id));
		
		model.addAttribute("agentRepository", agentRepository);
		
		model.addAttribute("action", "assign To");
		
		enableSuperPaginationSystem(model, new int [listTickets.getTotalPages()], pas, page, size);
		
		if (from != null && from.equals("assignedTickets")) {

			model.addAttribute("listAssignedTickets", iAgent.assignedTickets(id, page, size));
			
			model.addAttribute("action2", from);
			
		}
		
		return "tickets";
		
	}
	
	
	
	
	
	
	
	/* assign ticket to [all, agent, team] */
	//verified
	//used
	//improved
	
	@RequestMapping(value = "/agent/{id}/assign/ticket/{ticket_id}/to/{to}", method = RequestMethod.POST)
	
	public String assignTicketXToY(Model model, @PathVariable int id, @PathVariable int ticket_id,
			@PathVariable String to, String action, String toAgent, String toTeam, String from,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		Ticket ticket = iAgent.findOneTicket(ticket_id);
		
		Agent byAgent = iAgent.profile(id);
		
		String errorMsg = "";
		
		String successMsg = "";
		
		int agent_id = 0;
		
		Page<Ticket> listTickets = iAgent.listTickets(page, size);
		
		enableSuperPaginationSystem(model, new int[listTickets.getTotalPages()], pas, page, size);
		
		if(to.equals("agents")) {
			
			agent_id = Integer.parseInt(toAgent);
			
		}
		
		model.addAttribute("listTickets", listTickets);
		
		if (to.equals("all")) {
			
			//assign ticket to all
			if (!iAgent.assignTicketToAll(byAgent, ticket)) {
				
				errorMsg = "This ticket already assigned to all agents !";
				
			} else { successMsg = "The ticket number " + ticket.getId() + " has been successfully assigned to all agents."; }
			
			
		} else if (to.equals("team")) {
			
			//assign ticket to team X
			if (!iAgent.assignTicketToTeam(byAgent, ticket, toTeam)) {
				
				errorMsg = "This ticket already assigned to " + toTeam.toUpperCase() + " team !";
				
			} else { successMsg = "The ticket number " + ticket.getId() + " has been successfully assigned to " + toTeam.toUpperCase() + " team."; }
			
		} else {
			
			//assign ticket to agent X
			if (!iAgent.assignTicketToAgent(byAgent, ticket, agent_id)) {
				
				errorMsg = "This ticket already assigned to " + iAgent.profile(agent_id).getName();
				
			} else { successMsg = "The ticket number " + ticket.getId() + " has been successfully assigned to " + iAgent.profile(Integer.parseInt(toAgent)).getName(); }
			
		}
		
		if (!errorMsg.equals("")) {
			
			model.addAttribute("assignToError", errorMsg);
			
			model.addAttribute("action", "assign To");
			
			model.addAttribute("ticketAssignedTo", iAgent.getAssignTicketToOperations(ticket_id));
			
			model.addAttribute("listAgents", agentRepository.getAllAgents(null));
			
			model.addAttribute("agentRepository", agentRepository);
			
		} else {
			
			model.addAttribute("a_TickSuccessMsg", successMsg);
			
		}
		
		if (from != null && from.equals("assignedTickets")) {
			
			model.addAttribute("listAssignedTickets", iAgent.assignedTickets(id, page, size));
			
			model.addAttribute("action2", from);
			
			return "redirect:/agent/" + id + "/show/assignedTickets";
			
		}
		
		model.addAttribute("ticket", ticket);
			
		return "tickets";
		
	}
	
	
	
	
	
	
	
	
	
	
	/* save ticket */
	//verified
	//used
	
	@RequestMapping(value = "/agent/{id}/save/ticket")
	
	public String saveTicket(Model model, @PathVariable String id, String action, String action2, @ModelAttribute("ticket") Ticket ticket,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue="5") int size) {
		 
<<<<<<< HEAD
		System.out.println(page + "****" + size + "****" + ticket.getSubject() + "****");
=======
		
>>>>>>> 3611e4b3dcf10afa1c903082f997d9a03bad8465
		Agent agent = iAgent.profile(Integer.parseInt(id));
		
		ticketValidator.validate(ticket, result);
		
		String actionResult = "";
		
		//System.out.println(ticket.getSubject() + " - " + ticket.getDescription() + " from adminController line 485");
		
		if (result.hasErrors()) {
			
			Page<Ticket> listTickets = iAgent.listTickets(page, size);
			
			int[] pages = new int [listTickets.getTotalPages()];
			
			int[] subPages = pagination.getPaginationPages(pages, pas, page);
			
			model.addAttribute("listTickets", listTickets);
			
			model.addAttribute("listCompanies", companyRepository.getAllCompanies(null));
			
			model.addAttribute("action", action);
			
			model.addAttribute("currentPage", page);
			
			model.addAttribute("size", size);
			
			model.addAttribute("pages", subPages);
			
			model.addAttribute("taille", pages.length);
			
			return "tickets";
			
		}
		
		if (action.equals("create")) {
				
			Ticket t = iAgent.createTicket(agent, ticket);
			
			actionResult = "createSuccess=" + t.getId();
			
		} else {
			
			iAgent.updateTicket(agent, ticket);
			
			actionResult = "updateSuccess=" + ticket.getId();
		}
		
		if (action2 != null && action2.equals("assignedTickets")) {
			
			return "redirect:/agent/" + id + "/show/assignedTickets?" + actionResult;
			
		}

		return "redirect:/agent/" + id + "/show/tickets?" + actionResult;
		
	}
	

	
	
	
	
	
	/* func_num = 10 */
	/* get ticket details */
	
	@RequestMapping(value = "/agent/show/ticket", method = RequestMethod.GET)
	
	public String ticketDetails(Model model, @RequestParam(name = "id") Integer ticket_id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		
		try {
			
			model.addAttribute("ticket", iAgent.findOneTicket(ticket_id));
			
			model.addAttribute("ticketHistoric", iAgent.getTicketHistoric(ticket_id, page, size));
			
		} catch(Exception e) {
			
			model.addAttribute("error", e);
			
		}
		
		return "tickets";
		
	}
	
	
	
	public void initSessionAgentProfile(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("agentProfile") == null) {
			
			System.out.println("agentProfile null form agentController line 694");
			
			Authentication authentication = iAuthenticationFacade.getAuthentication();
			
			Agent agentProfile = iAgent.profile(authentication.getName());
			
			request.getSession().setAttribute("agentProfile", agentProfile);
		}
		
	}
	
	
	
	
	
	
	
	/* display tickets by status */
	
	@RequestMapping(value="tickets/statistic", method = RequestMethod.GET)
	@ResponseBody
	
	public List<Integer> numberOfAllTicketsByStatus() {
		//first: opened
		//second: in progress
		//third: closed
		
		List<Integer> listNbTickets = new ArrayList<Integer>();
		
		//number of opened tickets:
		listNbTickets.add(iAgent.listTicketsByStatus("opened").size());
		
		//number of in_progress tickets:
		listNbTickets.add(iAgent.listTicketsByStatus("in_progress").size());
		
		//number of closed tickets:
		listNbTickets.add(iAgent.listTicketsByStatus("closed").size());
		
		return listNbTickets;
		
	}
	
	

	
	
	
	
	/* enable pagination */
	
	public void enableSuperPaginationSystem(Model model, int[] pages, int pas, int page, int size) {
		
		int[] subPages = pagination.getPaginationPages(pages, pas, page);
		
		model.addAttribute("currentPage", page);
		
		model.addAttribute("size", size);
		
		model.addAttribute("pages", subPages);
		
		model.addAttribute("taille", pages.length);
		
	}
	
	
	
	
	
	
	
	
	
	
}
