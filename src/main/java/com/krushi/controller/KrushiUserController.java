package com.krushi.controller;

import static com.krushi.constant.UrlConstant.REGISTER;
import static com.krushi.constant.UrlConstant.LOGIN;
import static com.krushi.constant.UrlConstant.LOGOUT;
import static com.krushi.constant.UrlConstant.DASHBOARD;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	
//	@PostMapping(REGISTER)
//	public String registerUser(@RequestBody KrushiUser krushiUser) {
//		krushiUserService.registerUser(krushiUser);
//		return "Registration SuccsessFull...!";
//	}
	
	//==================================================================================================
	 @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public String registerUser(
	        @RequestPart("user") KrushiUser krushiUser,
	        @RequestPart(value = "image", required = false) MultipartFile imageFile) {

	        krushiUserService.registerUser(krushiUser, imageFile);
	        return "Registration Successful...!";
	    }
	//==================================================================================================
	
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
	    String name=user.getUsername();

	    // Build redirect URL based on role
	    String redirectUrl = switch (role) {
	        case ADMIN -> "/krushi/dashboard-admin";
	        case SELLER -> "/krushi/dashboard-seller";
	        case BUYER -> "/krushi/dashboard-buyer";
	        case TRANSPORT -> "/krushi/dashboard-transport";
	        default -> "/home";
	    };

	    return ResponseEntity.ok(new LoginResponse(
	        jwt,
	        "Login Successful",
	        role,
	        redirectUrl,
	        name
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

