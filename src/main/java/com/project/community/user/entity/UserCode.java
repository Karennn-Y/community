package com.project.community.user.entity;


public interface UserCode {
	// 현재 가입 요청중
	String MEMBER_STATUS_REQ = "REQ";

	// 현재 이용중인 상태
	String MEMBER_STATUS_ING = "ING";

	// 현재 정지 상태
	String MEMBER_STATUS_STOP = "STOP";

	// 현재 탈퇴 상태
	String MEMBER_STATUS_WITHDRAW = "WITHDRAW";

}
