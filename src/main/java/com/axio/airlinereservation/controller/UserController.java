package com.axio.airlinereservation.controller;

import com.axio.airlinereservation.config.PasswordEncoder;
import com.axio.airlinereservation.config.SecretKeyProvider;
import com.axio.airlinereservation.request.user.UserSingUpRequest;
import com.axio.airlinereservation.response.CustomMessage;
import com.axio.airlinereservation.service.UserService;
import com.axio.airlinereservation.request.user.UserLogInRequest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@RestController
@RequestMapping("/api/user")
class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretKeyProvider secretKeyProvider;

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<CustomMessage> signup(@Valid @RequestBody UserSingUpRequest user)  {
        String message=userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(message,"200"));
    }


    @PostMapping("/login")
    public ResponseEntity<CustomMessage> login(@Valid @RequestBody UserLogInRequest user, HttpServletResponse response) {
        String message= userService.logInUser(user);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(secretKeyProvider.getSecretKey())
                .compact();

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);   /** 1 day expiry **/
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(message,"200"));
    }


    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@CookieValue(name = "JWT_TOKEN", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found in cookies");
        }
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKeyProvider.getSecretKey()).build().parseClaimsJws(token).getBody();
            return ResponseEntity.ok("Token valid for user: " + claims.getSubject() );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }


}