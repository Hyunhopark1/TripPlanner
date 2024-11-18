package com.sb3.sbsj.planner.mapper;

import com.sb3.sbsj.planner.dto.PlannerSpotDto;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface IPlannerSpotMybatisMapper {

    //스팟 삽입
    void insertPlannerSpot(PlannerSpotDto plannerSpot);

    //명소 수정
    void updatePlannerSpot(PlannerSpotDto plannerSpotDto);

    //특정 명소 ID로 스팟 삭제
    void deletePlannerSpot(Long id);

    //특정 명소 ID로 스팟 조회
    PlannerSpotDto findById(Long id);

    //특정 플래너 ID로 스팟 조회
    List<PlannerSpotDto> selectPlannerSpotsByPlanId(PlannerSpotDto dto);

    //특정 플래너 ID로 스팟 삭제 (한 번에 모든 스팟 삭제)
    void deletePlannerSpotsByPlanId(Long planId);
}
