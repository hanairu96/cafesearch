package com.toy.cafesearch.config;

import com.toy.cafesearch.oauth.PrincipalOauth2UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Locale;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final MessageSource messageSource;

    //해당 메소드의 리턴되는 오브젝트를 loC로 등록해줌
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/image/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/cafe/").permitAll()
                .requestMatchers("/cafe/searchResult").permitAll()
                .requestMatchers("/cafe/cafeDetail").permitAll()
                .requestMatchers("/cafe/member/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/cafe/member/enrollMember").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                    .loginPage("/cafe/member/loginPage/")
                    .loginProcessingUrl("/login")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/cafe/member/loginSuccess")
                    //.failureForwardUrl("/cafe/member/loginFailure")
                    .failureHandler((request, response, exception) -> {
                        //BadCredentialsException의 메시지
                        String errorMessage = messageSource.getMessage(exception.getClass().getSimpleName(), null, "문제가 발생했습니다.", Locale.KOREA);
                        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", errorMessage);
                        response.sendRedirect("/cafe/member/loginPage/");
                    })
                    .permitAll()
            )
            .oauth2Login(login -> login
                    .loginPage("/cafe/member/loginPage/")
                    .userInfoEndpoint(point -> point
                            .userService(principalOauth2UserService))
                    .defaultSuccessUrl("/cafe/member/loginSuccess")
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .addLogoutHandler((request, response, authentication) -> {
                        //세션 무효화하는 로그아웃 핸들러
                        HttpSession session = request.getSession();
                        if (session != null){
                           session.invalidate();
                        }
                    })
                    .logoutSuccessHandler((request, response, authentication) -> {
                        //로그아웃 성공시 이동할 페이지 지정 핸들러
                        response.sendRedirect("/cafe/");
                    })
            );

        return http.build();
    }
}
