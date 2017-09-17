package com.GemCrmTickets.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.GemCrmTickets.entities.Ticket;


@Component

public class TicketValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Ticket.class.equals(clazz);
		
	}
	
	
	
	
	
	
	
	/* this function validate subject input */
	
	public boolean validateSubject(String subject, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "subject", "subject.empty", "The subject can't be empty !");
		
		if (subject.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("subject", "subject.invalidformat", "Subject can't begin with numbers !");
			
			return true;
			
		} else if (subject.length() < 8 || subject.length() > 30) {
			
			e.rejectValue("subject", "subject.outofrange", "The subject must contain between 4 and 30 characters.");
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	/* this function validate the description field */
	
	public boolean validateDescription(String description, Errors e) {
		
		System.out.println("here " + description);
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "description", "description.empty", "The description can't be empty !");
		
		if (description.matches("([0-9]+.*|-?\\d+(\\.\\d+)?)")) {
			
			e.rejectValue("description", "description.notstring", "Enter a valid description please !");
			
		} else if (description.length() < 8 || description.length() > 300) {
			
			e.rejectValue("description", "description.invalidformat", "The description must contain between 8 and 300 charachters.");
			
			return true;
			
		}
		
		return false;
		
	}
	

	
	
	
	
	
	@Override
	public void validate(Object obj, Errors e) {
		
		
		Ticket t = (Ticket) obj;
		
		
		//validate subject
		if (validateSubject(t.getSubject(), e)) {
			
			t.setSubject("");
			
		}
		
		
		//validate description
		if (validateDescription(t.getDescription(), e)) {
			
			t.setDescription("");
			
		}
		
		

	}

}
