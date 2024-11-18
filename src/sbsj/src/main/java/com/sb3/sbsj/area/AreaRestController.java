package com.sb3.sbsj.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaRestController {
    @Autowired
    private IAreaService areaService;

    @GetMapping("/getAllAreas")
    public ResponseEntity<List<AreaDto>> getAllAreas() {
        List<AreaDto> areas = areaService.getAllAreas();
        return ResponseEntity.ok(areas);
    }
}
