package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryAttribute {

    private Long id;
    private String name;
    private String displayName;
    private AttributeType type;
    private Boolean isRequired;
    private Boolean isFilterable;
    private Boolean isSearchable;
    private List<String> options;
    private String unit;
    private Integer sortOrder;
}
