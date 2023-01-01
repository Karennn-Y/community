package com.project.community.user.service;

import com.project.community.user.model.UserInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	// 등록
	boolean register(UserInput parameter);

	// uuid에 해당하는 계정 활성화
	boolean emailAuth(String uuid);
}
