package dev.jasser.RentACarNowBack.services.auth;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.jasser.RentACarNowBack.dto.SignupRequest;
import dev.jasser.RentACarNowBack.dto.UserDto;
import dev.jasser.RentACarNowBack.enums.UserRole;
import dev.jasser.RentACarNowBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import dev.jasser.RentACarNowBack.entity.User;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		User user= new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createdUser =userRepository.save(user);
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		return userDto;
	}
	@Override
	public boolean hasCustomerwithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
}
