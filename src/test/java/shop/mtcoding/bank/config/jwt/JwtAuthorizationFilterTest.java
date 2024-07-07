package shop.mtcoding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class JwtAuthorizationFilterTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void authorization_success_test() throws Exception{
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);

        System.out.println("테스트 : " + token);
        // when
        ResultActions resultActions = mvc
                .perform(
                        get("/api/s/hello/test")
                                .header(JwtVO.HEADER, token)
                );
        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void authorization_admin_test() throws Exception{
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);

        System.out.println("테스트 : " + token);
        // when
        ResultActions resultActions = mvc
                .perform(
                        get("/api/admin/hello/test")
                                .header(JwtVO.HEADER, token)
                );
        // then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void authorization_fail_test() throws Exception{
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);

        System.out.println("테스트 : " + token);
        // when
        ResultActions resultActions = mvc
                .perform(
                        get("/api/s/hello/test")
                );
        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}
