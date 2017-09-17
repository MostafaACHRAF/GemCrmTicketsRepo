package com.GemCrmTickets.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GemCrmTickets.entities.Values;

public interface ValuesRepository extends JpaRepository<Values, Integer> {
	
	
	/* get historic of an ticket */
	//not used
	@Query("select v from Values v where v.ticket.id = :ticket_id order by v.id desc")
	public Page<Values> getTicketHistoric(@Param("ticket_id") int ticket_id, Pageable pageable);
	
	
	
	
	
	/* get ticket last value */
	//not used
	@Query("select v from Values v where v.ticket.id = :ticket_id order by v.created_at desc")
	public Values getTicketOldValues(@Param("ticket_id") int ticket_id);
}
