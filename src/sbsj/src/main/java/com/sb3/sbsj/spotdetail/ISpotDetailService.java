package com.sb3.sbsj.spotdetail;


import java.util.List;

public interface ISpotDetailService {
    List<SpotDetailDto> areaDetail(String title, String addr1, String tel, String zipcode);
    SpotDetailDto findById(Long id);

}
