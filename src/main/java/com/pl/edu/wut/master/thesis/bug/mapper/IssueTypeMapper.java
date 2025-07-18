package com.pl.edu.wut.master.thesis.bug.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IssueTypeMapper {
    IssueTypeMapper INSTANCE = Mappers.getMapper(IssueTypeMapper.class);

}
