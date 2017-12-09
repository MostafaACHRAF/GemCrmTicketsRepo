package com.GemCrmTickets.metier;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

	Authentication getAuthentication();
}
