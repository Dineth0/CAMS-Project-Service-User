//package lk.ijse.eca.userservice.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//
//public class RoleHeaderFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String userRole = request.getHeader("X-User-Role");
//        String userEmail = request.getHeader("X-User-Email");
//        System.out.println("==== SECURITY FILTER INFO ====");
//        System.out.println("Email Header: " + userEmail);
//        System.out.println("Role Header : " + userRole);
//        System.out.println("==============================");
//
//        if (userRole != null && !userRole.isEmpty()) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
//
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userEmail, null, Collections.singletonList(authority)
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
