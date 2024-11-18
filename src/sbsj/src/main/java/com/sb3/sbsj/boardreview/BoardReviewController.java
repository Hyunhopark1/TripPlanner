package com.sb3.sbsj.boardreview;



import com.sb3.sbsj.boardfile.BoardFileDto;
import com.sb3.sbsj.boardfile.BoardFileListDto;
import com.sb3.sbsj.boardfile.IBoardFile;
import com.sb3.sbsj.boardfile.IBoardFileService;
import com.sb3.sbsj.boardfree.BoardFreeDto;
import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.boardlike.BoardLikeDto;
import com.sb3.sbsj.boardlike.IBoardLikeService;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boardReview")
public class BoardReviewController implements IResponseController {

    private final IBoardReviewService boardReviewService;
    private final IBoardLikeService boardLikeService;
    private final IBoardFileService boardFileService;

    // 후기게시글 목록 화면 return
    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto dto, Model model) {
        try {
            makeResponseCheckLogin(model);

            int total = this.boardReviewService.countAllByNameContains(dto);
            List<BoardReviewDto> list = this.boardReviewService.findAllByNameContains(dto);
            dto.setTotal(total);

            model.addAttribute("boardReviewList", list);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardreview/board_list";
    }

    // 후기게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd(Model model) {
        try {
            makeResponseCheckLogin(model);
        }  catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/board_add";
    }

    // 후기게시글 등록 후 목록 화면 return
    @PostMapping("/board_insert")
    @ResponseBody
    public ResponseEntity<?> boardInsert(@ModelAttribute BoardReviewDto dto, Model model
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardReviewService.insert(dto, cudInfoDto.getLoginUser(), files);
            String redirectUrl = "board_list?page=1&searchName=&areaCode=" + dto.getAreaCode();
            return ResponseEntity.ok().body(Map.of("redirect", redirectUrl));
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("redirect", "/selogin/login"));
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred"));
        }

    }

    // 후기게시글 뷰 화면 return
    @GetMapping("/board_view")
    public String boardView(@RequestParam Long id,Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);

            BoardReviewDto dto = BoardReviewDto.builder().build();
            IBoardReview find = this.boardReviewService.findById(id);

            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .createId(cudInfoDto.getLoginUser().getId())
                    .boardId(id)
                    .tbl(new BoardReviewDto().getTbl())
                    .build();
            Integer likeCount = this.boardLikeService.countByTableUserBoard(boardLikeDto);
            find.setUpdateDt(likeCount.toString());

            dto.copyFields(find);

            this.boardReviewService.addViewQty(id);

            // 파일 조회
            IBoardFile boardFile = BoardFileDto.builder()
                    .tbl(new BoardReviewDto().getTbl())
                    .boardId(id)
                    .build();
            List<IBoardFile> files = this.boardFileService.findAllByTblBoardId(boardFile);

            model.addAttribute("boardReviewDto", dto);
            model.addAttribute("tbl", dto.getTbl());
            model.addAttribute("files", files);  // 파일 리스트 추가
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardreview/board_view";
    }

    //후기게시글 삭제 후 게시글 목록화면 redirect
    @GetMapping("/board_delete")
    public String boardDelete(Model model, @RequestParam Long id) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardReviewService.delete(id);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=&areaCode=1";
    }

    //해당 후기게시글의 id를 쿼리스트링으로 받아와서 해당 게시글의 객체를 찾아서 업데이트화면으로 보내고 return
    @GetMapping("/board_modify")
    public String boardModify(@RequestParam Long id,Model model) {
        try {
			CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardReviewDto dto = BoardReviewDto.builder().build();
            IBoardReview find = this.boardReviewService.findById(id);
            dto.copyFields(find);

            model.addAttribute("updateDto",dto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardreview/board_update";
    }

    // 후기게시글 업데이트 후 게시글 목록 화면 redirect
    @PostMapping("/board_update")
    @ResponseBody
    public ResponseEntity<?> boardUpdate(@ModelAttribute BoardReviewDto dto, Model model
            , BoardFileListDto boardFileDtoList
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            this.boardReviewService.update(dto, boardFileDtoList, files);
            String redirectUrl = "board_view?id=" + dto.getId();
            return ResponseEntity.ok().body(Map.of("redirect", redirectUrl));
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("redirect", "/selogin/login"));
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred"));
        }

    }
}
