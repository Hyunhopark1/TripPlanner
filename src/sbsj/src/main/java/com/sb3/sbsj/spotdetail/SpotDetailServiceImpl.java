package com.sb3.sbsj.spotdetail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotDetailServiceImpl implements ISpotDetailService {
    @Autowired
    private ISpotDetailMybatisMapper detailMybatisMapper;

    @Override
    public List<SpotDetailDto> areaDetail(String title, String addr1, String tel, String zipcode) {
        return List.of();
    }

    @Override
    public SpotDetailDto findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        SpotDetailDto find = this.detailMybatisMapper.findById(id);
        return (SpotDetailDto) find;
    }
}
