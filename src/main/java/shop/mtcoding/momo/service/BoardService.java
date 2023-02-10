package shop.mtcoding.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.momo.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.momo.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.momo.handler.ex.CustomApiException;
import shop.mtcoding.momo.model.Board;
import shop.mtcoding.momo.model.BoardRepository;
import shop.mtcoding.momo.util.HtmlParser;

@Transactional(readOnly = true)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // where 절에 걸리는 파라미터를 앞에 받기
    @Transactional
    public int 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {
        // 1. content 내용을 Document로 받고 , img 찾아내서(0,1,2) src를찾아서 thumbnail에 추가
        String thumbnail = HtmlParser.getThumbnail(boardSaveReqDto.getContent());

        int result = boardRepository.insert(
                boardSaveReqDto.getTitle(),
                boardSaveReqDto.getContent(),
                thumbnail,
                userId);
        if (result != 1) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return 1;
    }

    @Transactional
    public void 게시글삭제(int id, int userId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("없는 게시글을 삭제할 수 없습니다");
        }
        if (boardPS.getUserId() != userId) {
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)
        }
    }

    @Transactional
    public void 게시글수정(int id, BoardUpdateReqDto boardUpdateReqDto, int principalId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("해당 게시글을 찾을 수 없습니다");
        }
        if (boardPS.getUserId() != principalId) {
            throw new CustomApiException("게시글을 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        String thumbnail = HtmlParser.getThumbnail(boardUpdateReqDto.getContent());
        int result = boardRepository.updateById(id, boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent(),
                thumbnail);
        if (result != 1) {
            throw new CustomApiException("게시글 수정에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
