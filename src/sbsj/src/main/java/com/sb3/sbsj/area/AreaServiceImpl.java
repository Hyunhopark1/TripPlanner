package com.sb3.sbsj.area;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements IAreaService{
    @Autowired
    private IAreaMybatisMapper areaMybatisMapper;

    @Override
    public List<AreaDto> getAllAreas() {
        return areaMybatisMapper.getAllAreas();
    }

}
