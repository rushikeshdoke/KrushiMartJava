package com.krushi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class KrushiCropProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String prodName;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    @JsonIgnore
    private KrushiCropCategory category;

    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public KrushiCropCategory getCategory() {
        return category;
    }

    public void setCategory(KrushiCropCategory category) {
        this.category = category;
    }
}
