package com.sb3.sbsj.boardreview;

import com.sb3.sbsj.boardlike.BoardLikeDto;
import com.sb3.sbsj.boardlike.IBoardLikeService;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.security.config.SecurityConfig;
import com.sb3.sbsj.user.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/boardreview")
public class BoardReviewRestController implements IResponseController {

    @Autowired
    private IBoardReviewService boardReviewService;

    @Autowired
    private IBoardLikeService boardLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardReviewService.addLikeQty(cudInfoDto.getLoginUser(), id);
            IBoardReview result = this.getBoardAndLike(id, cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardReviewService.subLikeQty(cudInfoDto.getLoginUser(), id);
            IBoardReview result = this.getBoardAndLike(id, cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }
    private IBoardReview getBoardAndLike(Long id, IUser loginUser) {
        IBoardReview result = this.boardReviewService.findById(id);
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .tbl(new BoardReviewDto().getTbl())
                .createId(loginUser.getId())
                .boardId(id)
                .build();
        Integer likeCount = this.boardLikeService.countByTableUserBoard(boardLikeDto);
        result.setUpdateDt(likeCount.toString());
        return result;
    }
}
