package com.krushi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class KrushiCropCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cat_id;

    private String category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KrushiCropProduct> krushiCropProducts;

   
    public Long getCat_id() {
        return cat_id;
    }

    public void setCat_id(Long cat_id) {
        this.cat_id = cat_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<KrushiCropProduct> getKrushiCropProducts() {
        return krushiCropProducts;
    }

    public void setKrushiCropProducts(List<KrushiCropProduct> krushiCropProducts) {
        this.krushiCropProducts = krushiCropProducts;
    }
}

