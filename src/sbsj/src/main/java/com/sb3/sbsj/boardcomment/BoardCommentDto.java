package com.sb3.sbsj.boardcomment;

import com.sb3.sbsj.boardcommon.BoardBaseDto;
import jakarta.validation.constraints.Size;
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
public class BoardCommentDto extends BoardBaseDto implements IBoardComment {
    private Long id;
    @Size(min = 1, max = 1000, message = "댓글은 1~1000자 입니다.")
    private String comment;
    private Integer likeQty;
    private Long boardId;
    private Long commentId;
}