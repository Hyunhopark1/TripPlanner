package com.sb3.sbsj.boardfree;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SearchBoardDto {
   private String orderByWord;  //정렬할 컬럼
   private String searchName;   //검색어
   private String searchType;   //검색분류(제목,작성자)
   private String sortColumn;
   private String sortAscDesc;  //오름차순 OR 내림차순
   private Integer rowsOnePage; //1페이지에 들어가는 행 개수
   private Integer firstIndex;  //어디서부터 가져올지
   private Integer areaCode;    //지역코드

    public Integer getFirstIndex() {
        return (this.page - 1) * this.rowsOnePage;
    }
   private Integer page;
   private Integer total;
   private List<?> dataList;

    public void settingValues() {
        this.setOrderByWord( (this.getSortColumn() != null ? this.getSortColumn() : "id")
                + " " + (this.getSortAscDesc() != null ? this.getSortAscDesc() : "DESC") );
        // SQL select 문장의 ORDER BY 구문을 만들어 주는 역할
        if ( this.getSearchType() == null ) {
            this.setSearchType("title");
        }
        if ( this.getRowsOnePage() == null ) {
            // 한 페이지당 보여주는 행의 갯수
            this.setRowsOnePage(10);
        }
        if ( this.getPage() == null || this.getPage() <= 0 ) {
            this.setPage(1);
        }
    }
    public void settingCommentValues() {
        this.setOrderByWord((this.getSortColumn() != null ? this.getSortColumn() : "id")
                + " " + (this.getSortAscDesc() != null ? this.getSortAscDesc() : "DESC"));
        // SQL select 문장의 ORDER BY 구문을 만들어 주는 역할
        if (this.getSearchType() == null) {
            this.setSearchType("title");
        }
        if (this.getRowsOnePage() == null) {
            // 한 페이지당 보여주는 행의 갯수
            this.setRowsOnePage(10000);
        }
        if (this.getPage() == null || this.getPage() <= 0) {
            this.setPage(1);
        }
    }
}
