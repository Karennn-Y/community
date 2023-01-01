package com.project.community.user.model;

import lombok.Data;

@Data
public class ResetPasswordInput {

	private String userId;
	private String userName;

	// 비밀번호 초기화때 필요
	private String id; // uuid parameter 값
	private String password;
}
