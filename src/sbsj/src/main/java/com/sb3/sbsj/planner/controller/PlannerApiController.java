package com.sb3.sbsj.planner.controller;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.service.PlannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/planner")
public class PlannerApiController implements IResponseController {

    @Autowired
    private PlannerService plannerService;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerDto find = this.plannerService.findById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            PlannerDto planner = plannerService.findById(id);

            //작성자와 로그인한 사용자가 같은지 확인
            if(!planner.getUserId().equals(cudInfoDto.getLoginUser().getId())) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "삭제 권한이 없습니다.", null);
            }
            this.plannerService.deletePlanner(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", true);
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
