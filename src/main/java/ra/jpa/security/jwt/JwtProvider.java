package ra.jpa.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j // cơ chế ghi log
public class JwtProvider {

    // nhà cung cấp chuỗi JWT
    // chức năng tạo token dựa trên username
    // chức nawng : validate token => boolean
    // chức năng giải mã token để laays ra thông tin username
    @Value("${jwt.secret-key}") // lấy message trong file application.properties
    private String SECRET_KEY;
    @Value("${jwt.expired}")
    private long EXPIRED; // thơ gian sống

    // tạo access token : dùng để truy cập
    public String generateToken(UserDetails userDetails) {
        Date today = new Date(); // thời gian hiện tại
        return Jwts.builder().setSubject(userDetails.getUsername()) // mã hóa username trong tiêu để
                .setIssuedAt(today) // thời gian bắt đầu sống
                .setExpiration(new Date(today.getTime() + EXPIRED)) // thời gian hết hạn 1 ngày
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact(); // trả về chuỗi JWT
    }
    // refresh token : dùng để xin câ lại access token nếu hết hạn

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); // giải mã
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT: message error expired:", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT: message error unsupported:", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT: message error not formated:", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT: message error signature not math:", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT: message claims empty or argument invalid: ", e.getMessage());
        }
        return false;
    }

    // giải mã lây ra username
    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject(); // lấy username
    }
}
