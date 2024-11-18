package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.IBase;

public interface IUser extends IBase {
    Long getId();
    void setId(Long id);

    String getLoginId();
    void setLoginId(String loginId);

    String getPassword();
    void setPassword(String password);

    String getNickname();
    void setNickname(String nickname);

    String getGender();
    void setGender(String gender);

    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);

    String getPhoto();
    void setPhoto(String photo);

    default void copyFields(IUser from){
        if(from == null ){
            return;
        }
        if( from.getId() != null){
            this.setId(from.getId());
        }
        if(from.getLoginId() != null && !from.getLoginId().isEmpty()){
            this.setLoginId(from.getLoginId());
        }
        if(from.getPassword() != null && !from.getPassword().isEmpty()){
            this.setPassword(from.getPassword());
        }
        if(from.getNickname() != null && !from.getNickname().isEmpty()){
            this.setNickname(from.getNickname());
        }
        if(from.getGender() != null && !from.getGender().isEmpty()){
            this.setGender(from.getGender());
        }
        if(from.getName() != null && !from.getName().isEmpty()){
            this.setName(from.getName());
        }
        if(from.getEmail() != null && !from.getEmail().isEmpty()){
            this.setEmail(from.getEmail());
        }
        if(from.getPhoto() != null && !from.getPhoto().isEmpty()){
            this.setPhoto(from.getPhoto());
        }
        IBase.super.copyFields(from);
    }

}
