package com.sb3.sbsj.boardcommon;


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
public class BoardBaseDto implements IBoardBase {

    //작성자 id
    Long createId;
    //작성자 이름 -> UserDto의 닉네임 받아옴
    String createName;
    //작성일
    String createDt;
    //수정일
    String updateDt;
    //삭제여부
    Boolean deleteYn;
    //테이블 분류
    String tbl;
}
