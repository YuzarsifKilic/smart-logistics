package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    private String id;
    private String code; // SUP001, SUP002, etc.
    private String companyName;
    private String tradeName;
    private String taxNumber;
    private String taxOffice;
    private SupplierType supplierType; // MANUFACTURER, DISTRIBUTOR, WHOLESALER, etc.
    private SupplierContactInfo contactInfo;
    private String addressID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> bankInfoIDs;
    private SupplierStatus status = SupplierStatus.PENDING;
    private BigDecimal creditLimit = BigDecimal.ZERO;
    private Integer paymentTermsDays;
    private PaymentMethod preferredPaymentMethod;
    private String currency = "TRY";
    private Integer leadTimeDays;
    private BigDecimal minimumOrderAmount;
    private BigDecimal discountRate = BigDecimal.ZERO;
    private BigDecimal rating; // 0.00 - 5.00
    private Integer totalOrders = 0;
    private BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
    private LocalDateTime lastOrderDate;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private List<String> certifications; // ISO, CE, FDA, etc.
    private List<String> categoryIDs;
    private String notes;
    private List<Product> products = new ArrayList<>();

    public void updateBasicInfo(String companyName, String tradeName, SupplierType supplierType) {
        this.companyName = companyName;
        this.tradeName = tradeName;
        this.supplierType = supplierType;
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        this.status = SupplierStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void suspend() {
        this.status = SupplierStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void terminate() {
        this.status = SupplierStatus.TERMINATED;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateRating(BigDecimal newRating) {
        this.rating = newRating;
        this.updatedAt = LocalDateTime.now();
    }

    public void recordPurchase(BigDecimal amount) {
        this.totalOrders++;
        this.totalPurchaseAmount = this.totalPurchaseAmount.add(amount);
        this.lastOrderDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == SupplierStatus.ACTIVE;
    }

    public boolean isContractValid() {
        if (contractStartDate == null || contractEndDate == null) {
            return true; // No contract dates means always valid
        }
        LocalDate now = LocalDate.now();
        return !now.isBefore(contractStartDate) && !now.isAfter(contractEndDate);
    }

    public BigDecimal getAverageOrderAmount() {
        if (totalOrders == 0) {
            return BigDecimal.ZERO;
        }
        return totalPurchaseAmount.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP);
    }
}
