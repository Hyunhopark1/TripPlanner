package com.sb3.sbsj.boardcommon;


import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBoardBase {
    Long getCreateId();
    void setCreateId(Long createId);

    String getCreateDt();
    void setCreateDt(String createDt);

    String getCreateName();
    void setCreateName(String createName);

    String getUpdateDt();
    void setUpdateDt(String updateDt);

    Boolean getDeleteYn();
    void setDeleteYn(Boolean deleteYn);

    String getTbl();
    void setTbl(String tbl);
    default String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }
    default void setUpdateInfo(Long memberId) {
        this.setUpdateDt(this.getSystemDt());
    }
}
