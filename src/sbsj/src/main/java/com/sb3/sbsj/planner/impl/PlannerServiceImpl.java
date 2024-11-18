package com.sb3.sbsj.planner.impl;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.planner.dto.SearchPlannerDto;
import com.sb3.sbsj.planner.mapper.IPlannerMybatisMapper;
import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlannerServiceImpl implements PlannerService {

    @Autowired
    private IPlannerMybatisMapper mapper;

    @Override
    public PlannerDto insertPlanner(CUDInfoDto cudInfoDto, PlannerDto dto) {
        //플래너를 삽입할 때 자동 생성된 planId가 Dto에 반영되도록 설정
        dto.setUserId(cudInfoDto.getLoginUser().getId());
        mapper.insertPlanner(dto);
        return dto; //삽입된 플래너 Dto 반환 (planId 포함)
    }

    @Override
    public List<PlannerDto> selectPlannerByUserId(Long userId) {
        List<PlannerDto> planners = mapper.selectPlannerByUserId(userId);
        return planners != null ? planners : new ArrayList<>(); //null 대신 빈 리스트 반환
    }

    @Override
    public PlannerDto findById(Long id) {
        PlannerDto find = mapper.findById(id);
        if ( find == null ) {
            throw new IdNotFoundException(String.format("Error: not found id = %d!", id));
        }
        return find;
    }

    @Override
    public Boolean deletePlanner(Long id) {
        mapper.deletePlanner(id);
        return true;
    }

    @Override
    public PlannerDto updatePlanner(PlannerDto planner) {
        mapper.updatePlanner(planner);
        return planner;
    }

    @Override
    public List<PlannerDto> findAllByNameContains(SearchPlannerDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();
        dto.setFirstIndex(dto.getFirstIndex());
        List<PlannerDto> list = this.mapper.findAllByNameContains(dto);
        return list;
    }

    @Override
    public Integer countAllByNameContains(SearchPlannerDto dto) {
        dto.settingValues();
        return this.mapper.countAllByNameContains(dto);
    }
}
