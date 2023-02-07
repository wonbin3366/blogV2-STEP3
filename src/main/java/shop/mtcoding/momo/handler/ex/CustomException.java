package shop.mtcoding.momo.handler.ex;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    // HttpStatus = HTTP 응답 상태 코드
    private HttpStatus status;

    public CustomException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public CustomException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }
}