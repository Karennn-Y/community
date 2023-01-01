package com.project.community.user.repository;

import com.project.community.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
	// 이메일 인증번호 확인
	Optional<User> findByEmailAuthKey(String emailAuthKey);

	// 이메일과 이름으로 찾기
	Optional<User> findByUserIdAndUserName(String userId, String userName);

	// 비밀번호 초기화시 uuid값으로 찾기
	Optional<User> findByResetPasswordKey(String resetPasswordKey);
}
