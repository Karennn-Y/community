package com.project.community.configuration;


import com.project.community.admin.service.LoginHistoryService;
import com.project.community.configuration.handler.UserAuthenticationFailureHandler;
import com.project.community.configuration.handler.UserAuthenticationSuccessHandler;
import com.project.community.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final LoginHistoryService loginHistoryService;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	UserAuthenticationFailureHandler getFailureHandler() {
		return new UserAuthenticationFailureHandler();
	}
	@Bean
	UserAuthenticationSuccessHandler getSuccessHandler() {
		return new UserAuthenticationSuccessHandler(loginHistoryService);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();

		http.authorizeRequests()
			.antMatchers(
				"/"
							, "/user/register"
							, "/user/email-auth"
							, "/user/find/password"
							, "/user/reset/password"
			)
			.permitAll();

		http.authorizeRequests()
				.antMatchers("/admin/**")
				.hasAuthority("ROLE_ADMIN");

		http.formLogin()
			.loginPage("/user/login")
			.failureHandler(getFailureHandler())
			.successHandler(getSuccessHandler())
			.permitAll();

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/")
					.invalidateHttpSession(true);

		http.exceptionHandling()
			.accessDeniedPage("/error/denied");

		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
			.passwordEncoder(getPasswordEncoder());
		super.configure(auth);
	}


}
