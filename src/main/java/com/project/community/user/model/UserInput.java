package com.project.community.user.model;

import lombok.Data;

@Data
public class UserInput {

	private String userId;
	private String userName;
	private String password;
	private String phoneNumber;

	private String newPassword;
	private String zipcode;
	private String addr;
	private String addrDetail;
}
