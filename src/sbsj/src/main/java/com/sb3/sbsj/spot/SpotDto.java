package com.sb3.sbsj.spot;

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
public class SpotDto implements ISpot{
    private Long id;
    private String title;
    private String addr1;
    private String tel;
    private String zipcode;
    private String mapX;
    private String mapY;
    private String firstimage;
    private Long areaCodeId;
    private Long contentTypeId;
}
