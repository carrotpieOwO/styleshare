package com.carrot.styleshare.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.carrot.styleshare.model.RespCM;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//모든 리퀘스트 받기
				http.authorizeRequests()
					.antMatchers("/user/profile/**","/user/password/**","/style/write/**","/style/modify/**","/style/delete/**","/timeline/**").authenticated() //얘는 인증 필요하다.
					.anyRequest().permitAll()
				.and()
					.formLogin()
					.loginPage("/user/login") 
					.loginProcessingUrl("/user/login") 
				//.defaultSuccessUrl("/"); //successHandler로 사용할 수도 있음. 로그인 성공하면 이곳으로 이동해라. //근데 이거 하면 제이슨으로 보내는게 아니어서 ajax 못씀
					.successHandler(new AuthenticationSuccessHandler() {
						//익명메소드 만들기, 리퀘스트 리스폰스 authentication 도 다 들고잇음.
		                //이동 전에 뭔가 작업할 수 있다.  
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {	
							PrintWriter out = response.getWriter();
							//제이슨으로 바꿔 보내주기
							ObjectMapper mapper = new ObjectMapper(); 
		                     //objectMapper -> 지선처럼 자바오브젝트 제이슨으로 바꿔주는거
							String jsonString = mapper.writeValueAsString(new RespCM(200,"ok"));
							out.print(jsonString);
							out.flush();
						}
					});
				
	}
	
				@Autowired
				private UserDetailsService userDetailsService;
				
				//로그인할때 암호화 다시 풀어주기 
				@Override
				protected void configure(AuthenticationManagerBuilder auth) throws Exception {
					auth.userDetailsService(userDetailsService).passwordEncoder(encode());
	}
}
