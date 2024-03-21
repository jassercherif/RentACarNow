package dev.jasser.RentACarNowBack.controller;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jasser.RentACarNowBack.dto.AuthenticationRequest;
import dev.jasser.RentACarNowBack.dto.AuthenticationResponse;
import dev.jasser.RentACarNowBack.dto.SignupRequest;
import dev.jasser.RentACarNowBack.dto.UserDto;
import dev.jasser.RentACarNowBack.services.auth.AuthService;
import dev.jasser.RentACarNowBack.services.jwt.UserService;
import dev.jasser.RentACarNowBack.utils.JWTUtil;
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
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws
	   BadCredentialsException,
	   DisabledException,
	   UsernameNotFoundException{
	  try {
		  AuthenticatedAuthorizationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest))
	  } catch (BadCredentialsException e) {
		  throw new BadCredentialsException("Incorrect username or password.");
	  }
	  final UserDetails userDetails = UserService.userDetailsService().loadUserByUsername(authenticationRequest.getUsername());
	  Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
	  final String jwt = JWTUtil.generateToken(userDetails);
	  AuthenticationResponse authenticationResponse = new AuthenticationResponse();
	  if(optionalUser.isPresent()) {
		  authenticationResponse.setJwt(jwt);
		  authenticationResponse.setUserId(optionalUser.get().getId());
		  authenticationResponse.setUserRole(optionalUser.get().getUserRole());
	  }
	  return authenticationResponse;
	}
	}
	
