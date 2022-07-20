package com.stim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().permitAll()	// 나머지주소는 인증없이 접근 가능하다
		.and()
	        .formLogin()					// form기반의 로그인인 경우
	            .loginPage("/loginForm")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
	            .usernameParameter("id")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
	            .passwordParameter("password")		// 로그인 시 form에서 가져올 값
	            .loginProcessingUrl("/login")	// 로그인을 처리할 URL 입력
	            .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
	            .failureUrl("/loginForm")		//로그인 실패 시 /loginForm으로 이동
        .and()
	        .logout()						// logout할 경우
	        	.logoutUrl("/logout")			// 로그아웃을 처리할 URL 입력
	            .logoutSuccessUrl("/");			// 로그아웃 성공 시 "/"으로 이동
		
		return http.build();
    }
	
}
