package com.yuzarsif.productcatalogueservice.model;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String id;
    private String sku;
    private String name;
    private String description;
    private String shortDescription;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String brandID;
    private String categoryID;
    private String supplierID;
    private BigDecimal basePrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private Long dimensionID;
    private Map<String, Object> attributes;
    private List<Long> imageIDs;
    private ProductStatus status = ProductStatus.DRAFT;
    private Boolean isFeatured;
    private Boolean isDigital;
    private Boolean requiresShipping;
    private BigDecimal taxRate;
    private Integer minOrderQuantity = 1;
    private Integer maxOrderQuantity;
    private String metaTitle; // SEO
    private String metaDescription; // SEO
    private String metaKeywords; // SEO
    private String barcode; // EAN, UPC, etc.
    private String manufacturerPartNumber;
    private Integer warrantyPeriodMonths;
    private LocalDate launchDate;
    private LocalDate discontinueDate;

    public void updateBasicInfo(String name, String description, String shortDescription) {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.slug = generateSlug(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePricing(BigDecimal basePrice, BigDecimal costPrice, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.costPrice = costPrice;
        this.taxRate = taxRate;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.status = ProductStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = ProductStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void discontinue() {
        this.status = ProductStatus.DISCONTINUED;
        this.discontinueDate = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal calculateSellingPrice() {
        return basePrice.add(basePrice.multiply(taxRate.divide(BigDecimal.valueOf(100))));
    }

    public boolean isAvailableForSale() {
        return status == ProductStatus.ACTIVE &&
                (discontinueDate == null || discontinueDate.isAfter(LocalDate.now()));
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
