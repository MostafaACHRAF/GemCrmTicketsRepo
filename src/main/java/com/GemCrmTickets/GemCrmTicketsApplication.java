package com.GemCrmTickets;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.GemCrmTickets.dao.AgentRepository;
import com.GemCrmTickets.dao.CompanyRepository;
import com.GemCrmTickets.dao.OperationRepository;
import com.GemCrmTickets.dao.TicketRepository;
import com.GemCrmTickets.dao.ValuesRepository;
import com.GemCrmTickets.entities.Admin;
import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.AssignTicket;
import com.GemCrmTickets.entities.Company;
import com.GemCrmTickets.entities.Developer;
import com.GemCrmTickets.entities.ManageAgent;
import com.GemCrmTickets.entities.ManageCompany;
import com.GemCrmTickets.entities.ManageTicket;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Support;
import com.GemCrmTickets.entities.Ticket;
import com.GemCrmTickets.entities.Values;
import com.GemCrmTickets.metier.IAdmin;
import com.GemCrmTickets.metier.IAgent;

@SpringBootApplication
public class GemCrmTicketsApplication implements CommandLineRunner {
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private ValuesRepository valuesRepository;
	
	@Autowired
	@Resource(name = "agent")
	private IAgent iAgent;

	
	@Autowired
	@Resource(name = "admin")
	private IAdmin iAdmin;

	
	public static void main(String[] args) {
		SpringApplication.run(GemCrmTicketsApplication.class, args); 
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		/* ajouter les agents */
		Agent agent1 = agentRepository.save(new Developer("mostafa", "achraf", "user.png", new SimpleDateFormat("dd/MM/yyyy").parse("07/12/1994"), "mostafaegma@gmail.com", "mostafa", 01, "0601943183", 8000, "Software Engineer", "cv1", "CDI", new Date(), new Date(), new Date(), null, true));
		Agent agent2 = agentRepository.save(new Support("achraf", "ali", "user.png", new SimpleDateFormat("dd/MM/yyyy").parse("09/08/1967"), "achrafali@gmail.com", "achraf", 02, "0644370050", 10000, "Marketing agent", "cv2", "CDI", new Date(), new Date(), new Date(), null, true));
		Agent agent3 = agentRepository.save(new Admin("fatima", "zahra", "user.png", new SimpleDateFormat("dd/MM/yyyy").parse("03/11/1969"), "fatimazahra@gmail.com", "fatima", 03, "0696101816", 18000, "Director", "cv3", "CDI", new Date(), new Date(), new Date(), null, true));
		//Done!
		
		/* ajouter des tickets */
		Ticket t1 = ticketRepository.save(new Ticket("subject 1", "description 1", "solution 1", "opened", "company1", "call", new Date(), null, true));
		Ticket t21 = ticketRepository.save(new Ticket("xxxxx 21", "xxxx21", "", "opened", "company21", "call", new Date(), null, true));
		Ticket t2 = ticketRepository.save(new Ticket("subject 2", "description 2", "solution 2", "in_progress", "company2", "call", new Date(), null, true));
		Ticket t3 = ticketRepository.save(new Ticket("subject 3", "description 3", "solution 3", "closed", "company3", "email", new Date(), null, true));
		Ticket t4 = ticketRepository.save(new Ticket("subject 4", "description 4", "solution 4", "closed", "company4", "email", new Date(), null, true));
		//Done!
		
		/* ajouter companies */
		Company cmp1 = companyRepository.save(new Company("IBM", "ibm-contact@gmail.com", "New york", "SA", "logo245", 1500000, 5000, "technologie", "Amirica", new Date(), null, true));
		Company cmp2 = companyRepository.save(new Company("HP", "hp-contact@gmail.com", "washinton", "SA", "logo000", 102350.52, 5000, "technologie", "Amirica", new Date(), null, true));
		Company cmp3 = companyRepository.save(new Company("Apple", "apple-contact@gmail.com", "Georgia", "SA", "logo11", 1564.26, 5000, "technologie", "Amirica", new Date(), null, true));
		Company cmp4 = companyRepository.save(new Company("Samsung", "samsung-contact@gmail.com", "Viladelvia", "SA", "logo145", 57095.2648, 5000, "technologie", "Amirica", new Date(), null, true));
		Company cmp5 = companyRepository.save(new Company("google", "google-contact@gmail.com", "Paris", "SA", "logo200", 2648, 5000, "technologie", "France", new Date(), null, true));
		Company cmp6 = companyRepository.save(new Company("Gem car", "gem car-contact@gmail.com", "Casablanca", "SA", "logo205", 156487.2648, 5000, "technologie", "Maroc", new Date(), null, true));
		//Done!
		
		/* ajouter operations */
		//sur les companies
		Operation op1 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp1, agent1));
		Operation op2 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp2, agent2));
		Operation op3 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp3, agent2));
		Operation op4 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp4, agent1));
		Operation op5 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp5, agent3));
		Operation op6 = operationRepository.save(new ManageCompany(new Date(), "add company", cmp6, agent1));
		//sur les tickets
		Operation op7 = operationRepository.save(new ManageTicket(new Date(), "add ticket", t1, agent1));
		Operation op8 = operationRepository.save(new ManageTicket(new Date(), "add ticket", t2, agent2));
		Operation op9 = operationRepository.save(new ManageTicket(new Date(), "add ticket", t3, agent2));
		//sur les agents
		Operation op10 = operationRepository.save(new ManageAgent(new Date(), "aad agent", null, agent3, 1));
		Operation op11 = operationRepository.save(new ManageAgent(new Date(), "add agent", null, agent3, 2));
		
		
		/* ajouter values ou bien histroiques des tickets */
		valuesRepository.save(new Values(t1.getDescription(), t1.getSolution(), new Date(), t1, agent1));
		valuesRepository.save(new Values(t2.getDescription(), t2.getSolution(), new Date(), t2, agent2));
		valuesRepository.save(new Values(t3.getDescription(), t3.getSolution(), new Date(), t3, agent1));
		valuesRepository.save(new Values(t4.getDescription(), t4.getSolution(), new Date(), t4, agent1));
		valuesRepository.save(new Values(t21.getDescription(), t21.getSolution(), new Date(), t21, agent1));
		
		/* assign ticket: t1,t2 to agent: agent1 by agent: agent1*/
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t1, agent1.getId(), false));
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t2, agent1.getId(), false));
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t3, agent1.getId(), false));
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t3, agent2.getId(), false));
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t3, agent3.getId(), false));
		operationRepository.save(new AssignTicket(new Date(), "assign ticket", agent1, t4, agent3.getId(), false));

		
	}
}
