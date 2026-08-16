//package lk.ijse.eca.userservice.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lk.ijse.eca.userservice.dto.UserDTO;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil implements Serializable {
//    private String SECRET = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566DF423F4428472B4B6250655368566D";
//
//    public String generateToken(UserDTO userDTO) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", userDTO.getRole().name());
//        claims.put("id", userDTO.getId());
//        return doGenerateToken(claims, userDTO.getEmail(), 1000 * 60 * 10);
//
//    }
//    public  String generateRefreshToken(UserDTO userDTO){
//        return doGenerateToken(new HashMap<>(), userDTO.getEmail(), 1000 * 60 * 60 * 24 * 7);
////    }
////    public String doGenerateToken(Map<String, Object> claims, String subject, int expiration) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
//                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
//    }
//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
//    }
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
//            return !isTokenExpired(token);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//    private boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//    private Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//
//    }
//    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimFromToken(token);
//         return claimsResolver.apply(claims);
//    }
//    private Claims getAllClaimFromToken(String token) {
//        return  Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
//    }
//}
