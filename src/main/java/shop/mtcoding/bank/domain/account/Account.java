package shop.mtcoding.bank.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor //스프링이 User 객체 생성할 때 반 생성자로 new를 하기 때문
@Getter
@EntityListeners(AuditingEntityListener.class) //@CreatedDate , @LastModifiedBy 자동 기입하기 위해 설정
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 4)
    private Long number; //계좌 번호

    @Column(nullable = false, length = 4)
    private Long password; //계좌 비밀번호

    @Column(nullable = false)
    private Long balance; //잔액 (기본값 1000원)

    // 항상  ORM 에서 FK의 주인은 Many Entity 쪽 이다.
    //account.getUser().아무필드호출() -> Lazy 발동 , 조회
    //getUser() 까지도 호출 되지 않는다.
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    private User user;

    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
