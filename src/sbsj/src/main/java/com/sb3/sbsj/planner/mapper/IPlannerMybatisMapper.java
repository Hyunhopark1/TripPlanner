package com.sb3.sbsj.planner.mapper;

import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.SearchPlannerDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPlannerMybatisMapper {
    //플래너 추가
    void insertPlanner(PlannerDto planner);

    //사용자 ID로 플래너 목록 조회
    List<PlannerDto> selectPlannerByUserId(Long userId);

    //플래너 ID로 플래너 조회
    PlannerDto findById(Long id);

    //플래너 삭제
    void deletePlanner(Long id);

    //플래너 수정
    void updatePlanner(PlannerDto planner);

    List<PlannerDto> findAllByNameContains(SearchPlannerDto dto);
    Integer countAllByNameContains(SearchPlannerDto dto);
}