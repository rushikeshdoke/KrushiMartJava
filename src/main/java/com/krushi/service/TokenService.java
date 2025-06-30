package com.krushi.service;

public interface TokenService {

	void blacklistToken(String token);

	boolean isTokenBlacklisted(String token);

}
