package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardcommon.IBoardBase;

public interface IBoardComment extends IBoardBase {
    Long getId();
    void setId(Long id);

    String getComment();
    void setComment(String comment);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    Long getBoardId();
    void setBoardId(Long boardId);


    default void copyFields(IBoardComment from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getComment() != null && !from.getComment().isEmpty()) {
            this.setComment(from.getComment());
        }
        if (from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
        if (from.getCreateDt() != null && !from.getCreateDt().isEmpty()) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getDeleteYn() != null) {
            this.setDeleteYn(from.getDeleteYn());
        }
        if (from.getCreateName() != null) {
            this.setCreateName(from.getCreateName());
        }
        if (from.getTbl() != null) {
            this.setTbl(from.getTbl());
        }
    }
}