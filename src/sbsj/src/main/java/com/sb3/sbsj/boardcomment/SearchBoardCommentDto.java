package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardfree.SearchBoardDto;
import com.sb3.sbsj.commons.dto.SearchAjaxDto;
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
public class SearchBoardCommentDto extends SearchBoardDto {
    private String boardId;
    private Long createId;
    private String tbl;
    private Long commentId;
}
