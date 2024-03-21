package dev.jasser.RentACarNowBack.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.jasser.RentACarNowBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findFirstByEmail(username)
				       .orElseThrow(()-> new UsernameNotFoundException("User not found"));
			}
		};
	}

}
