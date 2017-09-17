package com.GemCrmTickets.metier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.GemCrmTickets.dao.AgentRepository;
import com.GemCrmTickets.dao.CompanyRepository;
import com.GemCrmTickets.dao.OperationRepository;
import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Company;
import com.GemCrmTickets.entities.ManageAgent;
import com.GemCrmTickets.entities.ManageCompany;
import com.GemCrmTickets.entities.ManageTicket;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Ticket;


@Component("admin")

public class AdminImpl extends AgentImpl implements IAdmin {

	
	/*------- Attributs  -------*/
	
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	

	
	
	

	@Override
	public Agent createAgent(Agent agent, Agent on_agent) {
		
		on_agent.setCreated_at(new Date());
		
		agentRepository.save(on_agent);
		
		operationRepository.save(new ManageAgent(new Date(), "Create agent", null, agent, on_agent.getId()));
		
		return on_agent;
		
	}

	
	
	
	
	
	@Override
	public Agent updateAgent(Agent admin, Agent on_agent) {
		
		this.profile(on_agent.getId());
		
		agentRepository.modifyAgent(
				on_agent.getName(),
				on_agent.getLastName(),
				on_agent.getImage(),
				on_agent.getBirthDate(),
				on_agent.getEmail(),
				on_agent.getPassword(),
				on_agent.getOffice_number(),
				on_agent.getOffice_tel(),
				on_agent.getSalary(),
				on_agent.getFormation(),
				on_agent.getCv(),
				on_agent.getContract_type(),
				on_agent.getStart_date(),
				on_agent.getEnd_date(),
				on_agent.getVisibility(),
				on_agent.getId());
		
		operationRepository.save(new ManageAgent(new Date(), "Modfiy agent", null, admin, on_agent.getId()));
		
		return agentRepository.findOne(on_agent.getId());
		
	}

	
	
	
	
	
	@Override
	public boolean deleteAgentAccount(Agent agent, int agent_id) {

		Agent on_agent = this.profile(agent_id);
		
		on_agent.setVisibility(false);
		
		this.updateAgent(agent, on_agent);
		
		operationRepository.save(new ManageAgent(new Date(), "Delete agent", null, agent, agent_id));
		
		return true;
	}

	
	
	
	
	
	@Override
	public Company createCompany(Agent agent, Company company) {
		
		company.setCreated_at(new Date());
		
		companyRepository.save(company);
		
		operationRepository.save(new ManageCompany(new Date(), "create company", company, agent));
		
		return company;
	}

	
	
	
	
	
	@Override
	public void updateCompany(Agent agent, Company company) {

		this.findOneCompany(company.getId());

		companyRepository.modifyCompany(
					company.getName(),
					company.getEmail(),
					company.getLegal_status(),
					company.getLogo(),
					company.getTurnover(),
					company.getNumber_employees(),
					company.getSector(),
					company.getNationality(),
					company.getVisibility(),
					new Date(),
					company.getId()
				);
		
		operationRepository.save(new ManageCompany(new Date(), "modify company", company, agent));
		
		//return companyRepository.findOne(company.getId());
		
	}

	
	
	
	
	
	@Override
	public Company deleteCompany(Agent agent, int company_id) {


		Company c = this.findOneCompany(company_id);
		
		c.setVisibility(false);
		
		this.updateCompany(agent, c);
		
		operationRepository.save(new ManageCompany(new Date(), "Delete company", c, agent));
		
		return c;
		
	}

	@Override
	public Ticket deleteTicket(Agent agent, int ticket_id) {
		
		Ticket t = this.findOneTicket(ticket_id);

		t.setVisibility(false);
		
		this.updateTicket(agent, t);
		
		operationRepository.save(new ManageTicket(new Date(), "Delete ticket", t, agent));
		
		return t;
		
	}

	
	
	
	
	
	@Override
	public Page<Agent> searchAgents(String key, int page, int size) {

		return agentRepository.findAgentByKey(key, null);
		
	}

	
	
	
	
	
	@Override
	public Page<Company> searchCompanies(String key, int page, int size) {

		return companyRepository.findCompanyByKey(key, new PageRequest(page, size));
		
	}

	
	
	
	
	
	@Override
	public Page<Agent> listAgents(int page, int size) {

		return agentRepository.getAllAgents(new PageRequest(page, size));
		
	}

	
	
	
	
	
	@Override
	public Page<Company> listCompanies(int page, int size) {
		
		return companyRepository.getAllCompanies(new PageRequest(page, size));
		
	}

	
	
	
	
	
	@Override
	public Page<Operation> listAgentOperations(int agent_id, int page, int size) {
		
		return operationRepository.getOperationsByAgent(agent_id, new PageRequest(page, size));
		
	}






	@Override
	public Agent findOneAgent(int agent_id) {

		Agent a = agentRepository.findOne(agent_id);
		
		if (a == null) {
			
			throw new RuntimeException("Sorry. Agent not found !");
			
		}
		
		return a;
		
	}






	@Override
	public Company findOneCompany(int company_id) {
		
		Company c = companyRepository.findOne(company_id);
		
		if (c == null) {
			
			throw new RuntimeException("Sorry. Company not found !");
			
		}
		
		return c;
		
	}






	@Override
	public void checkIfCompanyExists(int company_id) {
		
		if(companyRepository.findOne(company_id) != null) {
			
			throw new RuntimeException("Sorry. This company already exists !");
			
		}
	}






	
}
