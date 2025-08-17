package com.yuzarsif.productcatalogueservice.service;

import com.yuzarsif.productcatalogueservice.dto.request.BrandRequest;
import com.yuzarsif.productcatalogueservice.exception.EntityNotFoundException;
import com.yuzarsif.productcatalogueservice.mapper.BrandStructMapper;
import com.yuzarsif.productcatalogueservice.model.Brand;
import com.yuzarsif.productcatalogueservice.model.BrandStatus;
import com.yuzarsif.productcatalogueservice.repository.BrandMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandMapper brandMapper;
    private final BrandStructMapper brandStructMapper;

    public void createBrand(BrandRequest request) {
        Brand brand = brandStructMapper.toEntity(request);
        brand.setId(UUID.randomUUID());
        brand.setIsFeatured(false);
        brand.setSortOrder(0);
        brand.setStatus(BrandStatus.PENDING_APPROVAL);
        brandMapper.insertBrand(brand);
    }

    public void updateBrand(UUID id, BrandRequest request) {
        log.info("updateBrand id: {}", id);
        Brand brand = brandMapper.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found by id: " + id));
        brand.updateAllInfo(request);
        brandMapper.updateBrand(brand);
    }

    public void deleteBrandById(UUID id) {
        log.info("deleteBrandById: {}", id);
        Brand brand = brandMapper.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found by id: " + id));
        brandMapper.deleteBrandById(id);
    }

    public void activateBrand(UUID id) {
        log.info("activateBrand: {}", id);
        Brand brand = brandMapper.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found by id: " + id));
        brandMapper.updateBrand(brand);
    }

    public Brand findBrandById(UUID id) {
        log.info("findBrandById: {}", id);
        return brandMapper.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found by id: " + id));
    }

    public List<Brand> findAllBrands() {
        log.info("findAllBrands");
        List<Brand> allBrands = brandMapper.findAllBrands();
        return allBrands;
    }
}
