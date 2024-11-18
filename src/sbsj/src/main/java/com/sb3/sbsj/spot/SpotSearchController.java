package com.sb3.sbsj.spot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/spot")
public class SpotSearchController {

    @GetMapping("/spot_search")
    private String spotSearch(){
        return "spot/spot_search";
    }

}
