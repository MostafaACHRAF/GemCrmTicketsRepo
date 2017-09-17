package com.GemCrmTickets.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ticket_values")
public class Values implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue
	private int id;
	
	private String description;
	
	private String solution;
	
	private Date created_at;
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	
	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;

	
	/*------------------- constructors -------------------*/
	public Values() {
		super();
	}


	public Values(String description, String solution, Date created_at, Ticket ticket, Agent agent) {
		super();
		this.description = description;
		this.solution = solution;
		this.created_at = created_at;
		this.ticket = ticket;
		this.agent = agent;
	}

	
	/*------------------- getters & setters -------------------*/
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSolution() {
		return solution;
	}


	public void setSolution(String solution) {
		this.solution = solution;
	}


	public Date getCreated_at() {
		return created_at;
	}


	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}


	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
	public Agent getAgent() {
		return agent;
	}
	
	
	public void setAgent(Agent a) {
		this.agent = a;
	}
	
	
}
