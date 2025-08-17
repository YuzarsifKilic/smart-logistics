package com.yuzarsif.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private String id;
    private String name;
    private String slug;
    private String description;
    private String iconUrl;
    private String imageUrl;
    private String bannerUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category parent;
    private List<Category> children = new ArrayList<>();
    private Integer level = 0; // 0: Root, 1: Main Category, 2: Sub Category, etc.
    private Integer sortOrder = 0;
    private Boolean isActive = true;
    private Boolean isFeatured = false;
    private Boolean showInMenu = true;
    private BigDecimal commissionRate;
    private List<Long> attributeIDs;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;

    public void updateBasicInfo(String name, String description) {
        this.name = name;
        this.description = description;
        this.slug = generateSlug(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void addChild(Category child) {
        child.setParent(this);
        child.setLevel(this.level + 1);
        this.children.add(child);
    }

    public void removeChild(Category child) {
        child.setParent(null);
        this.children.remove(child);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String getFullPath() {
        if (parent == null) {
            return name;
        }
        return parent.getFullPath() + " > " + name;
    }

    public List<Category> getAllAncestors() {
        List<Category> ancestors = new ArrayList<>();
        Category current = this.parent;
        while (current != null) {
            ancestors.add(0, current); // Add to beginning
            current = current.getParent();
        }
        return ancestors;
    }

    public List<Category> getAllDescendants() {
        List<Category> descendants = new ArrayList<>();
        for (Category child : children) {
            descendants.add(child);
            descendants.addAll(child.getAllDescendants());
        }
        return descendants;
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
