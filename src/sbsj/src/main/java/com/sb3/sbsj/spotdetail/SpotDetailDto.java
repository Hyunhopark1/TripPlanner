package com.sb3.sbsj.spotdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SpotDetailDto {
    private Long id;
    private String title;
    private String addr1;
    private String tel;
    private String zipcode;
    private String mapX;
    private String mapY;
    private String firstimage;
}
