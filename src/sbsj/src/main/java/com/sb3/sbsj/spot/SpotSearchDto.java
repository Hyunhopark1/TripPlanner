package com.sb3.sbsj.spot;

import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class SpotSearchDto extends SearchAjaxDto {
    private Long areaCodeId;
    private Long contentTypeId;
    private String title;
}
