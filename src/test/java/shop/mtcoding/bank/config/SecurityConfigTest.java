package shop.mtcoding.bank.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
@AutoConfigureMockMvc //Mock(가짜)  환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //가짜환경 
public class SecurityConfigTest {
    
    // 가짜 환경에 등록된 MockMvc를 DI함
    @Autowired
    private MockMvc mvc;
    @Test
    public void authentication_test()throws Exception { //인증 없는 접근 401
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401);
    }

    @Test
    public void authorization_test()throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + httpStatusCode);

        // then
        assertThat(httpStatusCode).isEqualTo(401);
    }
}
