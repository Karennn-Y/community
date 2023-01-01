package com.project.community.user.service.impl;

import com.project.community.component.MailComponents;
import com.project.community.user.entity.User;
import com.project.community.user.exception.UserEmailNotAuthException;
import com.project.community.user.model.UserInput;
import com.project.community.user.repository.UserRepository;
import com.project.community.user.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MailComponents mailComponents;

	@Override
	public boolean register(UserInput parameter) {

		Optional<User> optionalUser = userRepository.findById(parameter.getUserId());

		// id 회원가입 여부 조회(동일 id 있을 경우 return false)
		if (optionalUser.isPresent()) {
			return false;
		}

		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());


		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.phoneNumber(parameter.getPhoneNumber())
				.password(encPassword)
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.build();

		userRepository.save(user);

		String email = parameter.getUserId();
		String subject = "가입을 축하드립니다.";
		String text = "<p>가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
			+ "<div><a target='_blank' href='http://localhost:8080/user/email-auth?id="
			+ uuid + "'> 가입 완료 </a></div>";

		mailComponents.sendMail(email, subject, text);

		return true;
	}

	@Override
	public boolean emailAuth(String uuid) {

		Optional<User> optionalUser = userRepository.findByEmailAuthKey(uuid);

		if (!optionalUser.isPresent()) {
			return false;
		}

		User user = optionalUser.get();

		user.setEmailAuthYn(true);
		user.setEmailAuthDt(LocalDateTime.now());

		userRepository.save(user);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUser = userRepository.findById(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		User user = optionalUser.get();

		if (!user.isEmailAuthYn()) {
			throw new UserEmailNotAuthException("이메일 인증 이후에 로그인 해주세요.");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new org.springframework.security.core.userdetails.User(
				user.getUserId(), user.getPassword(), grantedAuthorities
			);
	}
}
