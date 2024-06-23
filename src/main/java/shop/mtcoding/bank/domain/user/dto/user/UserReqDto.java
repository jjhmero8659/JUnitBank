package shop.mtcoding.bank.domain.user.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class UserReqDto {
    @Getter
    @Setter
    public static class JoinReqDto{
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
        @NotEmpty
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .createdAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .build();
        }
    }
}
