package com.GemCrmTickets.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.entities.Ticket;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
	
	
	
	
	
	
	/* get all agents */
	//used
	@Query("select a from Agent a where a.visibility = true order by a.id desc")
	public Page<Agent> getAllAgents(Pageable pageable);
	
	
	
	
	
	
	/* search agent by name or last name */
	@Query("select a from Agent a where lower(a.name) like %:key% or lower(a.lastName) like %:key% order by a.id desc")
	public Page<Agent> findAgentByKey(@Param("key") String key, Pageable pageable);
	
	
	
	
	
	
	
	/* find agent account by email (username) */
	//used
	@Query("select a from Agent a where a.email like :email")
	public Agent findAgentAccountByEmail(@Param("email") String email);

	
	
	
	
	
	/* modify agent */
	//used
	@Modifying
	@Transactional
	@Query("update Agent a set a.name = :n, a.lastName = :ln, "
			+ "a.image = :img, a.birthDate = :birth, a.email = :mail, a.password = :pass, a.office_number = :of_num, "
			+ "a.office_tel = :of_tel, a.salary = :s, a.formation = :f, a.cv = :cv, a.contract_type = :ct, "
			+ "a.start_date = :sd, a.end_date = :ed, a.visibility = :v where a.id = :agent_id")
	public void modifyAgent(
				@Param("n") String name,
				@Param("ln") String lastName,
				@Param("img") String image,
				@Param("birth") Date birthDate,
				@Param("mail") String email,
				@Param("pass") String password,
				@Param("of_num") int office_number,
				@Param("of_tel") String office_tel,
				@Param("s") double salary,
				@Param("f") String formation,
				@Param("cv") String cv,
				@Param("ct") String contract_type,
				@Param("sd") Date start_date,
				@Param("ed") Date end_date,
				@Param("v") boolean visibility,
				@Param("agent_id") int agent_id
			);
	
	
	
	
	
	

	
	/* get agent X opened tickets */
	/* get agent X in progress tickets */
	/* get agent X closed tickets */
	
	@Query("select t from Ticket t, Operation o where t.status = :status and o.component.id = t.id and o.agent.id = :agent_id order by t.created_at desc")
	
	public Page<Ticket> getAgentTicketsByStatus(@Param("agent_id") int agent_id, @Param("status") String status, Pageable pageable);
	
	
	
	
	

	
	/* get agents by team [developer , admin, support] */
	//used
	@Query("select a from Agent a where a.class = :team order by a.id")
	
	public List<Agent> getAgentsByTeam(@Param("team") String team);
	
	
	
	
	
	
	
	/* get the number of the same email addresses */
	/* this request help me to check if an account already exist or not */
	/* this function used by AgentValidator class */
	//used
	@Query("select count(a) from Agent a  where a.email like :email")
	
	public int thisEmailAlreadyExist(@Param("email") String email);
	
	
	
	
	
	
	
	/* get agents that this ticket X assigned to them */
	//not used
	@Query("select a from Agent a, Operation o where o.component.id = :ticket_id and o.name = 'assign ticket' and a.id = o.to_agent")
	public List<Agent> getAgentTicketAssignedToThem(@Param("ticket_id") int ticket_id);
	
	
	
	
	/* get tickets by status */
	@Query("select t from Ticket t, Operation o where t.status = :status and o.component.id = t.id order by t.created_at desc")
	public List<Ticket> getTicketByStatus(@Param("status") String status);
	
}
