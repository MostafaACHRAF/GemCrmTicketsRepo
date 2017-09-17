package com.GemCrmTickets.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("m_tik")
public class ManageTicket extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*------------------- constructors -------------------*/
	public ManageTicket() {
		super();
	}


	public ManageTicket(Date created_at, String name, Component component, Agent agent) {
		super(created_at, name, component, agent);
	}

	
}
