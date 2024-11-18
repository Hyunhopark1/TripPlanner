package com.sb3.sbsj.planner.impl;

import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.PlannerSpotDto;
import com.sb3.sbsj.planner.mapper.IPlannerSpotMybatisMapper;
import com.sb3.sbsj.planner.service.PlannerSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlannerSpotServiceImpl implements PlannerSpotService {

    @Autowired
    private IPlannerSpotMybatisMapper plannerSpotMybatisMapper;


    @Override
    public PlannerSpotDto insertPlannerSpot(PlannerSpotDto plannerSpot) {
        this.plannerSpotMybatisMapper.insertPlannerSpot(plannerSpot);
        return plannerSpot;
    }

    @Override
    public PlannerSpotDto updatePlannerSpot(PlannerSpotDto plannerSpotDto) {
        this.plannerSpotMybatisMapper.updatePlannerSpot(plannerSpotDto);
        return plannerSpotDto;
    }

    @Override
    public Boolean deletePlannerSpot(Long id) {
        this.plannerSpotMybatisMapper.deletePlannerSpot(id);
        return true;
    }

    @Override
    public PlannerSpotDto findById(Long id) {
        PlannerSpotDto find = this.plannerSpotMybatisMapper.findById(id);
        return find;
    }

    @Override
    public List<PlannerSpotDto> selectPlannerSpotsByPlanId(PlannerSpotDto dto) {
        List<PlannerSpotDto> list = this.plannerSpotMybatisMapper.selectPlannerSpotsByPlanId(dto);
        return list;
    }

    @Override
    public Boolean deletePlannerSpotsByPlanId(Long planId) {
        this.plannerSpotMybatisMapper.deletePlannerSpotsByPlanId(planId);
        return true;
    }

    //두 날짜 간 일 수 계산
    public Integer getDiffDayCount(Date fromDate, Date toDate) {
        //fromDate와 toDate 사이의 시간 차이를 밀리초 단위로 계산 후 일수로 변환
        return (int) ((toDate.getTime() - fromDate.getTime()) / 1000 / 60 / 60 / 24);
    }

    //두 날짜 간의 일수 계산 후, 그 기간 동안의 날짜들을 순차적으로 리스트에 추가하여 반환
    public List<Date> getDiffDays(Date fromDate, Date toDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        int count = getDiffDayCount(fromDate, toDate);
        cal.add(Calendar.DATE, -1);
        List<Date> result = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            cal.add(Calendar.DATE, 1);
            result.add(cal.getTime());
        }
        return result;
    }
}