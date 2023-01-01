package com.project.community.user.exception;

public class UserEmailNotAuthException extends RuntimeException {
	public UserEmailNotAuthException(String error) {
		super(error);
	}
}
