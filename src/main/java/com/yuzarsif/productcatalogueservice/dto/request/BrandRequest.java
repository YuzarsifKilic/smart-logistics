package com.yuzarsif.productcatalogueservice.dto.request;

import com.yuzarsif.productcatalogueservice.model.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class BrandRequest {

    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String bannerUrl;
    private String websiteUrl;
    private String originCountry;
    private Integer establishedYear;
    private Map<String, String> socialMedia; // Instagram, Facebook, Twitter, etc.
    private String metaTitle;
    private String metaKeywords;
}
