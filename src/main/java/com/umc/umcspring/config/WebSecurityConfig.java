package com.umc.umcspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      // 스프링 설정 클래스를 선언하는 어노테이션
@EnableWebSecurity  // SpringSecurity 사용을 위한 어노테이션, 기본적으로 CSRF 활성화
// SpringSecurity란, Spring기반의 애플리케이션의 보안(인증, 권한, 인가 등)을 담당하는 Spring 하위 프레임워크
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SpringSecurity 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable();

//        http
//                .authorizeRequests()
//                .antMatchers("/manager/**")
//                .access("hasRole('MANAGER') or hasRole('ADMIN')")
//                .antMatchers("/auth/**")
//                .authenticated()
//                .anyRequest()
//                .permitAll()
//                .and()
//                .formLogin().loginPage("/loginForm")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/");


        return http.build();
    }
}
