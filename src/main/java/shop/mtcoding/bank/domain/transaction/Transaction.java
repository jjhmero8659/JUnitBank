package shop.mtcoding.bank.domain.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor //스프링이 User 객체 생성할 때 반 생성자로 new를 하기 때문
@Getter
@EntityListeners(AuditingEntityListener.class) //@CreatedDate , @LastModifiedBy 자동 기입하기 위해 설정
@Table(name = "transaction_tb")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    //논리적으로만 외래키를 맺고 물리적으로는 외래키를 맺지 않음
    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount;
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;
    
    private Long amount;

    //이체나 출금 당시 금액을 남겨 놓기 위함
    private Long withdrawAccountBalance;
    private Long depositAccountBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionEnum gubun;

    //계좌가 사라져도 로그는 남아야 한다.
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy //Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun, String sender, String receiver, String tel, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
