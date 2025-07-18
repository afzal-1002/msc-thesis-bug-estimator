package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.model.common.TimeTracking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeTrackingMapper {
    TimeTrackingMapper INSTANCE = Mappers.getMapper(TimeTrackingMapper.class);

    /** straight pass-through (MapStruct will copy each field) */
    TimeTracking toEntity(TimeTracking source);

}
