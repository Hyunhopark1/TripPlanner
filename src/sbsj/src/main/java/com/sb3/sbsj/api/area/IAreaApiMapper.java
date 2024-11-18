package com.sb3.sbsj.api.area;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAreaApiMapper {
    void insertArea(AreaApiDto areaApi);
}
