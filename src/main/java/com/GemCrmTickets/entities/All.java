package com.GemCrmTickets.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("all")
public class All extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*------------------- constructors -------------------*/
	public All() {
		super();
	}
	
}
