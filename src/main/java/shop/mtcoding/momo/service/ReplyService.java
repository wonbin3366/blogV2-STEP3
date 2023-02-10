package shop.mtcoding.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.momo.controller.ReplyController.ReplySavaReqDto;
import shop.mtcoding.momo.handler.ex.CustomApiException;
import shop.mtcoding.momo.model.ReplyRepository;

@Transactional(readOnly = true)
@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    // where 절에 걸리는 파라미터를 앞에 받기
    @Transactional
    public int 댓글쓰기(ReplySavaReqDto replySavaReqDto, int principalId) {
        // 1. content 내용을 Document로 받고 , img 찾아내서(0,1,2) src를찾아서 thumbnail에 추가
        int result = replyRepository.insert(
                replySavaReqDto.getComment(), replySavaReqDto.getBoardId(), principalId);
        if (result != 1) {
            throw new CustomApiException("댓글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return 1;
    }

}
