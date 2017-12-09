package com.GemCrmTickets.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tickets")
public class Ticket extends Component implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue
	private int id;
	
	private String subject;
	
	private String description;
	
	private String solution;
	
	private String status;
	
	private String company;
	
	private String activity_type;
	
	
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	private Collection<Values> values;
	
	/*------------------- constructors -------------------*/
	public Ticket() {
		super();
	}

	public Ticket(Date created_at, Date updated_at, boolean visibility) {
		super(created_at, updated_at, visibility);
	}

	public Ticket(String subject, String description, String solution, String status, String company,
			String activity_type, Date created_at, Date updated_at, boolean visibility) {
		super(created_at, updated_at, visibility);
		this.subject = subject;
		this.description = description;
		this.solution = solution;
		this.status = status;
		this.company = company;
		this.activity_type = activity_type;
	}
	
	
	/*------------------- getters & setters -------------------*/
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getActivity_type() {
		return activity_type;
	}


	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}


	public Collection<Values> getValues() {
		return values;
	}


	public void setValues(Collection<Values> values) {
		this.values = values;
	}
	

}
