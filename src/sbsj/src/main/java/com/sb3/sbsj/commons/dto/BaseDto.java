package com.sb3.sbsj.commons.dto;

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
public class BaseDto implements IBase{
    private String createDt;
    private Long createId;
    private String createName;

    private String updateDt;
    private String updateName;
    private Long updateId;

    private String deleteDt;
    private Long deleteId;
    private String deleteName;

    private Boolean deleteYn;
}
