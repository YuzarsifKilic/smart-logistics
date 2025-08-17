package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDimension {

    private Long id;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal volume;

    public BigDecimal calculateVolume() {
        if (length != null && width != null && height != null) {
            return length.multiply(width).multiply(height);
        }
        return BigDecimal.ZERO;
    }

    public String getDimensionsString() {
        if (length != null && width != null && height != null) {
            return String.format("%.1f x %.1f x %.1f cm", length, width, height);
        }
        return "";
    }
}
