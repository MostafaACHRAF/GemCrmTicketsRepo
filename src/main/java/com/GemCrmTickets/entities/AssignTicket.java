package com.GemCrmTickets.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("a_tik")
public class AssignTicket extends Operation {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*------------------- attributes -------------------*/
	private int to_agent;
	
	private boolean viewed;
	
	/*------------------- constructors -------------------*/
	public AssignTicket() {
		super();
	}

	public AssignTicket(Date created_at, String name, Agent agent, Component component, int to_agent, boolean viewed) {
		super(created_at, name, component, agent);
		this.to_agent = to_agent;
		this.viewed = viewed;
	}
	
	
	/*------------------- getters & setters -------------------*/
	public int getTo_agent() {
		return to_agent;
	}

	public void setTo_agent(int to_agent) {
		this.to_agent = to_agent;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}
	
	
}
