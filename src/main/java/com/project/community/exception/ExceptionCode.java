package com.project.community.exception;

import lombok.Getter;

public enum ExceptionCode {
	MEMBER_NOT_FOUND(404, "사용자 정보가 존재하지 않습니다."),
	ALREADY_AUTHORIZED_ACCOUNT(405, "이미 활성화 된 계정 입니다."),
	ALREADY_EXIST_ID(405, "이미 가입된 아이디 입니다."),
	DATE_IS_NOT_VALID(405, "유효한 날짜가 아닙니다."),
	PASSWORDS_DO_NOT_MATCH(405, "비밀번호가 일치하지 않습니다."),
	CATEGORY_IS_NOT_EXIST(405, "존재하지 않는 카테고리입니다.");

	@Getter
	private int code;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
