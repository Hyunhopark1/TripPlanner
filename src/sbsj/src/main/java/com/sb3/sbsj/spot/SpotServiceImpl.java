    package com.sb3.sbsj.spot;

    import com.sb3.sbsj.boardfree.BoardFreeDto;
    import com.sb3.sbsj.commons.dto.CUDInfoDto;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class SpotServiceImpl implements ISpotService {
        @Autowired
        ISpotMybatisMapper spotMybatisMapper;


        @Override
        public List<SpotDto> findSpotByFilters(SpotSearchDto spotSearchDto) {
            if ( spotSearchDto == null ) {
                return null;
            }
            // 필터를 매퍼로 전달하여 필터링된 결과를 가져옴
            spotSearchDto.settingValues();
            return spotMybatisMapper.findSpotByFilters(spotSearchDto);
        }

        @Override
        public Integer countAllByFilters(SpotSearchDto spotSearchDto) {
            return spotMybatisMapper.countAllByFilters(spotSearchDto);
        }

        @Override
        public SpotDto findById(Long id) {
            if ( id == null || id <= 0 ) {
                return null;
            }
            SpotDto find = this.spotMybatisMapper.findById(id);
            return find;
        }

        @Override
        public SpotDto insert(CUDInfoDto cudInfoDto, SpotDto dto) {
            if (dto == null) {
                return null;
            }
            SpotDto insert = SpotDto.builder().build();
            insert.copyFields(dto);
            this.spotMybatisMapper.insert(insert);
            return insert;
        }

        @Override
        public SpotDto update(CUDInfoDto cudInfoDto, SpotDto dto) {
            if (dto == null || dto.getId() == null || dto.getId() <= 0)  {
                return null;
            }
            SpotDto update = SpotDto.builder().build();
            update.copyFields(dto);
            this.spotMybatisMapper.update(update);
            return update;
        }

        @Override
        public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, SpotDto dto) {
            return null;
        }

        @Override
        public Boolean deleteById(Long id) {
            if (id == null || id < 0) {
                return false;
            }
            this.spotMybatisMapper.deleteById(id);
            return true;
        }
    }
