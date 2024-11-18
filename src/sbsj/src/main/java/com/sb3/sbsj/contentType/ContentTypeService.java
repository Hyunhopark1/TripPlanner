package com.sb3.sbsj.contentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTypeService implements IContentTypeService {

    @Autowired
    private IContentTypeMybatisMapper contentTypeMybatisMapper;

    @Override
    public List<ContentTypeDto> getAllContentType() {
        return contentTypeMybatisMapper.searchAll();

    }
}

