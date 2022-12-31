package com.project.community.user.repository;

import com.project.community.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmailAuthKey(String emailAuthKey);
}
