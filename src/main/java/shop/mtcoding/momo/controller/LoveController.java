package shop.mtcoding.momo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import shop.mtcoding.momo.dto.ResponseDto;
import shop.mtcoding.momo.dto.love.LoveReq.LoveSaveReqDto;
import shop.mtcoding.momo.handler.ex.CustomApiException;
import shop.mtcoding.momo.model.User;
import shop.mtcoding.momo.service.LoveService;

@RestController // 데이터 리턴
public class LoveController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoveService loveService;

    @DeleteMapping("/love/{id}")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        loveService.좋아요취소(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요취소 성공", null), HttpStatus.OK);
    }

    @PostMapping("/love")
    public ResponseEntity<?> save(@RequestBody LoveSaveReqDto loveSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (loveSaveReqDto.getBoardId() == null) {
            throw new CustomApiException("boardId를 전달해 주세요");
        }
        int loveId = loveService.좋아요(loveSaveReqDto.getBoardId(), principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 성공", loveId), HttpStatus.CREATED);
    }
}
