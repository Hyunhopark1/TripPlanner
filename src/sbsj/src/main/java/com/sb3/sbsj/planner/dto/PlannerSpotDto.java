package com.sb3.sbsj.planner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlannerSpotDto {
    private Long id; // 명소의 아이디
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String spotDate; // 날짜
    private Integer time; // 명소 별 시간
    private String spotMemo; // 명소 별 메모
    private String whatDay; // 요일
    private Float howFar; // 명소 간 거리
    private Long spotId; //플래너 ID
    private String title;
    private String addr1;
    private String tel;
    private String mapX;
    private String mapY;
    private String firstimage;
    private Long planId; //플래너 ID
}
