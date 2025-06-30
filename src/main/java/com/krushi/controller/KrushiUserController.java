package com.krushi.controller;

import static com.krushi.constant.UrlConstant.REGISTER;
import static com.krushi.constant.UrlConstant.LOGIN;
import static com.krushi.constant.UrlConstant.LOGOUT;
import static com.krushi.constant.UrlConstant.DASHBOARD;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushi.model.KrushiUser;
import com.krushi.model.Role;
import com.krushi.payload.LoginRequest;
import com.krushi.payload.LoginResponse;
import com.krushi.security.JwtHelper;
import com.krushi.service.KrushiUserService;
import com.krushi.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/krushi")
public class KrushiUserController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	KrushiUserService krushiUserService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private TokenService tokenService;

	
	@PostMapping(REGISTER)
	public String registerUser(@RequestBody KrushiUser krushiUser) {
		krushiUserService.registerUser(krushiUser);
		return "Registration SuccsessFull...!";
	}
	
	
	@PostMapping(LOGIN)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request) {
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	        );
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }

	    final UserDetails userDetails = krushiUserService.loadUserByUsername(request.getEmail());
	    final String jwt = jwtHelper.generateToken(userDetails);

	    KrushiUser user = krushiUserService.getUserByEmail(request.getEmail());
	    Role role = user.getRole(); 

	    // Build redirect URL based on role
	    String redirectUrl = switch (role) {
	        case ADMIN -> "/krushi/dashboard";
	        case SELLER -> "/krushi/dashboard";
	        case BUYER -> "/krushi/dashboard";
	        case TRANSPORT -> "/krushi/dashboard";
	        default -> "/home";
	    };

	    return ResponseEntity.ok(new LoginResponse(
	        jwt,
	        "Login Successful",
	        role,
	        redirectUrl
	    ));
	}
	
	@GetMapping(DASHBOARD)
	public String AuthSuccess() {
		return "Welcome to dashboard";
	}
	
	
	@PostMapping(LOGOUT)
	public ResponseEntity<?> logout(HttpServletRequest request) {
	    final String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        tokenService.blacklistToken(token);
	        return ResponseEntity.ok("Logout successful");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token not provided");
	}
	
	

}

