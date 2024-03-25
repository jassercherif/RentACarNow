package dev.jasser.RentACarNowBack.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.jasser.RentACarNowBack.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findFirstByEmail(String email);
}
