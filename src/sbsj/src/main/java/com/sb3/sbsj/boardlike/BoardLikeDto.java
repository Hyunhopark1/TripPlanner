package com.sb3.sbsj.boardlike;

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
public class BoardLikeDto implements IBoardLike {
    private Long id;
    private String tbl;
    private Long createId;
    private Long boardId;
}
