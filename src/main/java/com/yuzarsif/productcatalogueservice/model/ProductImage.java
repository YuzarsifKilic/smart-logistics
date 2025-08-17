package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    private Long id;
    private String url;
    private String altText;
    private String caption;
    private Integer sortOrder;
    private Boolean isPrimary;
    private String size;
    private String mimeType;
    private Long fileSize;
}
