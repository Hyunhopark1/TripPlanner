package com.sb3.sbsj.planner.controller;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.planner.dto.PlannerSpotDto;
import com.sb3.sbsj.planner.service.PlannerSpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/planners/spots")
public class PlannerSpotApiController implements IResponseController {

    @Autowired
    private PlannerSpotService plannerSpotService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> insertPlannerSpot(Model model, @RequestBody PlannerSpotDto plannerSpot) {
        try {
            if (plannerSpot == null || plannerSpot.getPlanId() <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerSpotDto result = this.plannerSpotService.insertPlannerSpot(plannerSpot);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PatchMapping("")
    public ResponseEntity<ResponseDto> updatePlannerSpot(Model model, @RequestBody PlannerSpotDto plannerSpot) {
        try {
            if (plannerSpot == null || plannerSpot.getId() <= 0 || plannerSpot.getPlanId() <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerSpotDto result = this.plannerSpotService.updatePlannerSpot(plannerSpot);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePlannerSpot(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            Boolean result = this.plannerSpotService.deletePlannerSpot(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerSpotDto find = this.plannerSpotService.findById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", find);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("/planner/{planId}")
    public ResponseEntity<ResponseDto> selectPlannerSpotsByPlanId(Model model, @RequestBody PlannerSpotDto dto) {
        try {
            if (dto == null || dto.getPlanId() <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            List<PlannerSpotDto> list = this.plannerSpotService.selectPlannerSpotsByPlanId(dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", list);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @DeleteMapping("/planner/{planId}")
    public ResponseEntity<ResponseDto> deletePlannerSpotsByPlanId(Model model, @PathVariable Long planId) {
        try {
            PlannerSpotDto plannerSpotDto = PlannerSpotDto.builder()
                    .planId(planId)
                    .build();
            if (planId == null || planId <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            Boolean result = this.plannerSpotService.deletePlannerSpotsByPlanId(plannerSpotDto.getPlanId());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }
}
