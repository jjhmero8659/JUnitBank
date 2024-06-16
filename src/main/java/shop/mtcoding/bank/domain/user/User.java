package shop.mtcoding.bank.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor //스프링이 User 객체 생성할 때 반 생성자로 new를 하기 때문
@Getter
@EntityListeners(AuditingEntityListener.class) //@CreatedDate , @LastModifiedBy 자동 기입하기 위해 설정
@Table(name = "user_tb")
@Entity
public class User { //extends 시간설정 (상속)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false , length = 20)
    private String username;

    @Column(nullable = false , length = 60) //패스워드 인코딩 시 길이가 증가 하기 때문에 넉넉하게 할당
    private String password;

    @Column(nullable = false , length = 20)
    private String email;

    @Column(nullable = false , length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; //ADMIN , CUSTOMER

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
