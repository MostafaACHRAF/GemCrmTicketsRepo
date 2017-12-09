package com.GemCrmTickets.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Component implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date created_at;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date updated_at;
	
	private boolean visibility;
	
	@OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
	private Collection<Operation> operations;

	
	
	
	/*------------------- constructors -------------------*/
	public Component() {
		super();
	}

	
	public Component(Date created_at, Date updated_at, boolean visibility) {
		super();
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.visibility = visibility;
	}

	
	/*------------------- getters & setters -------------------*/
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getCreated_at() {
		return created_at;
	}


	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}


	public Collection<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}


	public boolean getVisibility() {
		return visibility;
	}


	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}


	public Date getUpdated_at() {
		return updated_at;
	}


	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
	
	
}
