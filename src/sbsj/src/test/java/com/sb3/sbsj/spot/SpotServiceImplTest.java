//package com.sb3.sbsj.spot;
//
//import com.sb3.sbsj.boardfree.IBoardFree;
//import com.sb3.sbsj.commons.dto.CUDInfoDto;
//import com.sb3.sbsj.user.UserDto;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@Slf4j
//@SpringBootTest
//public class SpotServiceImplTest {
//
//    @Autowired
//    private SpotServiceImpl spotService;
//
//    private CUDInfoDto cudInfoDto;
//    private SpotDto spotDto;
//    private SpotSearchDto spotSearchDto;
//
//
//    @BeforeEach
//    public void setDto(){this.setDto("강남","010","abc","1212a","123","123","att",6L,39L);}
//
//    private void setDto(String title,String tel, String addr1, String zipcode,String mapX, String mapY, String firstimage, Long areaCodeId, Long contentTypeId){
//        this.cudInfoDto = new CUDInfoDto(UserDto.builder().build());
//        this.spotSearchDto = SpotSearchDto.builder()
//                .areaCodeId(areaCodeId)
//                .contentTypeId(contentTypeId)
//                .title(title)
//                .build();
//
//        this.spotDto = SpotDto.builder()
//                .title(title)
//                .tel(tel)
//                .addr1(addr1)
//                .zipcode(zipcode)
//                .mapX(mapX)
//                .mapY(mapY)
//                .firstimage(firstimage)
//                .areaCodeId(areaCodeId)
//                .contentTypeId(contentTypeId)
//                .build();
//    }
//    @Test
//    @Rollback(false) // Role 처리 안됨
//    @Transactional
//    public void insertTest() throws IOException {
//        ISpot result = this.insertSample("서초","011","aabc","21212a","1253","1263","batt",6L,39L);
//        this.assertInsertCheck(cudInfoDto,result,spotDto);
//    }
//
//    private ISpot insertSample(String title,String tel, String addr1, String zipcode,String mapX, String mapY, String firstimage, Long areaCodeId, Long contentTypeId) throws IOException{
//        // given
//        this.setDto(title, tel, addr1, zipcode, mapX, mapY, firstimage, areaCodeId, contentTypeId);
//
//        // when
//        ISpot result = spotService.insert(this.cudInfoDto,this.spotDto);
//        assertThat(result).isNotNull();
//        return this.findById(result.getId());
//    }
//
//    private ISpot findById(Long id){ return this.spotService.findById(id);}
//
//    private void assertInsertCheck(CUDInfoDto cudInfoDto, ISpot actual, ISpot result){
//        //then
//        assertThat(actual).isNotNull();
//        assertThat(actual.getId()).isGreaterThan(0L);
//        assertThat(actual.getTitle()).isEqualTo(result.getTitle());
//        assertThat(actual.getTel()).isEqualTo(result.getTel());
//        assertThat(actual.getAddr1()).isEqualTo(result.getAddr1());
//        assertThat(actual.getMapX()).isEqualTo(result.getMapX());
//        assertThat(actual.getMapY()).isEqualTo(result.getMapY());
//        assertThat(actual.getFirstimage()).isEqualTo(result.getFirstimage());
//        assertThat(actual.getAreaCodeId()).isEqualTo(result.getAreaCodeId());
//        assertThat(actual.getContentTypeId()).isEqualTo(result.getContentTypeId());
//
//    }
//
//    @Test
//    @Rollback(false) // Role 처리 안됨
//    @Transactional
//    public void findByIdTest() throws IOException {
//        // given
//        ISpot insert = this.insertSample("오감자맛있다","010-1234-5678","강남구-논현동","32125","1","2","a",5L,39L);
//        assertThat(insert).isNotNull();
//
//        // when
//        ISpot find = this.findById(insert.getId());
//
//        // then
//        this.assertInsertCheck(cudInfoDto,find,insert);
//        assertThat(find.getId()).isEqualTo(insert.getId());
//        assertThat(find.getId()).isGreaterThan(0L);
//        assertThat(find.getTitle()).isEqualTo(insert.getTitle());
//        assertThat(find.getTel()).isEqualTo(insert.getTel());
//        assertThat(find.getAddr1()).isEqualTo(insert.getAddr1());
//        assertThat(find.getMapX()).isEqualTo(insert.getMapX());
//        assertThat(find.getMapY()).isEqualTo(insert.getMapY());
//        assertThat(find.getFirstimage()).isEqualTo(insert.getFirstimage());
//        assertThat(find.getAreaCodeId()).isEqualTo(insert.getAreaCodeId());
//        assertThat(find.getContentTypeId()).isEqualTo(insert.getContentTypeId());
//
//    }
//
//    @Test
//    @Rollback(false)
//    @Transactional
//    public void updateTest(){
//        // given
//        SpotDto result = spotService.insert(this.cudInfoDto,this.spotDto);
//        assertThat(result).isNotNull();
//        result.setTitle("update title");
//        result.setAddr1("update addr1");
//        result.setTel("update tel");
//        result.setAreaCodeId(5L);
//        result.setContentTypeId(39L);
//
//        // when
//        result = spotService.update(this.cudInfoDto,result);
//        ISpot find = this.findById(result.getId());
//
//        // then
//        this.assertUpdateCheck(find,result);
//    }
//
//    private void assertUpdateCheck(ISpot actual, ISpot result){
//        assertThat(actual).isNotNull();
//        assertThat(result).isNotNull();
//        assertThat(actual.getId()).isEqualTo(result.getId());
//        assertThat(actual.getTitle()).isEqualTo(result.getTitle());
//        assertThat(actual.getTel()).isEqualTo(result.getTel());
//        assertThat(actual.getZipcode()).isEqualTo(result.getZipcode());
//        assertThat(actual.getMapX()).isEqualTo(result.getMapX());
//        assertThat(actual.getMapY()).isEqualTo(result.getMapY());
//        assertThat(actual.getFirstimage()).isEqualTo(result.getFirstimage());
//        assertThat(actual.getAreaCodeId()).isEqualTo(result.getAreaCodeId());
//        assertThat(actual.getContentTypeId()).isEqualTo(result.getContentTypeId());
//    }
//
//    @Test
//    @Rollback(false)
//    @Transactional
//    public void findSpotByFiltersTest(){
//        // given
//        SpotDto result = spotService.insert(this.cudInfoDto,this.spotDto);
//        assertThat(result).isNotNull();
//
//        List<SpotDto> results = spotService.findSpotByFilters(spotSearchDto);
//        assertFindSpotByFiltersCheck(results);
//
//    }
//    private void assertFindSpotByFiltersCheck(List<SpotDto> results){
//        // 테스트한 필터 조건에 따라 적절한 결과가 있는지 확인
//        assertThat(results).isNotNull();   // 결과가 null이 아닌지 확인
//        assertThat(results.size()).isGreaterThan(0);   // 최소한 하나 이상의 결과가 있어야 함
//
//        // 첫 번째 결과를 기반으로 검증 (여기서는 첫 번째 결과만 검증)
//        SpotDto firstResult = results.get(0);
//        assertThat(firstResult.getTitle()).contains(spotSearchDto.getTitle()); // 타이틀이 포함되어야 함
//        assertThat(firstResult.getAreaCodeId()).isEqualTo(spotSearchDto.getAreaCodeId()); // AreaCodeId가 일치해야 함
//        assertThat(firstResult.getContentTypeId()).isEqualTo(spotSearchDto.getContentTypeId()); // ContentTypeId가 일치해야 함
//    }
//
//    @Test
//    @Rollback(false)
//    @Transactional
//    public void countSpotByFiltersTest() throws IOException {
//        // given
//        // 테스트용 데이터 삽입 (필요 시 더 많은 데이터를 삽입해도 됨)
//        this.insertSample("서울", "010-1234-5678", "강남구", "12345", "37.1234", "127.1234", "image1.jpg", 5L, 39L);
//        this.insertSample("부산", "010-9876-5432", "해운대구", "54321", "35.1234", "129.1234", "image2.jpg", 6L, 39L);
//        this.insertSample("서울", "010-1234-5678", "서초구", "145", "37", "12", "image1.jpg", 5L, 39L);
//        this.insertSample("서울", "010-1234-5678", "은평구", "125", "35", "13", "image2.jpg", 5L, 39L);
//        this.insertSample("서울", "010-1234-5678", "중랑구", "135", "36", "14", "image3.jpg", 5L, 39L);
//        this.insertSample("부산", "010-9423-5432", "동구", "532414321", "35.143234", "1143229.1234", "image5.jpg", 6L, 39L);
//
//        // 필터 조건을 설정
//        SpotSearchDto searchDto = SpotSearchDto.builder()
//                .title("서울")  // 제목에 '서울'이 포함된 데이터 필터링
//                .areaCodeId(5L)  // 지역 코드가 5인 데이터 필터링
//                .contentTypeId(39L)  // 콘텐츠 타입이 39인 데이터 필터링
//                .build();
//
//        // when
//        int count = spotService.countAllByFilters(searchDto);
//
//        // then
//        // 필터링된 결과가 1개임을 확인 (삽입된 데이터에 따라 다를 수 있음)
//        assertThat(count).isEqualTo(4);
//
//    }
//}
