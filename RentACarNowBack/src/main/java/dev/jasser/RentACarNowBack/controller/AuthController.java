package dev.jasser.RentACarNowBack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.jasser.RentACarNowBack.dto.SignupRequest;
import dev.jasser.RentACarNowBack.dto.UserDto;
import dev.jasser.RentACarNowBack.services.auth.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
	if(authService.hasCustomerwithEmail(signupRequest.getEmail()))
		return new ResponseEntity<>("Customer already exists with this email",HttpStatus.NOT_ACCEPTABLE);
	UserDto createdCustomerDto=authService.createCustomer(signupRequest);
	if(createdCustomerDto == null) return new ResponseEntity <>
     	("Customer not created, Come again later",HttpStatus.BAD_REQUEST);
	return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}
	}
	
