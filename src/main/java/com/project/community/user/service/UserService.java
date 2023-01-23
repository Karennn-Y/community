package com.project.community.user.service;

import com.project.community.admin.dto.UserDto;
import com.project.community.admin.model.UserParam;
import com.project.community.user.model.ResetPasswordInput;
import com.project.community.user.model.UserInput;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	// 등록
	boolean register(UserInput parameter);

	// uuid에 해당하는 계정 활성화
	boolean emailAuth(String uuid);

	// 입력받은 이메일로 비밀번호 초기화 정보 전송
	boolean sendResetPassword(ResetPasswordInput parameter);

	// 입력받은 uuid에 대해서 password 초기화
	boolean resetPassword(String uuid, String password);

	// uuid 값이 유효한지 확인
	boolean checkResetPassword(String uuid);

	// 회원 목록 리턴(관리자 용)
	List<UserDto> list(UserParam parameter);

	// 회원 상세 정보
	UserDto detail(String userId);

	// 회원 상태 변경
	boolean updateStatus(String userId, String userStatus);

	// 관리자 화면 회원 비밀번호 초기화
	boolean updatePassword(String userId, String password);

	// 회원 정보 업데이트
	boolean updateUser(UserInput parameter);

	// 회원 정보 페이지 비밀번호 변경
	boolean updateUserPassword(UserInput parameter);

	// 회원 탈퇴 로직
	boolean withdraw(String userId, String password);
}
