package com.project.community.admin.dto;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDto {

	String userId;

	String userName;
	String phoneNumber;
	String password;
	LocalDateTime regDt;
	// 회원정보 수정일
	LocalDateTime udtDt;

	boolean emailAuthYn;
	LocalDateTime emailAuthDt;
	String emailAuthKey;

	String resetPasswordKey;
	LocalDateTime resetPasswordLimitDt;

	// 관리자 여부 지정
	boolean adminYn;

	// 회원 상태 (이용가능한 상태, 정지 상태)
	String userStatus;

	// 주소
	String zipcode;
	String addr;
	String addrDetail;

	// indexing 위한 추가컬럼
	long totalCount;
	long seq; // sequence 처리
}
