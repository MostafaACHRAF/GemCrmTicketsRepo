package com.GemCrmTickets.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.GemCrmTickets.entities.Company;

@Component

public class CompanyValidator implements Validator {

	
	@Override
	public boolean supports(Class<?> claszz) {
		
		return Company.class.equals(claszz);
		
	}
	
	
	
	
	/* this function validate the company name */
	public boolean validateName(String name, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.emptyvalue", "The company name can't be empty !");
		
		if (name.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("name", "name.numberformat", "The company name can't strat with numbers !");
			
			return true;
			
		} else if (name.length() < 4 || name.length() > 15) {
			
			e.rejectValue("name", "name.outrange", "The company name must contain between 4 and 15 characters.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	/* this function validate email address */
	
	public boolean validateEmail(String email, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty", "Empty email address !");
		
		if (! email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
			
			e.rejectValue("email", "email.invalidformat", "Invalid email address format !");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	/* this function validate the company address */
	
	public boolean validateAddress(String address, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "address", "address.emptyvalue", "The company address can't be empty !");
		
		if (address.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("address", "address.numberformat", "The company address can't strat with numbers !");
			
			return true;
			
		} else if (address.length() < 8 || address.length() > 30) {
			
			e.rejectValue("address", "address.outrange", "The company address must contain between 8 and 30 characters.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	/* this function validate the company turnover */
	
	public boolean validateTurnover(double turnover, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "turnover", "turnover.required", "The company turnover is required !");
		
		if (turnover < 1000 || turnover > 1000000) {
			
			e.rejectValue("turnover", "turnover.outofrange", "The company turnover must be between 1000 and 1000000 euros.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	/* this function validate the company number of employees */

	public boolean validateNumberOfEmployees(int number_employees, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "number_employees", "numberemployees.required", "The company number of employees is required !");
		
		if (number_employees < 10 || number_employees > 1000000) {
			
			e.rejectValue("number_employees", "numberemployees.outofrange", "The company number of employees must be between 10 and 1000000.");
			
			return true;
			
		}
		
		return false;
		
	}



	

	@Override
	public void validate(Object obj, Errors e) {
		
		
		Company c = (Company) obj;
		
		
		/* validate name */
		validateName(c.getName(), e);
		
		
		/* validate email */
		validateEmail(c.getEmail(), e);
		
		
		/* validate address */
		validateAddress(c.getAddress(), e);
		
		
		/* validate turnover */
		validateTurnover(c.getTurnover(), e);
		
		
		/* validate number of employees */
		validateNumberOfEmployees(c.getNumber_employees(), e);
		
	}

}
