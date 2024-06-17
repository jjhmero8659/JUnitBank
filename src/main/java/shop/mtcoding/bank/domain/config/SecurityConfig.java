package shop.mtcoding.bank.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

@Configuration //설정파일 Bean 등록
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    //로그 찍어내기 위함
    @Bean //IoC 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }
    
    //JWT 필터 등록 추가 구현 필요

    //JWT 서버 생성, Session 사용 안함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("디버그 : filterChain 빈 등록됨");
        
        http.headers().frameOptions().disable(); //iframe 허용 안함

        http.csrf().disable(); // enable 상태 이면 post맨 작동 안함

        http.cors().configurationSource(configurationSource()); //교차 플랫폼 접근 허용

        //jSessionId를 서버에서 관리하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //react, 앱 , Security에서 제공하는 기본 로그인 방식을 사용하지 않음
        http.formLogin().disable();

        //httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행 한다. , disable 설정
        http.httpBasic().disable();
        
        //Exception 가로채기
        //Spring ExceptionFilter 에서 처리하는 Error 를 편하게 관리하도록 통합 하여 사용
        http.exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> {
                    CustomResponseUtil.unAuthentication(response, "로그인을 진행해야 합니다.");
                }
        );

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated() //s가 붙은 API는 인증 해야 한다.
                // 최근 공식문서에는 ROLE_ 붙이지 않아도 된다.
                .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN)
                .anyRequest().permitAll(); // 나머지 요청은 허용

        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.info("디버그 : configurationSource 설정이 SecurityFilterChain 에 등록됨");
        
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); //GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); //모든 IP 주소 허용 (프론트 엔드  IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
