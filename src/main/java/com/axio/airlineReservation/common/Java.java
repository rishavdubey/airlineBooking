//package com.rapifuzz.IncidentManagement.common;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.crypto.SecretKey;
//
//@Component
//class JwtInterceptor implements HandlerInterceptor {
//    private final SecretKey secretKey;
//
//    public JwtInterceptor(SecretKey secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("JWT_TOKEN".equals(cookie.getName())) {
//                    try {
//                        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(cookie.getValue()).getBody();
//                        request.setAttribute("user", claims.getSubject());
//                        return true;
//                    } catch (Exception e) {
//                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                        response.getWriter().write("Invalid or expired token");
//                        return false;
//                    }
//                }
//            }
//        }
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().write("No token found");
//        return false;
//    }