package com.sb3.sbsj.api.spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotApiServiceImpl implements ISpotApiService {
    @Autowired
    ISpotApiMapper spotApiMapper;

    @Override
    public void saveSpot(ISpotApi spot) {
        spotApiMapper.insertSpot((SpotApiDto) spot);
    }

    public void saveSpotDtoFromApiResponse(SpotApiResponse spotApiResponse){
        for(SpotApiResponse.Item item: spotApiResponse.getResponse().getBody().getItems().getItem()){
            if(item.getAreacode().isEmpty() || item.getContenttypeid().isEmpty() ){
                continue;
            }

            // Item을 SpotDto로 변환
            SpotApiDto spotDto = SpotApiDto.builder()
                    .title(item.getTitle())
                    .tel(item.getTel())
                    .addr1(item.getAddr1())
                    .zipcode(item.getZipcode())
                    .mapx(item.getMapx())
                    .mapy(item.getMapy())
                    .firstimage(item.getFirstimage())
                    .areacode(Long.parseLong(item.getAreacode()))
                    .contenttypeid(Long.parseLong(item.getContenttypeid()))
                    .build();

            saveSpot(spotDto);
        }


    }
}
