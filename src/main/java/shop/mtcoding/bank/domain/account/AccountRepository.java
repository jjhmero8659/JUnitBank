package shop.mtcoding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account , Long> {
    //jpa query method
    //checkPoint
    Optional<Account> findByNumber(Long number);
}
