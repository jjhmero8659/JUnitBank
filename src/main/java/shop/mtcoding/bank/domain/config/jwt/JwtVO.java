package shop.mtcoding.bank.domain.config.jwt;

/*
Secret 키는 노출되면 안된다. (클라우드 ARS - 환경변수, 파일에 있는 것을 읽을 수도 있고!!)
Refresh Token
 */
public interface JwtVO {
    public static final String SECRET = "BANK-PRACTICE"; // HS256 (대칭키)
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer "; //한칸 띄워야함
    public static final String HEADER = "Authorization";
}
