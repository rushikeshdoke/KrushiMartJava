package com.krushi.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krushi.model.KrushiCropCategory;
import com.krushi.repository.KrushiBuyerRepository;
import com.krushi.service.KrushiBuyerService;

@Service
public class KrushiBuyerServiceImpl implements KrushiBuyerService {

	@Autowired
	KrushiBuyerRepository krushiBuyerRepository;
	
	@Override
	public List<KrushiCropCategory> getCategoryList() {
		
		return krushiBuyerRepository.findAll();
	}

	@Override
	public Optional<KrushiCropCategory> getProductList(Long cat_id) {
		
		return krushiBuyerRepository.findById(cat_id) ;
	}

}
