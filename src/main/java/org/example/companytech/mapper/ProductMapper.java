package org.example.companytech.mapper;

import org.example.companytech.domain.Product;
import org.example.companytech.dto.req.product.ProductAddingReqDto;
import org.example.companytech.dto.req.product.ProductUpdatingReqDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product toEntity(ProductAddingReqDto productAddingReqDto);

    Product toEntity(ProductUpdatingReqDto productUpdatingReqDto);

}