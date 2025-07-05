package com.krushi.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.krushi.model.KrushiUser;
import com.krushi.repository.KrushiUserRepository;
import com.krushi.service.KrushiUserService;



@Service("userServiceImpl")
public class KrushiUserServiceImpl implements KrushiUserService {

    @Autowired
    KrushiUserRepository krushiUserRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

//    @Override
//    public KrushiUser registerUser(KrushiUser krushiUser) {
//        krushiUser.setPassword(passwordEncoder.encode(krushiUser.getPassword()));
//        return krushiUserRepository.save(krushiUser);
//    }
    
  //==================================================================================================
    @Override
    public KrushiUser registerUser(KrushiUser krushiUser, MultipartFile imageFile) {
        try {
            // Encrypt the password
            krushiUser.setPassword(passwordEncoder.encode(krushiUser.getPassword()));

            // Handle image upload if file is present
            if (imageFile != null && !imageFile.isEmpty()) {
                krushiUser.setImageName(imageFile.getOriginalFilename());
                krushiUser.setImageType(imageFile.getContentType());
                krushiUser.setImageData(imageFile.getBytes());
            }

            // Save to database
            return krushiUserRepository.save(krushiUser);

        } catch (Exception e) {
            throw new RuntimeException("User registration failed", e);
        }
    }

    
  //==================================================================================================

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    KrushiUser user = krushiUserRepository.findByEmail(email);
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found");
	    }
	    if (user == null) throw new UsernameNotFoundException("User not found");
	    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole()));
	    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
    
    @Override
    public KrushiUser getUserByEmail(String email) {
        return krushiUserRepository.findByEmail(email);
    }

}
