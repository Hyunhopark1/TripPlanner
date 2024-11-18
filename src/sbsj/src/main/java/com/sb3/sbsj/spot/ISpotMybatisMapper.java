package com.sb3.sbsj.spot;

import com.sb3.sbsj.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISpotMybatisMapper extends IMybatisCRUD<SpotDto> {
    List<SpotDto> findSpotByFilters(SpotSearchDto spotSearchDto);
    Integer countAllByFilters(SpotSearchDto spotSearchDto);
    SpotDto findById(Long id);
}
