package com.sb3.sbsj.api.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/areas")
public class AreaApiController {

    @Autowired
    private AreaApiRequset areaApiRequset;

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAreas(){
        try{
            AreaApiResponse areaApiResponse = areaApiRequset.fetchDataFromApi();
            return ResponseEntity.ok(areaApiResponse);
        } catch (Exception e){
            // 예외 처리 로직 - 스택 트레이스를 로그에 기록
            e.printStackTrace(); // 로그에 출력
            // 에러 메시지와 함께 HTTP 500 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching data: " + e.toString());
        }
    }
}
