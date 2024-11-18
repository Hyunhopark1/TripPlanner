package com.sb3.sbsj.planner.controller;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.SearchPlannerDto;
import com.sb3.sbsj.planner.impl.PlannerSpotServiceImpl;
import com.sb3.sbsj.planner.service.PlannerService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/planner")
public class PlannerController implements IResponseController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PlannerService plannerService;

    //자기 플래너
    @GetMapping("/owner")
    public String ownPlanner(Model model, @ModelAttribute("searchPlannerDto") SearchPlannerDto dto) {
        try{
            makeResponseCheckLogin(model);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/ownPlanner";
    }

    //자기 플래너
    @GetMapping("/list")
    public String plannerList(Model model, @ModelAttribute("searchPlannerDto") SearchPlannerDto dto) {
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            //dto.setUserId(cudInfoDto.getLoginUser().getId());
            int total = this.plannerService.countAllByNameContains(dto);
            List<PlannerDto> list = this.plannerService.findAllByNameContains(dto);
            dto.setTotal(total);
            model.addAttribute("plannerList",list);
            model.addAttribute("searchPlannerDto",dto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/planner_list";
    }

    // 자유게시글 등록 화면 return
    @GetMapping("/planner_add")
    public String plannerAdd(Model model) {
        try {
            makeResponseCheckLogin(model);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/planner_add";
    }

    // 자유게시글 등록 후 목록 화면 return
    @PostMapping("/planner_insert")
    public String plannerInsert(@ModelAttribute PlannerDto dto, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.plannerService.insertPlanner(cudInfoDto, dto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/planner/list?page=1&searchName=";
    }

    // 자유게시글 상세보기 화면 return
    @GetMapping("/planner_view")
    public String plannerView(@RequestParam Long id, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerDto find = this.plannerService.findById(id);
            model.addAttribute("plannerDto",find);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/planner_view";
    }

    // 자유게시글 삭제 후 게시판 목록 화면 redirect
    @GetMapping("/planner_delete")
    public String plannerDelete(Model model, @RequestParam Long id){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerDto planner = plannerService.findById(id);

            //작성자와 로그인한 사용자가 같은지 확인
            if (!planner.getUserId().equals(cudInfoDto.getLoginUser().getId())) {
                throw new AccessDeniedException("해당 플래너에 대한 삭제 권한이 없습니다.");
            }
            plannerService.deletePlanner(id);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "redirect:/planner/list?page=1&searchName=";
    }

    // 쿼리스트링 id를 받아와서 BoardFreeDto 객체를 찾아서 업데이트 화면에 보내고 return
    @GetMapping("/planner_modify")
    public String plannerModify(@RequestParam Long id, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerDto dto = this.plannerService.findById(id);

            //작성자와 로그인한 사용자가 같은지 확인
            if (!dto.getUserId().equals(cudInfoDto.getLoginUser().getId())) {
                throw new AccessDeniedException("해당 플래너에 대한 수정 권한이 없습니다.");
            }
            model.addAttribute("plannerDto",dto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/planner_update";
    }

    // 자유게시글 업데이트 후 게시글 목록 화면 redirect
    @PostMapping("/planner_update")
    public String plannerUpdate(@ModelAttribute PlannerDto dto, Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);

            //작성자와 로그인한 사용자가 같은지 확인
            if (!dto.getUserId().equals(cudInfoDto.getLoginUser().getId())) {
                throw new AccessDeniedException("해당 플래너에 대한 수정 권한이 없습니다.");
            }
            this.plannerService.updatePlanner(dto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/planner/list?page=1&searchName=";
    }
}