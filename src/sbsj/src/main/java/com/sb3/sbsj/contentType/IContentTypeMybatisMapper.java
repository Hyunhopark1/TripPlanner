package com.sb3.sbsj.contentType;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IContentTypeMybatisMapper {
    List<ContentTypeDto> searchAll();}
