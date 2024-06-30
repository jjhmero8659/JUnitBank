package shop.mtcoding.bank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateUtil {
    public static String toStringFormat(LocalDateTime localDateTime){
        // 2024-06-30
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
