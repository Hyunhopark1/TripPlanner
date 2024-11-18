package com.sb3.sbsj.planner.dto;

import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPlannerDto extends SearchAjaxDto {
    private Long userId;
}
