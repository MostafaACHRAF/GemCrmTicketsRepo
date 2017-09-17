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

import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "companies")
public class Company extends Component implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String email;
	
	private String address;
	
	private String legal_status;
	
	private String logo;
	
	@NumberFormat
	private double turnover;
	
	@NumberFormat
	private int number_employees;
	
	private String sector;
	
	private String nationality;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private Collection<Operation> operations;
	
	
	/*------------------- constructors -------------------*/
	public Company() {
		super();
	}


	public Company(String name, String email, String address, String legal_status, String logo, double turnover,
			int number_employees, String sector, String nationality, Date created_at, Date updated_at, boolean visibility) {
		super(created_at, updated_at, visibility);
		this.name = name;
		this.email = email;
		this.address = address;
		this.legal_status = legal_status;
		this.logo = logo;
		this.turnover = turnover;
		this.number_employees = number_employees;
		this.sector = sector;
		this.nationality = nationality;
	}

	
	/*------------------- getters & setters -------------------*/
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLegal_status() {
		return legal_status;
	}


	public void setLegal_status(String legal_status) {
		this.legal_status = legal_status;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public double getTurnover() {
		return turnover;
	}


	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}


	public int getNumber_employees() {
		return number_employees;
	}


	public void setNumber_employees(int number_employees) {
		this.number_employees = number_employees;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public Collection<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	
}
