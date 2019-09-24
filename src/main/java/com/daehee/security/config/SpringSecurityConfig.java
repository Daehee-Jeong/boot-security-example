package com.daehee.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@ComponentScan(basePackages = {"com.daehee.security.*"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthProvider authProvider;

    @Autowired
    AuthFailureHandler authFailureHandler;

    @Autowired
    AuthSuccessHandler authSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 허용되어야 할 경로들
        web.ignoring().antMatchers("/resources/**",
                                    "/css/**",
                                    "/js/**",
                                    "/image/**",
                                    "/common/**",
                                    "/console/**",
                                    "/user/join");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // csrf 사용유무 설정
        // csrf 설정을 사용하면 모든 request에 csrf 값을 함께 전달해야한다.
        http.csrf().disable()
            // 로그인 설정
            .authorizeRequests()
            .antMatchers("/", "/user/login/**", "/error**").permitAll()
            .antMatchers("/**").access("ROLE_USER")
            .antMatchers("/**").access("ROLE_ADMIN")
            .antMatchers("/admin/**").access("ROLE_ADMIN")
            .antMatchers("/**").authenticated()
        .and()
            // 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
            .formLogin()
            .loginPage("/user/login")
            .loginProcessingUrl("/user/login/authenticate")
            .defaultSuccessUrl("/")
            .failureHandler(authFailureHandler)
            .successHandler(authSuccessHandler)
            .usernameParameter("id")
            .passwordParameter("password")
        .and()
            // 로그아웃 관련 설정
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        .and()
            // 로그인 프로세스가 진행될 provicer
            .authenticationProvider(authProvider);
    }
}
