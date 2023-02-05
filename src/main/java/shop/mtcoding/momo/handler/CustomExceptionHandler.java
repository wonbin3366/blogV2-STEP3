package shop.mtcoding.momo.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.momo.handler.ex.CustomException;
import shop.mtcoding.momo.util.Script;

@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String customException(CustomException e) {
        return Script.back(e.getMessage());
    }
}
