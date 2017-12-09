package com.GemCrmTickets.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GemCrmTickets.entities.Company;
import com.GemCrmTickets.entities.Operation;
import com.GemCrmTickets.entities.Ticket;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

	
	/* get all operations performed by an agent X ordered by date in descending order */
	@Query("select o from Operation o where o.agent.id = :agent_id order by o.created_at desc")
	public Page<Operation> getOperationsByAgent(@Param("agent_id") int agent_id, Pageable pageable);
	
	
	/* get all operations performed on an ticket*/
	@Query("select o from Operation o, Ticket t where o.component.id = :ticket_id and t.id = :ticket_id order by o.created_at desc")
	public Page<Ticket> getOperationsOnTicket(@Param("ticket_id") int ticket_id, Pageable pageable);
	
	
	/* get all operations performed on an company */
	@Query("select o from Operation o, Company c where o.component.id = :company_id and c.id = :company_id order by o.created_at desc")
	public Page<Company> getOperationsOnCompany(@Param("company_id") int company_id, Pageable pageable);
	
	
	/* get all operations performed on an agent  */
	/*@Query("select o from Operation o, Agent a where o.on_agent_id = :company_id and c.id = :company_id order by o.created_at desc")
	public Page<Agent> getOperationsOnAgent(@Param("company_id") int company_id, Pageable pageable);*/
	
	
	/* get operations by date */
	
	/* get assign ticket X to ... operations */
	@Query("select o from Operation o where o.name = 'assign ticket' and o.component.id = :ticket_id order by o.created_at desc")
	public List<Operation> getAssignTicketToOperations(@Param("ticket_id") int ticket_id);
	
}
