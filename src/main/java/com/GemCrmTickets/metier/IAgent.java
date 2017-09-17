package com.GemCrmTickets.metier;

import java.util.List;

import org.springframework.data.domain.Page;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Ticket;
import com.GemCrmTickets.entities.Values;


public interface IAgent {
	

	public Page<Ticket> listTickets(int page, int size);
	
	
	public Page<Ticket> searchTickets(String key, int page, int size);
	
	
	public Page<Ticket> assignedTickets(int agent_id, int page, int size);
	
	
	/* get opened, in progress and closed tickets */
	public Page<Ticket> getAgentTicketsByStatus(int agent_id, String status, int page, int size);
	
	
	/* assign ticket to all agent */
	public boolean assignTicketToAll(Agent agent, Ticket ticket);
	
	
	/* assign ticket to agent X */
	public boolean assignTicketToAgent(Agent agent, Ticket ticket, int agent_id);
	
	
	/* assign ticket to team Z */
	public boolean assignTicketToTeam(Agent agent, Ticket ticket, String team);
	
	
	public Ticket createTicket(Agent agent, Ticket ticket);
	
	
	public Ticket updateTicket(Agent agent, Ticket ticket);
	
	
	public Page<Ticket> notifications(int page, int size);
	
	
	/* this function throw an exception if an agent not exists */
	public Agent profile(int agent_id);
	
	
	/* get agent profile by his address mail (username) */
	public Agent profile(String email);
	
	
	/* update agent account or profile */
	public Agent updateProfil(Agent agent);
	
	
	/* this function throw an exception if a ticket not exists */
	public Ticket findOneTicket(int ticket_id);
	
	
	/* this function throw an exception if an agent is already exists */
	public void checkIfAgentExists(int agent_id);
	
	
	/* this function throw an exception if a ticket is already exists */
	public void checkIfTicketExists(int ticket_id);
	
	
	/* this function get the historic of a ticket X */
	public Page<Values> getTicketHistoric(int ticket_id, int page, int size);
	
	
	/* this function check if an agent account already exist by checking if the agent email is unique */
	public boolean checkIfAccountAlreadyExist(String email);
	
	
	/* get ticket last value */
	public Page<Values> getTicketLastValue(int ticket_id);
	
	
	/* get list of agents that a ticket X assigned to them */
	public List<Operation> getAssignTicketToOperations(int ticket_id);
	
	
	/* check if ticket X already assigned to agent Y */
	public boolean ticketAlreadyAssignedToAgent(int ticket_id, int agent_id);
	
	
	/* list tickets by status */
	public List<Ticket> listTicketsByStatus(String status);
	
}
