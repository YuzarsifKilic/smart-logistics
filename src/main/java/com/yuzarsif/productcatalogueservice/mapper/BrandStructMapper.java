package com.yuzarsif.productcatalogueservice.mapper;

import com.yuzarsif.productcatalogueservice.dto.request.BrandRequest;
import com.yuzarsif.productcatalogueservice.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandStructMapper {

    Brand toEntity(BrandRequest request);
}
