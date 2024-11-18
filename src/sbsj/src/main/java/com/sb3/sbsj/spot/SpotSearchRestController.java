package com.sb3.sbsj.spot;

import com.sb3.sbsj.area.AreaDto;
import com.sb3.sbsj.area.IAreaService;
import com.sb3.sbsj.commons.dto.ResponseCode;
import com.sb3.sbsj.commons.dto.ResponseDto;
import com.sb3.sbsj.commons.exeption.LoginAccessException;
import com.sb3.sbsj.commons.inif.IResponseController;
import com.sb3.sbsj.contentType.ContentTypeDto;
import com.sb3.sbsj.contentType.IContentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spot")
public class SpotSearchRestController implements IResponseController {

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IContentTypeService contentTypeService;

    @Autowired
    private ISpotService spotService;

    @GetMapping ("/area")
    public ResponseEntity<ResponseDto> getAreaAll() {
        try {
            List<AreaDto> result = this.areaService.getAllAreas();
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/content")
    public ResponseEntity<ResponseDto> getContentAll(){
        try{
            List<ContentTypeDto> result = this.contentTypeService.getAllContentType();
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "Ok",result );
        }
        catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/photos/{page}/{title}/{areaCodeId}/{contentTypeId}")
    public ResponseEntity<ResponseDto> getSelectPhotos(
            @PathVariable int page
            , @PathVariable(required = false) String title
            , @PathVariable(required = false) Long areaCodeId
            , @PathVariable(required = false) Long contentTypeId) {
        try {
            SpotSearchDto spotSearchDto = SpotSearchDto.builder()
                    .page(page)
                    .rowsOnePage(6)
                    .title(title)
                    .areaCodeId(areaCodeId)
                    .contentTypeId(contentTypeId)
                    .build();
            if (spotSearchDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }

            // 데이터 가져오기
            List<SpotDto> spots = spotService.findSpotByFilters(spotSearchDto);
            spotSearchDto.setTotal(spotService.countAllByFilters(spotSearchDto));
            spotSearchDto.setDataList(spots);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", spotSearchDto);
            // 200 OK 와 result 데이터를 응답한다.

        }
        catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }


    @GetMapping("/findBySpotTitle")
    public ResponseEntity<ResponseDto> findBySpotTitle(
            Model model
            , @RequestParam Integer page
            , @RequestParam Integer rowsOnePage
            , @RequestParam String title) {
        try {
            makeResponseCheckLogin(model);
            SpotSearchDto spotSearchDto = SpotSearchDto.builder()
                    .page(page)
                    .rowsOnePage(rowsOnePage)
                    .title(title)
                    .areaCodeId(0L)
                    .contentTypeId(0L)
                    .build();
            if (spotSearchDto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            // 데이터 가져오기
            List<SpotDto> spots = spotService.findSpotByFilters(spotSearchDto);
            spotSearchDto.setTotal(spotService.countAllByFilters(spotSearchDto));
            spotSearchDto.setDataList(spots);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", spotSearchDto);
            // 200 OK 와 result 데이터를 응답한다.
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }
}
