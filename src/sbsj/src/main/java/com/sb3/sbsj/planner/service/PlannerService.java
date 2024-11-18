package com.sb3.sbsj.planner.service;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.SearchPlannerDto;

import java.text.ParseException;
import java.util.List;

public interface PlannerService {
    PlannerDto insertPlanner(CUDInfoDto cudInfoDto, PlannerDto dto);
    List<PlannerDto> selectPlannerByUserId(Long userId);
    PlannerDto findById(Long id);
    Boolean deletePlanner(Long id);
    PlannerDto updatePlanner(PlannerDto planner);
    List<PlannerDto> findAllByNameContains(SearchPlannerDto dto);
    Integer countAllByNameContains(SearchPlannerDto dto);
}