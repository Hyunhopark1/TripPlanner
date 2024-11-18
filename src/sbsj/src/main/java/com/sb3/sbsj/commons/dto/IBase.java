package com.sb3.sbsj.commons.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBase {
    String getCreateDt();
    void setCreateDt(String createDt);

    Long getCreateId();
    void setCreateId(Long createId);

    String getCreateName();
    void setCreateName(String createName);

    String getUpdateDt();
    void setUpdateDt(String updateDt);

    String getDeleteDt();
    void setDeleteDt(String deleteDt);

    Boolean getDeleteYn();
    void setDeleteYn(Boolean deleteYn);

    default void copyFields(IBase from){
        if(from == null){
            return;
        }
        if(from.getCreateDt() != null && !from.getCreateDt().isEmpty()){
            this.setCreateDt(from.getCreateDt());
        }
        if(from.getCreateId() != null){
            this.setCreateId(from.getCreateId());
        }
        if(from.getCreateName() != null && !from.getCreateName().isEmpty()){
            this.setCreateName(from.getCreateName());
        }
        if(from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()){
            this.setUpdateDt(from.getUpdateDt());
        }
        if(from.getDeleteDt() != null && !from.getDeleteDt().isEmpty()){
            this.setUpdateDt(from.getUpdateDt());
        }
        if(from.getDeleteYn() != null){
            this.setDeleteYn(from.getDeleteYn());
        }
    }

    default String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    default void setCreateInfo(Long memberId) {
        this.setCreateDt(this.getSystemDt());
        this.setCreateId(memberId);
    }

    default void setUpdateInfo(Long memberId) {
        this.setUpdateDt(this.getSystemDt());
    }

    default void setDeleteInfo(Long memberId) {
        this.setDeleteDt(this.getSystemDt());
    }
}