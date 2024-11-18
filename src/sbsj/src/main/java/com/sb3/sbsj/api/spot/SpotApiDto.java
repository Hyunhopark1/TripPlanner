package com.sb3.sbsj.api.spot;

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

public class SpotApiDto implements ISpotApi {
    private Long id;
    private String title;
    private String addr1;
    private String tel;
    private String zipcode;
    private String mapx;
    private String mapy;
    private String firstimage;
    private Long areacode;
    private Long contenttypeid;


}
