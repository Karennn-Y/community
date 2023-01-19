package com.project.community.user.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User implements UserCode {

	@Id
	private String userId;

	private String userName;
	private String nickname;
	private String phoneNumber;
	private String password;
	private LocalDateTime regDt;
	// 회원정보 수정일
	private LocalDateTime udtDt;

	private boolean emailAuthYn;
	private LocalDateTime emailAuthDt;
	private String emailAuthKey;

	private String resetPasswordKey;
	private LocalDateTime resetPasswordLimitDt;

	// 관리자 여부 지정
	private boolean adminYn;

	// 회원 상태 (이용가능한 상태, 정지 상태)
	private String userStatus;

	// 주소
	private String zipcode;
	private String addr;
	private String addrDetail;



}
