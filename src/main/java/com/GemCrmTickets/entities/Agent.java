package com.GemCrmTickets.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "agents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING, length = 3)
public abstract class Agent implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*------------------- attributes -------------------*/
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String lastName;
	
	private String image;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	
	@Transient
	private int age;
	
	private String email;
	
	private String password;
	
	@Transient
	private String confirmPassword;
	
	private int office_number;
	
	private String office_tel;
	
	private double salary;
	
	private String formation;
	
	private String cv;
	
	private String contract_type;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date start_date;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date end_date;
	
	private Date created_at;
	
	private Date updated_at;
	
	private Boolean visibility;
	
	@OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
	private Collection<Operation> operations;
	
	
	/*@OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
	private Collection<Values> values;*/
	
	/*------------------- constructors -------------------*/
	public Agent() {
		super();
	}
	
	
	public Agent(String name, String lastName, String image, Date birthDate, String email, String password, Integer office_number, 
			String office_tel, Double salary, String formation, String cv, String contract_type,
			Date start_date, Date end_date, Date created_at, Date updated_at, Boolean visibility) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.image = image;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.office_number = office_number;
		this.office_tel = office_tel;
		this.salary = salary;
		this.formation = formation;
		this.cv = cv;
		this.contract_type = contract_type;
		this.start_date = start_date;
		this.end_date = end_date;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.visibility = visibility;
	}
	
	
	/*------------------- getters & setters -------------------*/
	public Integer getId() {
		return id;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	public Date getBirthDate() {
		
		return this.birthDate;
		
	}
	
	
	public void setBirthDate(Date birthDate) {
		
			this.birthDate = birthDate;	
			
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Integer getOffice_number() {
		return office_number;
	}
	public void setOffice_number(Integer office_number) {
		this.office_number = office_number;
	}
	
	
	public String getOffice_tel() {
		return office_tel;
	}
	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}
	
	
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
	public String getFormation() {
		return formation;
	}
	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	
	
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	
	
	public Date getStart_date() {
		return start_date;
	}
	
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public Collection<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}


	public Boolean getVisibility() {
		return visibility;
	}


	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}


	public Date getCreated_at() {
		return created_at;
	}


	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}


	public Date getUpdated_at() {
		return updated_at;
	}


	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	public int getAge() {
		if (birthDate != null) {
			
			this.age = age(this.birthDate);
			
		} else {
			
			this.age = -1;
			
		}
		
		return this.age;
		
	}
	
	
	public void setAge(int age) {
		this.age = age;
	}


	public int age(Date birthDate) {
		
		int age = 0;
		
		if (birthDate != null) {
			
			LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			LocalDate currentDate = LocalDate.now();
			
			age = Period.between(birth, currentDate).getYears();
			
		}
		
		return age;
	}
	
	
	


	
	
	@Override
	public String toString() {
		return "Agent [name=" + name + ", lastName=" + lastName + ", birthDate=" + birthDate + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	
	
	

}
