package com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.app.ws.UsersMicroservice.mobileappwsUsersMicroservice.UI.RequestModel.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginRequestModel.getEmail(),
                      loginRequestModel.getPassword(),
                      new ArrayList<>()
              )

            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String userName = ((User)authResult.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(String.valueOf(864000000))))
                .signWith(SignatureAlgorithm.HS512, "JWT Token Secret")
                .compact();
        response.addHeader("token", token);
        response.addHeader("userName", userName);
    }
}
