package com.krushi.service;



import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.krushi.model.KrushiUser;


//public interface KrushiUserService extends UserDetailsService{
//	
//	public KrushiUser registerUser(KrushiUser krushiUser);
//	KrushiUser getUserByEmail(String email);
//
//}

//==================================================================================================

public interface KrushiUserService extends UserDetailsService{
	
//	public KrushiUser registerUser(KrushiUser krushiUser,MultipartFile imageFile) throws IOException;
	KrushiUser registerUser(KrushiUser krushiUser, MultipartFile imageFile);

	KrushiUser getUserByEmail(String email);

}



//==================================================================================================
