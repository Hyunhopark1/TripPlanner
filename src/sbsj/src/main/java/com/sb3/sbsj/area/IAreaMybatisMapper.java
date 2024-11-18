package com.sb3.sbsj.area;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAreaMybatisMapper {
        List<AreaDto> getAllAreas();
}
