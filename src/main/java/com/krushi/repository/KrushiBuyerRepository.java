package com.krushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krushi.model.KrushiCropCategory;

@Repository
public interface KrushiBuyerRepository extends JpaRepository<KrushiCropCategory,Long>{
	
}
