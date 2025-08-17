package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankInfo {

    private String id;
    private String bankName;
    private String branchName;
    private String branchCode;
    private String accountNumber;
    private String iban;
    private String swiftCode;
    private String accountHolderName;
    private String accountType;
    private Currency currency;
    private Boolean isActive;
}

