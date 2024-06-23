package shop.mtcoding.bank.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.handler.ex.CustomValidationException;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect //관점
public class CustomValidationAdvice {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){
        
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void putMapping(){
        
    }

    //post (body), put (body)
    @Around("postMapping() || putMapping()") // joinPoint의 전후 제어
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinPoint 의 매개변수

        for (Object arg : args){
            if (arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()){ //Error 발생 했을 경우
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()){
                        errorMap.put(error.getField() , error.getDefaultMessage());
                    }

                    throw new CustomValidationException("유효성 검사 실패" , errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed(); //정상적으로 해당 메서드를 실행 해라!!
    }
    
    //get , delete,
}
