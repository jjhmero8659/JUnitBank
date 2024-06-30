package shop.mtcoding.bank.handler.ex;
// 추후 사용할 예정
public class CustomForbiddenException extends RuntimeException{
    public CustomForbiddenException(String message) {
        super(message);
    }
}
