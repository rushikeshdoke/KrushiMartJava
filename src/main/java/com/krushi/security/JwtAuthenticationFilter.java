package com.krushi.security;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krushi.service.KrushiUserService;
import com.krushi.service.TokenService;

import org.slf4j.Logger;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private KrushiUserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        logger.info("Header : {}", requestHeader);

        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.warn("Illegal Argument while fetching the username!!");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT token is expired!!");
            } catch (MalformedJwtException e) {
                logger.warn("Invalid JWT Token!!");
            } catch (Exception e) {
                logger.warn("Something went wrong while parsing the token!!", e);
            }
        } else {
            logger.info("Invalid Header Value!!");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = ((UserDetailsService) this.userService).loadUserByUsername(username);

            if (jwtHelper.validateToken(token, userDetails)) {

                // 🚫 Check if token is blacklisted
                if (tokenService.isTokenBlacklisted(token)) {
                    logger.warn("Blacklisted token attempted: {}", token);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token is blacklisted. Please log in again.");
                    return;
                }

                // ✅ Set authentication if token is valid and not blacklisted
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("JWT token validation failed!!");
            }
        }

        filterChain.doFilter(request, response);
    }
}
