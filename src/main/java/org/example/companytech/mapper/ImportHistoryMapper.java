package org.example.companytech.mapper;

import org.example.companytech.domain.ImportHistory;
import org.example.companytech.dto.req.importe.ImportHistoryReqDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImportHistoryMapper {

    @Mapping(target = "importDate", ignore = true)
    ImportHistory toEntity(ImportHistoryReqDto importHistoryReqDto);

}