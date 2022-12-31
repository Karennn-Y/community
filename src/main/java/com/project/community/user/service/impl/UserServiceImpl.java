package com.project.community.user.service.impl;

import com.project.community.component.MailComponents;
import com.project.community.user.entity.User;
import com.project.community.user.model.UserInput;
import com.project.community.user.repository.UserRepository;
import com.project.community.user.service.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.phoneNumber(parameter.getPhoneNumber())
				.password(parameter.getPassword())
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
}
