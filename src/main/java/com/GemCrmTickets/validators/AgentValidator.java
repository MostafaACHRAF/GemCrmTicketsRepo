package com.GemCrmTickets.validators;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.GemCrmTickets.entities.Agent;
import com.GemCrmTickets.metier.IAgent;


@Component
public class AgentValidator implements Validator {
	
	

	@Autowired
	@Resource(name = "agent")
	private IAgent iAgent;
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Agent.class.equals(clazz);
		
	}
	
	
	
	
	
	
	
	/* validate agent birthDate */
	
	public boolean validateBirthDate(Date birthDate, Errors e) {
		
		if (birthDate == null) {
			
			e.rejectValue("birthDate", "birthdate.emptyvalue", "The birthDate is required !!!");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* validate agent age */
	
	public void validateAge(int age, Errors e) {
		
		if (age == 0) {
			
			e.rejectValue("birthDate", "birthdate.emptyvalue", "The birth date is required !");
			
		} else if (age < 18) {
			
			e.rejectValue("birthDate", "birthdate.tooyoung", "You are too young !");
			
		} else if (age > 120) {
			
			e.rejectValue("birthDate", "birthdate.tooold", "You are too old !");
			
		}
		
	}

	
	
	
	
	

	/* validate agent name */
	
	public boolean validateName(String name, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty", "Empty name !");
			
		if (name.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("name", "name.numberformat", "Your name can't strat with numbers !");
			
			return true;
			
		} else if (name.length() < 4 || name.length() > 10) {
			
			e.rejectValue("name", "name.invalidformat", "Your name must contain between 4 and 10 characters.");
			
			return true;
			
		}
		
		return false;
	}
	
	
	
	
	
	
	
	/* validate agent last name */
	
	public boolean validateLastName(String lastName, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "lastName", "lastname.empty", "Empty last name !");
		
		if (lastName.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("lastName", "lastname.numberformat", "Your last name can't strat with numbers !");
			
			return true;
			
		} else if (lastName.length() < 4 || lastName.length() > 10) {
			
			e.rejectValue("lastName", "lastname.invalidformat", "Your last name must contain between 4 and 10 characters.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* validate agent email */
	
	public boolean validateEmail(String email, boolean alreadyExist, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty", "Empty email adress !");
		
		if (! email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
			
			e.rejectValue("email", "email.invalidformat", "Invalid email address format !");
			
			return true;
			
		} else if (alreadyExist) {
			
			e.rejectValue("email", "email.alreadyexists", "This address mail is already used ! Change it please.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* validate agent password */
	
	public boolean validatePassword(String password, String confirmPassword, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty", "Empty password !");
		
		if (password.matches("[a-z0-9]+")) {
			
			e.rejectValue("password", "password.invalidformat", "Your password must contain a combination of miniscule and uppercase characters and digits.");
			
			return true;
			
		} else if (! password.equals(confirmPassword)) {
			
			e.rejectValue("confirmPassword", "confirmpassword.notmatch", "Your password not match de password.");
			
			return true;
			
		}
		
		if (password.length() < 8 || password.length() > 10) {
			
			e.rejectValue("password", "password.toolongorshort", "Your password must be between 8 and 10 characters.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/*validate contract start date */
	
	public LocalDate validateStartDate(Date start_date, Errors e) {
		
		if (start_date == null) {
			
			e.rejectValue("start_date", "startdate.nullvalue", "The contract start date is required !");
			
			return null;
			
		}
		
		return start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
	}
	
	
	
	
	
	
	
	/*validate contract end date */
	
	public LocalDate validateEndDate(Date end_date, Errors e) {
		
		if (end_date == null) {
			
			e.rejectValue("end_date", "startdate.nullvalue", "The contract end date is required !");
			
			return null;
			
		}
		
		return end_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
	}
	
	
	
	
	
	
	
	/* validate agent start and end dates must be like this : [end_date > start-date] */
	
	public void validateStart_EndDates(Date start_date, Date end_date, String contract_type, Errors e) {
		
		if (contract_type.equals("CDD")) {
			
			LocalDate startD = validateStartDate(start_date, e);
			
			LocalDate endD = validateEndDate(end_date, e);
			
			if (startD != null && endD != null) {
				
				if (startD.compareTo(endD) >= 0) {
					
					e.rejectValue("start_date", "startdate.bigertoenddate", "The contract start date must be smaller than the end date.");
					
				}
				
			}
			
		} else {
			
			validateStartDate(start_date, e);
			
		}
		
	}
	
	
	
	
	
	
	
	/* validate agent formation */
	
	public boolean validateFormation(String formation, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "formation", "formation.empty", "The formation field is required !");
		
		if (formation.length() < 10 || formation.length() > 500) {
			
			e.rejectValue("formation", "formation.invalidformat", "Your formation must contain between 10 and 500 characteres.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* validate agent phone nummber */
	
	public boolean validateOfficeTel(String tel, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "office_tel", "officetel.empty", "The phone number field is required !");
		
		if (!tel.matches("^06[0-9]{8}")) {
			
			e.rejectValue("office_tel", "officetel.invalidformat", "Enter a valid phone number please.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* validate agent office number */
	
	/*....*/
		
	
	
	
	
	
	
	/* validate salary */
	public boolean validateSalary(double salary, Errors e) {
		
		return true;
	}
	
		
	
	
	
	
	
	
	
	
	
	
	
	/* validate agent instances using the above functions */
	
	@Override
	public void validate(Object obj, Errors e) {
		
		
		Agent a = (Agent) obj;

		
		/* check if the entered name is valid */
		/* if there is a problem ! clear the input */
		if (validateName(a.getName(), e)) {
			
			a.setName("");
			
		}
		
		
		/* check if the entered last name is empty of whitespace */
		
		if (validateLastName(a.getLastName(), e)) {
			
			a.setLastName("");
			
		}
		
		
		/* check if the selected birth date is valid */
		
		if (!validateBirthDate(a.getBirthDate(), e)) {
			
			validateAge(a.age(a.getBirthDate()), e);
			
		}
		
		//validateBirthDate(a.age(a.getBirthDate()), e);
		
		
		/* check if entered email is valid when the administrator attempt to create a new agent account*/
		/* agent id = 0 if it's a new one */
		if (a.getId() == 0) {
			
			if (validateEmail(a.getEmail(), iAgent.checkIfAccountAlreadyExist(a.getEmail()), e)) {
				
				a.setEmail("");
				
			}
			
		}
		
		
		
		/* check if the entered password is valid */
		
		if (validatePassword(a.getPassword(), a.getConfirmPassword(), e)) {
			
			//a.setPassword("");
			
			//a.setConfirmPassword("");
			
		}
		
		
		/* check if the selected strat_date and end date of the agent contract are valid */
		validateStart_EndDates(a.getStart_date(), a.getEnd_date(), a.getContract_type(), e);
		
		
		/* check if the typed formation is valid */
		if (validateFormation(a.getFormation(), e)) {
			
			a.setFormation("");
			
		}
		
		
		/* check if the entered tel is valid */
		
		if (validateOfficeTel(a.getOffice_tel(), e)) {
			
			a.setOffice_tel("");
			
		}

	}
	

	
	
	
	
	
}
