package com.project.community.admin.dto;


import com.project.community.user.entity.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	LocalDateTime loginDt;

	public static UserDto of (User user) {

		return UserDto.builder()
			.userId(user.getUserId())
			.userName(user.getUserName())
			.phoneNumber(user.getPhoneNumber())
			.regDt(user.getRegDt())
			.udtDt(user.getUdtDt())
			.emailAuthYn(user.isEmailAuthYn())
			.emailAuthDt(user.getEmailAuthDt())
			.resetPasswordKey(user.getResetPasswordKey())
			.resetPasswordLimitDt(user.getResetPasswordLimitDt())
			.adminYn(user.isAdminYn())
			.userStatus(user.getUserStatus())
			.zipcode(user.getZipcode())
			.addr(user.getAddr())
			.addrDetail(user.getAddrDetail())
			.build();

	}

	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return regDt != null ? regDt.format(formatter) : "";
	}

	public String getLoginDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return loginDt != null ? loginDt.format(formatter) : "";
	}
}
