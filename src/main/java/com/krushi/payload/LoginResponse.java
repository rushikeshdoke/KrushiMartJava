package com.krushi.payload;

import com.krushi.model.Role;

public class LoginResponse {
    private String token;
    private String message;
    private Role role;
    private String redirectUrl;

    public LoginResponse(String token, String message, Role role, String redirectUrl) {
        this.token = token;
        this.message = message;
        this.role = role;
        this.redirectUrl = redirectUrl;
    }

    public String getToken() {
        return token;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
    
}