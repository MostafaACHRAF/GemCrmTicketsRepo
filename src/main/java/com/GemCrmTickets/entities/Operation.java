package com.GemCrmTickets.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "operations")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING, length=5)
public abstract class Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue
	private int id;
	
	private Date created_at;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "component_id")
	private Component component;
	
	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;
	
	
	/*------------------- constructors -------------------*/
	public Operation() {
		super();
	}

	
	public Operation(Date created_at, String name, Component component, Agent agent) {
		super();
		this.created_at = created_at;
		this.name = name;
		this.component = component;
		this.agent = agent;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Component getComponent() {
		return component;
	}


	public void setComponent(Component component) {
		this.component = component;
	}


	public Agent getAgent() {
		return agent;
	}


	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	
}
