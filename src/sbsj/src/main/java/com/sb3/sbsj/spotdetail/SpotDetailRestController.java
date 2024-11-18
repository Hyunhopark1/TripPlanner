package com.sb3.sbsj.spotdetail;

import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.spot.ISpotService;
import com.sb3.sbsj.spot.SpotDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/spot")
public class SpotDetailRestController implements IResponseController {
    @Autowired
    ISpotService spotService;

    @GetMapping("/spot_detail/{id}")
    public ResponseEntity<ResponseDto> detail(@PathVariable Long id){
        try {
            SpotDto spotDto = spotService.findById(id);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", spotDto);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }
}
