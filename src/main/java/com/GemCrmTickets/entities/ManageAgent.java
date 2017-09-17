package com.GemCrmTickets.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("m_age")
public class ManageAgent extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*------------------- attributes -------------------*/
	private int on_agent_id; //l'id de l'agent sur laquelle l'operation est effectue
	
	
	/*------------------- constructors -------------------*/
	public ManageAgent() {
		super();
	}

	
	public ManageAgent(Date created_at, String name, Component component, Agent agent, int on_agent_id) {
		super(created_at, name, component, agent);
		this.on_agent_id = on_agent_id;
	}


	/*------------------- getters & setters -------------------*/
	public int getOn_agent_id() {
		return on_agent_id;
	}


	public void setOn_agent_id(int on_agent_id) {
		this.on_agent_id = on_agent_id;
	}
	

}
