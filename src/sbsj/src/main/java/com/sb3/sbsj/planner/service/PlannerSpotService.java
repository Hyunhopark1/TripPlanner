package com.sb3.sbsj.planner.service;

import com.sb3.sbsj.planner.dto.PlannerDto;
import com.sb3.sbsj.planner.dto.PlannerSpotDto;

import java.util.Date;
import java.util.List;

public interface PlannerSpotService {

    //스팟 삽입
    PlannerSpotDto insertPlannerSpot(PlannerSpotDto plannerSpot);

    //명소 수정
    PlannerSpotDto updatePlannerSpot(PlannerSpotDto plannerSpotDto);

    //특정 명소 ID로 스팟 삭제
    Boolean deletePlannerSpot(Long id);

    //특정 명소 ID로 스팟 조회
    PlannerSpotDto findById(Long id);

    //특정 플래너 ID로 스팟 조회
    List<PlannerSpotDto> selectPlannerSpotsByPlanId(PlannerSpotDto dto);

    //특정 플래너 ID로 스팟 삭제 (한 번에 모든 스팟 삭제)
    Boolean deletePlannerSpotsByPlanId(Long planId);

    //두 날짜 간 일 수 계산
    Integer getDiffDayCount(Date fromDate, Date toDate);

    //두 날짜 간의 일 수를 기준으로 날짜 리스트 반환
    List<Date> getDiffDays(Date fromDate, Date toDate);

//    //Dto를 엔티티로 전환
//    default PlannerSpotDto entityToDto(PlannerSpotDto entity) {
//        return PlannerSpotDto.builder()
//                .planSpId(entity.getPlanSpId())
//                .spotDate(entity.getSpotDate())
//                .time(entity.getTime())
//                .spotMemo(entity.getSpotMemo())
//                .howFar(entity.getHowFar())
//                .build();
//    }
//
//    //엔티티를 Dto로 전환
//    default PlannerSpotDto dtoToEntity(PlannerSpotDto dto) {
//        return PlannerSpotDto.builder()
//                .planSpId(dto.getPlanSpId())
//                .spotDate(dto.getSpotDate())
//                .time(dto.getTime())
//                .spotMemo(dto.getSpotMemo())
//                .howFar(dto.getHowFar())
//                .build();
//    }
}
