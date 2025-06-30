package com.krushi.serviceimpl;


import com.krushi.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenServiceImpl implements TokenService {

    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    @Override
    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

