package com.krushi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krushi.model.KrushiUser;



@Repository
public interface KrushiUserRepository extends JpaRepository<KrushiUser, Long> {
	KrushiUser findByEmail(String email);
}
