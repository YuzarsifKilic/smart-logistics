package com.yuzarsif.productcatalogueservice.repository;

import com.yuzarsif.productcatalogueservice.model.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface BrandMapper {

    void insertBrand(Brand brand);

    void updateBrand(Brand brand);

    void deleteBrandById(UUID id);

    Optional<Brand> findBrandById(UUID id);

    List<Brand> findAllBrands();

    Optional<Brand> findBrandBySlug(String slug);

    List<Brand> findBrandsByStatus(String status);

    Optional<Brand> findBrandByName(String name);
}
