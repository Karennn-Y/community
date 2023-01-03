package com.project.community.configuration.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RedirectHandler implements AuthenticationSuccessHandler {


	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Authentication authentication)
		throws IOException, ServletException {

		String role = authentication.getAuthorities().toString();

		if (role.contains("ADMIN")){
			httpServletResponse.sendRedirect("/admin/main.do");
		} else {
			httpServletResponse.sendRedirect("/");
		}
	}
}
