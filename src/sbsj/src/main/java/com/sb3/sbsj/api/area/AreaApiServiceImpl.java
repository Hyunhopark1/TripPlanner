package com.sb3.sbsj.api.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaApiServiceImpl implements IAreaApiService{

    @Autowired
    private IAreaApiMapper areaApiMapper;


    @Override
    public void saveAreaApi(IAreaApi area) {
        areaApiMapper.insertArea((AreaApiDto) area);
    }


    public void saveAreasFromApiResponse(AreaApiResponse areaApiResponse){
        for( AreaApiResponse.Item item : areaApiResponse.getResponse().getBody().getItems().getItem()){
            // Item을 AreaApiDto로 변환
            AreaApiDto areaApiDto = AreaApiDto.builder()
                    .code(Long.parseLong(item.getCode()))
                    .name(item.getName())
                    .build();
            areaApiDto.copyfield(areaApiDto);
            saveAreaApi(areaApiDto);

        }
    }


}
