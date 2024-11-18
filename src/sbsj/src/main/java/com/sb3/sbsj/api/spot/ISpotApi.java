package com.sb3.sbsj.api.spot;

public interface ISpotApi {
    Long getId();
    void setId(Long id);

    String getTitle();
    void setTitle(String title);

    String getAddr1();
    void setAddr1(String addr1);

    String getTel();
    void setTel(String tel);

    String getZipcode();
    void setZipcode(String zipcode);

    String getMapx();
    void setMapx(String mapx);

    String getMapy();
    void setMapy(String mapy);

    String getFirstimage();
    void setFirstimage(String firstimage);

    Long getAreacode();
    void setAreacode(Long areacode);

    Long getContenttypeid();
    void setContenttypeid(Long contenttypeid);

}
