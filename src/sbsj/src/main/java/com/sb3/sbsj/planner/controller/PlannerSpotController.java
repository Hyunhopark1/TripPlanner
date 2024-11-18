package com.sb3.sbsj.planner.controller;

import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.PlannerSpotDto;
import com.sb3.sbsj.planner.service.PlannerService;
import com.sb3.sbsj.planner.service.PlannerSpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/planners/spots")
public class PlannerSpotController implements IResponseController {
    @Autowired
    private PlannerService plannerService;
    //자기 플래너
    @GetMapping("/make/{planId}")
    public String makePlanner(Model model, @PathVariable Long planId) {
        try{
            makeResponseCheckLogin(model);
            PlannerDto find = plannerService.findById(planId);
            model.addAttribute("planId", find.getId());
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return "redirect:/planner/list?page=1&searchName=";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/makePlanner";
    }

    //자기 플래너
    @GetMapping("/{planId}")
    public String plannerDetail(Model model, @PathVariable Long planId) {
        try{
            makeResponseCheckLogin(model);
            PlannerDto find = plannerService.findById(planId);
            model.addAttribute("planId", find.getId());
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return "redirect:/planner/list?page=1&searchName=";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "planner/plannerDetail";
    }
}
