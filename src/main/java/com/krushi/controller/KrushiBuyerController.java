package com.krushi.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushi.model.KrushiCropCategory;
import com.krushi.service.KrushiBuyerService;

@RestController
@RequestMapping
public class KrushiBuyerController {
	
	@Autowired
	KrushiBuyerService krushiBuyerService;
	
	@GetMapping("/getCategoryList")
	public List<KrushiCropCategory> getCategoryList() {
		return krushiBuyerService.getCategoryList();
	}
	
	@GetMapping("/getProductList/{id}")
	public Optional<KrushiCropCategory> getProductList(@PathVariable("id")Long cat_id) {
		return krushiBuyerService.getProductList(cat_id);
	}
	
	
}
