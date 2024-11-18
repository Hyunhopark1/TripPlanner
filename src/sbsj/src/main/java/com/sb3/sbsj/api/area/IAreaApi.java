package com.sb3.sbsj.api.area;

public interface IAreaApi {
    Long getCode();
    void setCode(Long code);

    String getName();
    void setName(String name);

    default void copyfield(IAreaApi from){
        if( from == null ){
            return;
        }
        if( from.getCode() != null ){
            this.setCode(from.getCode());
        }
    }
}
