package com.yuzarsif.productcatalogueservice.model;

import com.yuzarsif.productcatalogueservice.dto.request.BrandRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String bannerUrl;
    private String websiteUrl;
    private String originCountry;
    private Integer establishedYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> socialMedia; // Instagram, Facebook, Twitter, etc.
    private BrandStatus status;
    private Boolean isFeatured;
    private Integer sortOrder;
    private String metaTitle;
    private String metaKeywords;

    public void updateBasicInfo(String name, String description) {
        this.name = name;
        this.description = description;
        this.slug = generateSlug(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.status = BrandStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = BrandStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public void updateAllInfo(BrandRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.logoUrl = request.getLogoUrl();
        this.bannerUrl = request.getBannerUrl();
        this.websiteUrl = request.getWebsiteUrl();
        this.originCountry = request.getOriginCountry();
        this.establishedYear = request.getEstablishedYear();
        this.socialMedia = request.getSocialMedia();
        this.metaTitle = request.getMetaTitle();
        this.metaKeywords = request.getMetaKeywords();
        this.slug = generateSlug(name);
        this.updatedAt = LocalDateTime.now();
    }
}
