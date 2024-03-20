package dev.jasser.RentACarNowBack.services.auth;

import dev.jasser.RentACarNowBack.dto.SignupRequest;
import dev.jasser.RentACarNowBack.dto.UserDto;

public interface AuthService {
	UserDto createCustomer(SignupRequest signupRequest);
	boolean hasCustomerwithEmail(String email);
}
