package com.sb3.sbsj.boardfile;

public interface IBoardFile {
    Long getId();
    void setId(Long id);

    String getTbl();
    void setTbl(String tbl);

    Long getBoardId();
    void setBoardId(Long boardId);

    String getName();
    void setName(String name);

    Integer getOrd();
    void setOrd(Integer ord);

    String getFileType();
    void setFileType(String fileType);

    String getUniqName();
    void setUniqName(String uniqName);

    Long getLength();
    void setLength(Long length);

    Boolean getDeleteYn();
    void setDeleteYn(Boolean deleteFlag);

    default void copyFields(IBoardFile from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            this.setName(from.getName());
        }
        if (from.getOrd() != null) {
            this.setOrd(from.getOrd());
        }
        if (from.getFileType() != null && !from.getFileType().isEmpty()) {
            this.setFileType(from.getFileType());
        }
        if (from.getUniqName() != null && !from.getUniqName().isEmpty()) {
            this.setUniqName(from.getUniqName());
        }
        if (from.getLength() != null) {
            this.setLength(from.getLength());
        }
        if (from.getTbl() != null && !from.getTbl().isEmpty()) {
            this.setTbl(from.getTbl());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
        if (from.getDeleteYn() != null) {
            this.setDeleteYn(from.getDeleteYn());
        }
    }
}
