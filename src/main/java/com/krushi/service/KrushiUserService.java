package com.krushi.service;



import org.springframework.security.core.userdetails.UserDetailsService;


import com.krushi.model.KrushiUser;


public interface KrushiUserService extends UserDetailsService{
	
	public KrushiUser registerUser(KrushiUser krushiUser);
	KrushiUser getUserByEmail(String email);

}
