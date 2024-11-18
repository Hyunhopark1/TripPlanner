package com.sb3.sbsj.spot;


import com.sb3.sbsj.commons.inif.IServiceCRUD;

import java.util.List;

public interface ISpotService extends IServiceCRUD<SpotDto> {
    List<SpotDto> findSpotByFilters(SpotSearchDto spotSearchDto);
    Integer countAllByFilters(SpotSearchDto spotSearchDto);
    SpotDto findById(Long id);
}
