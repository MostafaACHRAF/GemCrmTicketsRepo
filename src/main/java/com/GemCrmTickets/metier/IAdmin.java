package com.GemCrmTickets.metier;

import org.springframework.data.domain.Page;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Company;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Ticket;


public interface IAdmin extends IAgent {

	public Agent createAgent(Agent agent, Agent on_agent);
	public Agent updateAgent(Agent agent, Agent on_agent);
	public boolean deleteAgentAccount(Agent agent, int agent_id);
	public Company createCompany(Agent agent, Company company);
	public void updateCompany(Agent agent, Company company);
	public Company deleteCompany(Agent agent, int company_id);
	public Ticket deleteTicket(Agent agent, int ticket_id);
	public Page<Agent> searchAgents(String key, int page, int size);
	public Page<Company> searchCompanies(String key, int page, int size);
	public Page<Agent> listAgents(int page, int size);
	public Page<Company> listCompanies(int page, int size);
	public Page<Operation> listAgentOperations(int agent_id, int page, int size);
	public Agent findOneAgent(int agent_id);
	public Company findOneCompany(int company_id);
	public void checkIfCompanyExists(int company_id);
	
}
