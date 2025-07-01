package com.krushi.serviceimpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krushi.model.KrushiUser;
import com.krushi.repository.KrushiUserRepository;
import com.krushi.service.KrushiUserService;
import org.springframework.security.core.GrantedAuthority;



@Service("userServiceImpl")
public class KrushiUserServiceImpl implements KrushiUserService {

    @Autowired
    KrushiUserRepository krushiUserRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public KrushiUser registerUser(KrushiUser krushiUser) {
        krushiUser.setPassword(passwordEncoder.encode(krushiUser.getPassword()));
        return krushiUserRepository.save(krushiUser);
    }

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
