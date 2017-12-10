package com.GemCrmTickets.metier;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.GemCrmTickets.dao.AgentRepository;
import com.GemCrmTickets.dao.OperationRepository;
import com.GemCrmTickets.dao.TicketRepository;
import com.GemCrmTickets.dao.ValuesRepository;
import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.AssignTicket;
import com.GemCrmTickets.entities.ManageAgent;
import com.GemCrmTickets.entities.ManageTicket;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Ticket;
import com.GemCrmTickets.entities.Values;



@Component("agent")

@Transactional

public class AgentImpl implements IAgent {

	
	/*------- Attributs  -------*/
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private ValuesRepository valuesRepository;
	
	
	
	
	
	@Override
	public Page<Ticket> listTickets(int page, int size) {
		
		return ticketRepository.getAllTickets(new PageRequest(page, size));
		
	}
	
	
	
	
	
	
	@Override
	public Page<Ticket> searchTickets(String key, int page, int size) {

		return ticketRepository.findTicketsByKey(key, new PageRequest(page, size));

	}

	
	
	
	
	
	@Override
	public Page<Ticket> assignedTickets(int agent_id, int page, int size) {

		return ticketRepository.getAssignedTicketsTo(agent_id, null);
		
	
	}

	

	
	

	@Override
	public Ticket createTicket(Agent agent, Ticket ticket) {

		ticket.setCreated_at(new Date());
		
		ticketRepository.save(ticket);
		
		operationRepository.save(new ManageTicket(new Date(), "Create ticket", ticket, agent));
		
		valuesRepository.save(new Values(ticket.getDescription(), ticket.getSolution(), new Date(), ticket, agent));
		
		assignTicketToAgent(agent, ticket, agent.getId()); //by default assign ticket to me
		
		return ticket;
		
	}

	
	
	
	
	
	@Override
	public Ticket updateTicket(Agent agent, Ticket ticket) {
		
		this.findOneTicket(ticket.getId());
			
		ticketRepository.modifyTicket(
				ticket.getSubject(),
				ticket.getDescription(),
				ticket.getSolution(),
				ticket.getStatus(),
				ticket.getCompany(),
				ticket.getActivity_type(),
				ticket.getVisibility(),
				new Date(),
				ticket.getId()
			);
			
		operationRepository.save(new ManageTicket(new Date(), "Modify ticket", ticket, agent));
			
		List<Values> val = this.getTicketLastValue(ticket.getId()).getContent();
		
		if (!val.get(0).getDescription().equals(ticket.getDescription()) || !val.get(0).getSolution().equals(ticket.getSolution())) {
			
			valuesRepository.save(new Values(ticket.getDescription(), ticket.getSolution(), new Date(), ticket, agent));
			
		}	
			
		return ticket;
		
	}

	
	
	
	
	
	@Override
	public Page<Ticket> notifications(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	@Override
	public Agent profile(int agent_id) {
		
		
		Agent agent = agentRepository.findOne(agent_id);
		
		if (agent == null) {
			
			throw new RuntimeException("Sorry. Profil not found !");
			
		}
		
		return agent;
		
	}

	
	
	
	
	
	@Override
	public Agent updateProfil(Agent agent) {

		this.profile(agent.getId());
		
		agentRepository.modifyAgent(
				agent.getName(),
				agent.getLastName(),
				agent.getImage(),
				agent.getBirthDate(),
				agent.getEmail(),
				agent.getPassword(),
				agent.getOffice_number(),
				agent.getOffice_tel(),
				agent.getSalary(),
				agent.getFormation(),
				agent.getCv(),
				agent.getContract_type(),
				agent.getStart_date(),
				agent.getEnd_date(),
				agent.getVisibility(),
				agent.getId()
				);
		
		operationRepository.save(new ManageAgent(new Date(), "Modify agent", null, agent, agent.getId()));
		
		return agentRepository.findOne(agent.getId());
		
	}






	@Override
	public void checkIfAgentExists(int agent_id) {
		
		if (agentRepository.findOne(agent_id) != null) {
			 
			throw new RuntimeException("Sorry. This account already exists !");
			
		}
		
	}






	@Override
	public Ticket findOneTicket(int ticket_id) {
		
		Ticket t = ticketRepository.findOne(ticket_id);
		
		if (t == null) {
			
			throw new RuntimeException("Sorry. Ticket not found !");
			
		}
		
		return t;
		
	}






	@Override
	//not used
	public Page<Values> getTicketHistoric(int ticket_id, int page, int size) {
		
		return valuesRepository.getTicketHistoric(ticket_id, new PageRequest(page, size));
		
	}






	@Override
	public void checkIfTicketExists(int ticket_id) {
		
		if (ticketRepository.findOne(ticket_id) != null) {
			
			throw new RuntimeException("Sorry. This ticket already exists !");
			
		}
	}






	@Override
	public boolean checkIfAccountAlreadyExist(String email) {
		
		return agentRepository.thisEmailAlreadyExist(email) == 1;
		
	}






	@Override
	public Agent profile(String email) {
		
		return agentRepository.findAgentAccountByEmail(email);
		
	}






	@Override
	public Page<Values> getTicketLastValue(int ticket_id) {
		return valuesRepository.getTicketHistoric(ticket_id, null);
	}






	@Override
	public List<Operation> getAssignTicketToOperations(int ticket_id) {

		return operationRepository.getAssignTicketToOperations(ticket_id);
		
	}





	/* this function check if a ticket X already assigned to agent Y */
	@Override
	public boolean ticketAlreadyAssignedToAgent(int ticket_id, int agent_id) {

		return ticketRepository.ticketAlreadyAssignedToAgent(ticket_id, agent_id) >= 1;
		
	}






	
	
	/* assign ticket X to agent Y */
	@Override
	public boolean assignTicketToAgent(Agent agent, Ticket ticket, int agent_id) {
		
		if (ticketAlreadyAssignedToAgent(ticket.getId(), agent_id)) {
			
			return false;
			
		}
		
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent, ticket, agent_id, false));
		
		return true;
		
	}

	
	
	
	
	
	
	/* assign ticket X to team Y */
	@Override
	public boolean assignTicketToTeam(Agent agent, Ticket ticket, String team) {
		
		List<Agent> listAgents = agentRepository.getAgentsByTeam(team);
		
		int totalAgents = listAgents.size();
		
		int i = 0;
		
		for(Agent a : listAgents) {
			
			if (!assignTicketToAgent(agent, ticket, a.getId())) {
				
				i++;
				
			}
			
		}
		
		return i < totalAgents;
		
	}
	






	/* assign ticket X to all agents */
	@Override
	public boolean assignTicketToAll(Agent agent, Ticket ticket) {
		
		List<Agent> listAgents = agentRepository.getAllAgents(null).getContent();
		
		int totalAgents = listAgents.size();
		
		int i = 0;
		
		for(Agent a : listAgents) {
				
			if (!assignTicketToAgent(agent, ticket, a.getId())) {
			
				i++;
				
			}
			
		}
		
		return i < totalAgents;
		
	}






	@Override
	public Page<Ticket> getAgentTicketsByStatus(int agent_id, String status, int page, int size) {
		
		return  agentRepository.getAgentTicketsByStatus(agent_id, status, new PageRequest(page, size));
		
	}






	@Override
	public List<Ticket> listTicketsByStatus(String status) {
		
		return ticketRepository.getTicketsByStatus(status, null).getContent();
		
	}

}
