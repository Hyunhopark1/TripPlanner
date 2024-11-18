package com.sb3.sbsj.planner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlannerDto {
    private Long id;  // 플래너 ID
    private String title; // 플래너 제목
    private Long userId; // 작성자 ID 값
    private String userName; // 작성자 이름

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = "yyyy-MM-dd")
    private String startDate; // 여행 시작 날짜

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate; // 여행 종료 날짜

    private String memo; // 플래너 메모
    private int people; // 인원 수

}
