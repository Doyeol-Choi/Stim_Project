package com.stim.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.stim.service.user.StimOauth2Service;
import com.stim.service.user.StimUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Autowired
    private StimOauth2Service stimOauth2Service;
    
    private final DataSource dataSource;
    
    private final StimUserService stimUserService;
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors()
	    		.configurationSource(corsConfigurationSource())
	    .and()
	    	.rememberMe()	// 로그인 유지를 위한 설정
                .rememberMeParameter("remember-me")
                .userDetailsService(stimUserService)
                .tokenRepository(tokenRepository())
        .and()
        	.csrf().disable()
        .authorizeRequests()
//				.antMatchers("/user/**").authenticated()     
//						// user주소에 대해서 인증을 요구한다
//				.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")	
//						// manager주소는 ROLE_MANAGER권한이나 ROLE_ADMIN권한이 있어야 접근할 수 있다.
//				.antMatchers("/admin/**").hasRole("ROLE_ADMIN")	
//						// admin주소는 ROLE_ADMIN권한이 있어야 접근할 수 있다.
				.anyRequest().permitAll()	// 나머지주소는 인증없이 접근 가능하다
		.and()
	        .formLogin()					// form기반의 로그인인 경우
	            .loginPage("/loginForm")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
	            .usernameParameter("user_id")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
	            .passwordParameter("user_password")	// 로그인 시 form에서 가져올 값
	            .loginProcessingUrl("/login")	// 로그인을 처리할 맵핑 입력
	            .defaultSuccessUrl("/")			// 로그인 성공하면 해당 맵핑으로 이동
	            .failureUrl("/loginForm")		//로그인 실패 시 /loginForm으로 이동
        .and()
	        .logout()						// logout할 경우
	        	.logoutUrl("/logout")			// 로그아웃을 처리할 URL 입력
	            .logoutSuccessUrl("/")	// 로그아웃 성공 시 이동할 맵핑으로 이동
	            .invalidateHttpSession(true)	// 세션 초기화
	            .deleteCookies("JSESSIONID","remember-me") //로그아웃 후 쿠키 삭제
        .and()
			.sessionManagement()
		        .maximumSessions(200) 				//세션 허용 갯수
		        .expiredUrl("/")		 			//세션 만료시 이동할 페이지
		        .sessionRegistry(sesionRegistry())
		        .maxSessionsPreventsLogin(true);	//동시 로그인 차단, false인 경우 기존 세션 만료
					
        http.oauth2Login()				// OAuth2기반의 로그인인 경우
            .loginPage("/loginForm")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
            .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
            .failureUrl("/loginForm")		// 로그인 실패 시 /loginForm으로 이동
            .userInfoEndpoint()				// 로그인 성공 후 사용자정보를 가져온다
            .userService(stimOauth2Service);	//사용자정보를 처리할 때 사용한다
        
		

		// Cross site Request forgery로 사이즈간 위조 요청인데, 즉 정상적인 사용자가 의도치 않은 위조요청을 보내는 것을 의미
		// GET요청을 제외한 상태를 변화시킬 수 있는 POST, PUT, DELETE 요청으로부터 보호하는 csrf를 disable해서 post형식으로 회원가입이 가능하게 한다.

		return http.build();
    }
    
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/resources/**")
            .antMatchers("/css/**")
//            .antMatchers("/vendor/**")
//            .antMatchers("/js/**")
//            .antMatchers("/favicon*/**")
            .antMatchers("/img/**");
    }
	
	// CORS 허용 적용
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");	// 허용할 URL
        configuration.addAllowedHeader("*");	// 허용할 Header
        configuration.addAllowedMethod("*");	// 허용할 Http Method
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    SessionRegistry sesionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    PersistentTokenRepository tokenRepository() {
        // JDBC 기반의 tokenRepository 구현체
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // dataSource 주입
        return jdbcTokenRepository;
    }
	
}
