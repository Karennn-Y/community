package com.project.community.user.service.impl;

import com.project.community.admin.dto.UserDto;
import com.project.community.admin.mapper.UserMapper;
import com.project.community.admin.model.UserParam;
import com.project.community.component.MailComponents;
import com.project.community.exception.CustomException;
import com.project.community.exception.ExceptionCode;
import com.project.community.user.entity.User;
import com.project.community.user.entity.UserCode;
import com.project.community.user.exception.StopUserException;
import com.project.community.user.exception.UserEmailNotAuthException;
import com.project.community.user.model.ResetPasswordInput;
import com.project.community.user.model.UserInput;
import com.project.community.user.repository.UserRepository;
import com.project.community.user.service.UserService;
import com.project.community.util.PasswordUtils;
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
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final MailComponents mailComponents;
	private final UserMapper userMapper;

	@Override
	public boolean register(UserInput parameter) {

		Optional<User> optionalUser = userRepository.findById(parameter.getUserId());

		// id 회원가입 여부 조회(동일 id 있을 경우 return false)
		if (optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.ALREADY_EXIST_ID);
		}

		String encPassword = PasswordUtils.encPassword(parameter.getPassword());


		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.nickname(parameter.getNickname())
				.phoneNumber(parameter.getPhoneNumber())
				.password(encPassword)
				.zipcode(parameter.getZipcode())
				.addr(parameter.getAddr())
				.addrDetail(parameter.getAddrDetail())
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.userStatus(User.USER_STATUS_REQ)
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
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		if (user.isEmailAuthYn()) {
			throw new CustomException(ExceptionCode.ALREADY_AUTHORIZED_ACCOUNT);
		}

		user.setEmailAuthYn(true);
		user.setUserStatus(User.USER_STATUS_ING);
		user.setEmailAuthDt(LocalDateTime.now());

		userRepository.save(user);
		return true;
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {

		Optional<User> optionalUser = userRepository.findByUserIdAndUserName(
							parameter.getUserId(), parameter.getUserName());
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		String uuid = UUID.randomUUID().toString();
		user.setResetPasswordKey(uuid);
		user.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
		userRepository.save(user);


		String email = parameter.getUserId();
		String subject = "비밀번호 초기화";
		String text = "<p>비밀번호 초기화 메일 입니다.</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요</p>"
			+ "<div><a target='_blank' href='http://localhost:8080/user/reset/password?id="
			+ uuid + "'> 비밀번호 초기화 링크 </a></div>";
		mailComponents.sendMail(email, subject, text);

		return true;
	}

	@Override
	public boolean resetPassword(String uuid, String password) {

		Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		// 초기화 날짜 유효성 체크
		if (user.getResetPasswordLimitDt() == null) {
			throw new CustomException(ExceptionCode.DATE_IS_NOT_VALID);

		}
		if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new CustomException(ExceptionCode.DATE_IS_NOT_VALID);
		}

		String encPassword = PasswordUtils.encPassword(password);
		user.setPassword(encPassword);
		user.setResetPasswordKey("");
		user.setResetPasswordLimitDt(null);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean checkResetPassword(String uuid) {
		Optional<User> optionalUser = userRepository.findByResetPasswordKey(uuid);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}


		User user = optionalUser.get();

		// 초기화 날짜 유효성 체크
		if (user.getResetPasswordLimitDt() == null) {
			throw new CustomException(ExceptionCode.DATE_IS_NOT_VALID);		}
		if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new CustomException(ExceptionCode.DATE_IS_NOT_VALID);		}

		return true;
	}

	@Override
	public List<UserDto> list(UserParam parameter) {
		long totalCount = userMapper.selectListCount(parameter);
		List<UserDto> list = userMapper.selectList(parameter);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (UserDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}

		return list;
	}

	@Override
	public UserDto detail(String userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			return null;
		}
		User user = optionalUser.get();

		return UserDto.of(user);
	}

	@Override
	public boolean updateStatus(String userId, String userStatus) {

		Optional<User> optionalUser = userRepository.findById(userId);

		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();
		user.setUserStatus(userStatus);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean updatePassword(String userId, String password) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		user.setPassword(encPassword);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean updateUser(UserInput parameter) {

		String userId = parameter.getUserId();
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		user.setNickname(parameter.getNickname());
		user.setPhoneNumber(parameter.getPhoneNumber());
		user.setZipcode(parameter.getZipcode());
		user.setAddr(parameter.getAddr());
		user.setAddrDetail(parameter.getAddrDetail());
		user.setUdtDt(LocalDateTime.now());
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean updateUserPassword(UserInput parameter) {

		String userId = parameter.getUserId();
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();
		if (!PasswordUtils.equals(parameter.getPassword(), user.getPassword())) {
			throw new CustomException(ExceptionCode.PASSWORDS_DO_NOT_MATCH);
		}

		String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());
		user.setPassword(encPassword);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean withdraw(String userId, String password) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		User user = optionalUser.get();

		if (!PasswordUtils.equals(password, user.getPassword())) {
			throw new CustomException(ExceptionCode.PASSWORDS_DO_NOT_MATCH);
		}

		user.setUserName("탈퇴회원");
		user.setNickname("");
		user.setPhoneNumber("");
		user.setPassword("");
		user.setRegDt(null);
		user.setUdtDt(null);
		user.setEmailAuthYn(false);
		user.setEmailAuthDt(null);
		user.setEmailAuthKey("");
		user.setResetPasswordKey("");
		user.setResetPasswordLimitDt(null);
		user.setUserStatus(UserCode.USER_STATUS_WITHDRAW);
		user.setZipcode("");
		user.setAddr("");
		user.setAddrDetail("");

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

		if (User.USER_STATUS_REQ.equals(user.getUserStatus())) {
			throw new UserEmailNotAuthException("이메일 인증 이후에 로그인 해주세요.");
		}

		if (User.USER_STATUS_STOP.equals(user.getUserStatus())) {
			throw new StopUserException("정지된 회원입니다.");
		}

		if (User.USER_STATUS_WITHDRAW.equals(user.getUserStatus())) {
			throw new StopUserException("탈퇴 회원입니다.");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (user.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(
				user.getUserId(), user.getPassword(), grantedAuthorities
			);
	}
}
