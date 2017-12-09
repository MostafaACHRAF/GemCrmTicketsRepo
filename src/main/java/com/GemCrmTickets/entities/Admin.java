package com.GemCrmTickets.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("adm")
public class Admin extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*------------------- constructors -------------------*/
	
	public Admin() {
		super();
	}

	
	
	
	
	
	
	public Admin(String name, String lastName, String image, Date birthDate, String email, String password, int office_number,
			String office_tel, double salary, String formation, String cv, String contract_type, Date start_date,
			Date end_date, Date created_at, Date updated_at, boolean visibility) {
		
		super(name, lastName, image, birthDate, email, password, office_number, office_tel, salary, formation, cv, contract_type,
				start_date, end_date, created_at, updated_at, visibility);
		
	}
	
	
	
	
	
	
	
}
