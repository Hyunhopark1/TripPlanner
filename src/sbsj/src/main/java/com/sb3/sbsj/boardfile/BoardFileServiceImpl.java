package com.sb3.sbsj.boardfile;


import com.sb3.sbsj.boardfree.BoardFreeDto;
import com.sb3.sbsj.boardfree.IBoardFree;
import com.sb3.sbsj.boardreview.BoardReviewDto;
import com.sb3.sbsj.boardreview.IBoardReview;
import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.filecntl.FileCtrlService;
import com.sb3.sbsj.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class BoardFileServiceImpl implements IBoardFileService {
    @Autowired
    private IBoardFileMybatisMapper boardFileMybatisMapper;

    @Autowired
    private FileCtrlService fileCtrlService;

    @Override
    public IBoardFile insert(CUDInfoDto cudInfoDto, IBoardFile dto) {
        if (dto == null) {
            return null;
        }
        BoardFileDto insert = BoardFileDto.builder().build();
        insert.copyFields(dto);
        this.boardFileMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public IBoardFile update(CUDInfoDto cudInfoDto, IBoardFile dto) {
        return null;
    }

    @Override
    public Boolean delete(IBoardFile dto) {
        if (dto == null || dto.getId() == null || dto.getId() <= 0) {
            return false;
        }
        BoardFileDto delete = BoardFileDto.builder().build();
        delete.copyFields(dto);
        this.boardFileMybatisMapper.updateDeleteFlag(delete);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        this.boardFileMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public IBoardFile findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        BoardFileDto find = this.boardFileMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public List<IBoardFile> findAllByTblBoardId(IBoardFile search) {
        if (search == null) {
            return List.of();
        }
        BoardFileDto dto = BoardFileDto.builder().build();
        dto.copyFields(search);
        List<BoardFileDto> list = this.boardFileMybatisMapper.findAllByTblBoardId(dto);
        List<IBoardFile> result = this.getInterfaceList(list);
        return result;
    }

    private List<IBoardFile> getInterfaceList(List<BoardFileDto> list) {
        if (list == null) {
            return List.of();
        }
        List<IBoardFile> result = list.stream().map(x -> (IBoardFile) x).toList();
        return result;
    }

    @Transactional
    @Override
    public Boolean insertFreeFiles(IBoardFree boardDto, List<MultipartFile> files) {
        if ( boardDto == null || files == null ) {
            return false;
        }
        int ord = 0;
        try {
            for ( MultipartFile file : files ) {
                if ( file.getSize() > 0 ) {
                    BoardFileDto insert = BoardFileDto.builder()
                            .tbl(new BoardFreeDto().getTbl())
                            .name(file.getOriginalFilename())
                            .ord(ord++)
                            .fileType(this.getFileType(Objects.requireNonNull(file.getOriginalFilename())))
                            .uniqName(UUID.randomUUID().toString())
                            .length(file.getSize())
                            .boardId(boardDto.getId())
                            .build();
                    this.boardFileMybatisMapper.insert(insert);
                    this.fileCtrlService.saveFile(file, insert.getTbl(), insert.getUniqName() + insert.getFileType());
                }
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Transactional
    @Override
    public Boolean insertReviewFiles(IBoardReview boardDto, List<MultipartFile> files) {
        if ( boardDto == null || files == null ) {
            return false;
        }
        int ord = 0;
        try {
            for ( MultipartFile file : files ) {
                if (file.getSize() > 0) {
                    BoardFileDto insert = BoardFileDto.builder()
                            .tbl(new BoardReviewDto().getTbl())
                            .name(file.getOriginalFilename())
                            .ord(ord++)
                            .fileType(this.getFileType(Objects.requireNonNull(file.getOriginalFilename())))
                            .uniqName(UUID.randomUUID().toString())
                            .length(file.getSize())
                            .boardId(boardDto.getId())
                            .build();
                    this.boardFileMybatisMapper.insert(insert);
                    this.fileCtrlService.saveFile(file, insert.getTbl(), insert.getUniqName() + insert.getFileType());
                }
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Boolean updateFiles(BoardFileListDto boardFileListDto) {
        if ( boardFileListDto == null || boardFileListDto.getBoardfiles() == null || boardFileListDto.getBoardfiles().size() <= 0 ) {
            return false;
        }
        for ( BoardFileDto boardFileDto : boardFileListDto.getBoardfiles()) {
            if ( boardFileDto.getDeleteYn()) {
                this.boardFileMybatisMapper.delete(boardFileDto);
            }
        }
        return true;
    }

    @Override
    public byte[] getBytesFromFile(IBoardFile down) {
        if ( down == null ) {
            return new byte[0];
        }
        byte[] result = this.fileCtrlService.downloadFile(down.getTbl(), down.getUniqName(), down.getFileType());
        return result;
    }

    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
