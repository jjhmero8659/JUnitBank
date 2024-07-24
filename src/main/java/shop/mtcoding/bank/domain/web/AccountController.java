package shop.mtcoding.bank.domain.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto;
import shop.mtcoding.bank.service.AccountService;


import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> controller(
            @RequestBody @Valid AccountReqDto.AccountSaveReqDto accountSaveReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        System.out.println("Data : " + loginUser.getUser().getId());
        System.out.println("Data : " + loginUser.getUser().getRole());

        AccountRespDto.AccountSaveRespDto accountSaveRespDto = accountService.계좌등록(
                accountSaveReqDto,
                loginUser.getUser().getId()
        );

        return new ResponseEntity<>(
                new ResponseDto<>(1, "계좌등록 성공", accountSaveRespDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/s/checkSessionUser")
    public ResponseEntity<?> controller(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        System.out.println("Data : " + loginUser.getUser().getId());
        System.out.println("Data : " + loginUser.getUser().getRole());

        return null;
    }
}
