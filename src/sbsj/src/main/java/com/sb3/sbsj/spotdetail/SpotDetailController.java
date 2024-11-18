package com.sb3.sbsj.spotdetail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spot")
public class SpotDetailController {

    @GetMapping("/spot_detail")
    public String detail(){
        return "spot/spot_detail";
    }

}
