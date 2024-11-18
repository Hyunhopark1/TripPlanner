package com.sb3.sbsj.commons.dto;

import com.sb3.sbsj.user.IUser;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CUDInfoDto {
    private final IUser loginUser;

    public CUDInfoDto(IUser loginUser) {
        this.loginUser = loginUser;
    }

    private String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    public void setCreateInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setCreateDt(this.getSystemDt());
        iBase.setCreateId(loginUser.getId());
    }

    public void setUpdateInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setUpdateDt(this.getSystemDt());
    }

    public void setDeleteInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setDeleteDt(this.getSystemDt());
    }
}
