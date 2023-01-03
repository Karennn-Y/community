package com.project.community.configuration;


import com.project.community.configuration.handler.RedirectHandler;
import com.project.community.configuration.handler.UserAuthenticationFailureHandler;
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

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	UserAuthenticationFailureHandler getFailureHandler() {
		return new UserAuthenticationFailureHandler();
	}

	@Bean
	RedirectHandler getRedirectHandler() {
		return new RedirectHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

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
			.successHandler(getRedirectHandler())
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