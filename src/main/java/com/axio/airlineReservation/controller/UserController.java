package com.axio.airlineReservation.controller;

import com.axio.airlineReservation.config.PasswordEncoder;
import com.axio.airlineReservation.config.SecretKeyProvider;
import com.axio.airlineReservation.request.user.UserSingUpRequest;
import com.axio.airlineReservation.response.CustomMessage;
import com.axio.airlineReservation.service.UserService;
import com.axio.airlineReservation.request.user.UserLogInRequest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<CustomMessage> signup(@RequestBody UserSingUpRequest user)  {
        String message=userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(message,"200"));
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserRequest user) {
//        String token = Jwts.builder()
//                .setSubject(user.getUsername())
//                .claim("roles", user.getRoles())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
//                .signWith(secretKey)
//                .compact();
//        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body("Login successful");
//    }
//


//    @GetMapping("/validate")
//    public ResponseEntity<String> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        try {
//            token = token.replace("Bearer ", "");
//            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//            return ResponseEntity.ok("Token valid for user: " + claims.getSubject() + " with roles: " + claims.get("roles"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
//        }
//    }


    @PostMapping("/login")
    public ResponseEntity<CustomMessage> login(@RequestBody UserLogInRequest user, HttpServletResponse response) {
        System.out.println("Inside LogIn");
        String message= userService.logInUser(user);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
//                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(secretKeyProvider.getSecretKey())
                .compact();

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 1 day expiry
        response.addCookie(cookie);
        System.out.println("ExitForm login");

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
        System.out.println(token);
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