package com.sb3.sbsj.boardreview;


import com.sb3.sbsj.boardcommon.BoardBaseDto;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardReviewDto extends BoardBaseDto implements IBoardReview {

   private Long id;
   @Size(min = 2, max = 100, message = "제목은 2자~100자 사이로 입력해 주세요.")
   private String title;
   @Size(min = 2, max = 1000, message = "본문은 2자~1000자 사이로 입력해 주세요.")
   private String content;
   private Integer viewQty;
   private Integer likeQty;
   private Long areaCode;
   private String countComment;


   public String getTbl() {
      return "review";
   }

}
