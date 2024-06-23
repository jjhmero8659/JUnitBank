package shop.mtcoding.bank.service;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.domain.user.dto.user.UserReqDto;
import shop.mtcoding.bank.domain.user.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.bank.domain.user.dto.user.UserRespDto;
import shop.mtcoding.bank.domain.user.dto.user.UserRespDto.JoinRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //서비스는 DTO를 요청받고, DTO로 응답한다.
    @Transactional
    public JoinRespDto membership(JoinReqDto joinReqDto){
        //1. 동일 유저네임 존재 검사
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());

        if (userOp.isPresent()){
            //유저네임 중복 되었다는 뜻
            throw new CustomApiException("동일한 username 이 존재 합니다.");
        }

        //2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        //3. dto 응답
        return new JoinRespDto(userPS);
    }




}
