package com.sb3.sbsj.boardlike;

public interface IBoardLike {
    Long getId();
    void setId(Long id);

    String getTbl();
    void setTbl(String tbl);

    Long getCreateId();
    void setCreateId(Long createId);

    Long getBoardId();
    void setBoardId(Long boardId);

    default void copyFields(IBoardLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTbl() != null && !from.getTbl().isEmpty()) {
            this.setTbl(from.getTbl());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
    }
}
