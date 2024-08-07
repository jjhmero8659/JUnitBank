package shop.mtcoding.bank.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import shop.mtcoding.bank.config.auth.LoginUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//모든 주소에서 동작함 (토큰 검증)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        if (isHeaderVerify(request, response)) {
            // 토큰이 존재하는 상태
            String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
            LoginUser loginUser = JwtProcess.verify(token);

            //임시 세션 (UserDeatils 타입 or username)
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            loginUser,
                            null,
                            loginUser.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("SecurityContextHolder 유저 정보 등록 : {}" , authentication);
        }

        chain.doFilter(request,response); //필터가 없으면 Controller 로 이동
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtVO.HEADER);

        if (header == null || !header.startsWith(JwtVO.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
