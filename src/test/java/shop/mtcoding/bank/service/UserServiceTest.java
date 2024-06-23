package shop.mtcoding.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.domain.user.dto.user.UserReqDto;
import shop.mtcoding.bank.domain.user.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.bank.domain.user.dto.user.UserRespDto;
import shop.mtcoding.bank.domain.user.dto.user.UserRespDto.JoinRespDto;


//Spring 관련 Bean들이 하나도 없는 환경
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyObject {
    @InjectMocks
    private UserService userService;
    @Mock //가짜로 메모리에 띄운다음 @InjectMocks 에 주입 , stub 필요
    private UserRepository userRepository;

    @Spy //진짜로 메모리에 띄운다음 @InjectMocks 에 주입 , 진짜 를 가짜 환경에 주입
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void 회원가입_Test() throws Exception {
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@gmail.com");
        joinReqDto.setFullname("쌀");

        //stub1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //빈 객체
        
//        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        //중복된 유저
        
        //stub2
        User ssar = newMockUser(1L,"ssar","쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        //when
        JoinRespDto joinRespDto = userService.membership(joinReqDto);
        System.out.println("테스트 : " + joinRespDto);
        //then

        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}
