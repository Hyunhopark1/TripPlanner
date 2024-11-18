package com.sb3.sbsj.api.spot;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISpotApiMapper {
    void insertSpot(SpotApiDto spotDto);
}
