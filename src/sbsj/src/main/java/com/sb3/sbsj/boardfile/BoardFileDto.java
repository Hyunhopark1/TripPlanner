package com.sb3.sbsj.boardfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileDto implements IBoardFile {
    private Long id;
    private String name;
    private Integer ord;
    private String fileType;
    private String uniqName;
    private Long length;
    private String tbl;
    private Long boardId;
    private Boolean deleteYn;
}
