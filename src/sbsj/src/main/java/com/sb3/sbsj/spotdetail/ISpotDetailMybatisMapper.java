package com.sb3.sbsj.spotdetail;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISpotDetailMybatisMapper {
    List<SpotDetailDto> areaDetail();
    SpotDetailDto findById(long id);
}
