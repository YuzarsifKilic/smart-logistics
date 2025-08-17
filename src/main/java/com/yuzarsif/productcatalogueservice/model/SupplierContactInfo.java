package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierContactInfo {

    private String id;
    private String primaryContactName;
    private String primaryContactTitle;
    private String primaryEmail;
    private String primaryPhone;
    private String secondaryContactName;
    private String secondaryEmail;
    private String secondaryPhone;
    private String website;
    private String fax;
}