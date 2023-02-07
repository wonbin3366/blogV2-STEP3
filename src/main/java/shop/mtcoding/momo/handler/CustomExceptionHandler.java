package shop.mtcoding.momo.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.momo.handler.ex.CustomException;
import shop.mtcoding.momo.util.Script;


//RestcontrolletAdvice는 @ControllerAdvice와 @ResponseBody를 합쳐놓은것이다
//@ControllerAdvice는 @ExceptionHandler가 있는 컨트롤러단에 적용하기 위한 어노테이션이다.
// @ControllerAdvice는 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리해주는 annotation이다.
//API서버여서 에러 응답으로 객체를 리턴해야한다면 @ResponseBody 어노테이션이 추가된 @RestControllerAdvice를 적용하면 되는 것이다.
@RestControllerAdvice
public class CustomExceptionHandler {

    //@ExceptionHandler 는  controller 에서 발생하는 에러를 잡아서 메서드로 처리해주는 기능이다
    //ResponseEntity 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다.
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        String responseBody = Script.back(e.getMessage());
        return new ResponseEntity<>(responseBody, e.getStatus());
    }
}