package com.yuzarsif.productcatalogueservice.controller;

import com.yuzarsif.productcatalogueservice.dto.request.BrandRequest;
import com.yuzarsif.productcatalogueservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Object> createBrand(@RequestBody BrandRequest request) {
        brandService.createBrand(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findBrandById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(brandService.findBrandById(id));
    }

    @GetMapping
    public ResponseEntity<Object> findAllBrands() {
        return ResponseEntity.ok().body(brandService.findAllBrands());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBrand(@PathVariable UUID id, @RequestBody BrandRequest request) {
        brandService.updateBrand(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBrandById(@PathVariable UUID id) {
        brandService.deleteBrandById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Object> activateBrand(@PathVariable UUID id) {
        brandService.activateBrand(id);
        return ResponseEntity.ok().build();
    }
}
