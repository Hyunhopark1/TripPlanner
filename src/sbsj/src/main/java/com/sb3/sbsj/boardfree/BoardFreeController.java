package com.sb3.sbsj.boardfree;


import com.sb3.sbsj.boardfile.BoardFileDto;
import com.sb3.sbsj.boardfile.BoardFileListDto;
import com.sb3.sbsj.boardfile.IBoardFile;
import com.sb3.sbsj.boardfile.IBoardFileService;
import com.sb3.sbsj.boardcomment.IBoardCommentService;
import com.sb3.sbsj.boardlike.BoardLikeDto;
import com.sb3.sbsj.boardlike.IBoardLikeService;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boardFree")
public class BoardFreeController implements IResponseController {

    private final IBoardFreeService boardFreeService;
    private final IBoardLikeService boardLikeService;
    private final IBoardFileService boardFileService;

    // 자유게시글 목록 화면 return
    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto dto, Model model) {
        try {
            makeResponseCheckLogin(model);

            int total = this.boardFreeService.countAllByNameContains(dto);
            List<BoardFreeDto> list = this.boardFreeService.findAllByNameContains(dto);
            dto.setTotal(total);

            model.addAttribute("boardList",list);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardfree/board_list";
    }

    // 자유게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd(Model model) {
        try {
            makeResponseCheckLogin(model);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/board_add";
    }

    @PostMapping("/board_insert")
    @ResponseBody
    public ResponseEntity<?> boardInsert(@ModelAttribute BoardFreeDto dto, Model model
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardFreeService.insert(dto, cudInfoDto.getLoginUser(), files);
            String redirectUrl = "board_list?page=1&searchName=";
            return ResponseEntity.ok().body(Map.of("redirect", redirectUrl));
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("redirect", "/selogin/login"));
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred"));
        }

    }


    // 자유게시글 상세보기 화면 return
    @GetMapping("/board_view")
    public String boardView(@RequestParam Long id, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            Long userId = cudInfoDto.getLoginUser().getId();
            // 게시글 정보 조회
            BoardFreeDto dto = BoardFreeDto.builder().build();
            IBoardFree find = this.boardFreeService.findById(id);

            // 좋아요 개수 조회
            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .tbl(new BoardFreeDto().getTbl())
                    .createId(cudInfoDto.getLoginUser().getId())
                    .boardId(id)
                    .build();
            Integer likeCount = this.boardLikeService.countByTableUserBoard(boardLikeDto);
            find.setUpdateDt(likeCount.toString());
            dto.copyFields(find);
            
            
            // 조회수 증가
            this.boardFreeService.addViewQty(id,userId);

            // 파일 조회
            IBoardFile boardFile = BoardFileDto.builder()
                    .tbl(new BoardFreeDto().getTbl())
                    .boardId(id)
                    .build();
            List<IBoardFile> files = this.boardFileService.findAllByTblBoardId(boardFile);

            model.addAttribute("boardFreeDto", dto);
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
        return "boardfree/board_view";
    }


    // 자유게시글 삭제 후 게시판 목록 화면 redirect
    @GetMapping("/board_delete")
    public String boardDelete(Model model, @RequestParam Long id){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardFreeService.delete(id);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=";
    }

    // 쿼리스트링 id를 받아와서 BoardFreeDto 객체를 찾아서 업데이트 화면에 보내고 return
    @GetMapping("/board_modify")
    public String boardModify(@RequestParam Long id, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardFreeDto dto = BoardFreeDto.builder().build();
            IBoardFree find = this.boardFreeService.findById(id);
            dto.copyFields(find);
            model.addAttribute("updateDto",dto);

            IBoardFile boardFile = BoardFileDto.builder()
                    .tbl(new BoardFreeDto().getTbl())
                    .boardId(id)
                    .build();
            List<IBoardFile> files = this.boardFileService.findAllByTblBoardId(boardFile);

            model.addAttribute("files", files);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardfree/board_update";
    }

    // 자유게시글 업데이트 후 게시글 목록 화면 redirect
    @PostMapping("/board_update")
    @ResponseBody
    public ResponseEntity<?> boardUpdate(@ModelAttribute BoardFreeDto dto, Model model
            , BoardFileListDto boardFileDtoList
            , @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try {
            this.boardFreeService.update(dto, boardFileDtoList, files);
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
