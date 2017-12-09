package com.GemCrmTickets.dao;



import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	
	/* get all tickets ordered by date */
	@Query("select t from Ticket t where t.visibility = true order by t.id desc")
	public Page<Ticket> getAllTickets(Pageable pageable);
	
	
	/* get tickets by status */
	@Query("select t from Ticket t where t.status = :s and t.visibility = true order by t.created_at desc")
	public Page<Ticket> getTicketsByStatus(@Param("s") String s, Pageable pageable);
	
	
	
	/* get assigned tickets to an agent */
	@Query("select t from Ticket t, Operation o where o.to_agent = :agent_id and o.name = 'assign ticket' and t.id = o.component.id and t.visibility = true order by o.created_at desc")
	public Page<Ticket> getAssignedTicketsTo(@Param("agent_id") int agent_id, Pageable pageable);
	
	
	/* search tickets by key word [subject, solution and description] */
	@Query("select t from Ticket t where t.subject like %:key% or t.description like %:key% or t.solution like %:key% order by t.id desc")
	public Page<Ticket> findTicketsByKey(@Param("key") String key, Pageable pageable);
	
	
	/* update / delete => ticket */
	@Modifying
	@Transactional
	@Query("update Ticket t set t.subject = :su, t.description = :d, t.solution = :so, t.status = :st, "
			+ "t.company = :c, t.activity_type = :act, t.visibility = :v, t.updated_at = :da where t.id = :id")
	public void modifyTicket(
				@Param("su") String subject,
				@Param("d") String description,
				@Param("so") String solution,
				@Param("st") String status,
				@Param("c") String company,
				@Param("act") String activity_type,
				@Param("v")  boolean v,
				@Param("da") Date updated_at,
				@Param("id") int id
			);
	
	
	/* ticket updated by */
	@Query("select a from Agent a, Operation o where o.name = 'modify ticket' and o.component.id = :ticket_id and a.id = o.agent.id and o.id = :op_id")
	public Agent ticketModfiedBy(@Param("ticket_id") int ticket_id, @Param("op_id") int op_id);
	
	
	/* check if a ticket X already assigned to agent Y */
	@Query("select count(distinct o) from Operation o, Ticket t where o.component.id = :ticket_id and o.name = 'assign ticket' and o.to_agent = :agent_id")
	public int ticketAlreadyAssignedToAgent(@Param("ticket_id") int ticket_id, @Param("agent_id") int agent_id);
	
}
