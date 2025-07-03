package com.krushi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krushi.model.KrushiCropCategory;

public interface KrushiBuyerService {

	public List<KrushiCropCategory> getCategoryList();

	public Optional<KrushiCropCategory> getProductList(Long cat_id);
}
