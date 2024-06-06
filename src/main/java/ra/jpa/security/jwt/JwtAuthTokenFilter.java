package ra.jpa.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.jpa.security.principle.UserDetailsServiceCustom;

import java.io.IOException;


@Component
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    // bộ loc token jwt
    @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceCustom userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // lấy token ra từ request
        try{
            String token = getTokenFromRequest(request);
            if (token!=null && jwtProvider.validateToken(token)){ // có null v hợp lệ không
                // lây ra username
                String username = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // tạo usernamepasswordauthtoken
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth); // da xac thuc bang token , lưu vào contextholder
            }
        }catch (Exception e){
            log.error("security filter chain err :",e.getMessage());
        }
        filterChain.doFilter(request,response);// gửi request tiếp tục tới cac thành phần khác

    }
    private String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        /*
        Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
        eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Imh1bmdoeCI
        sImlhdCI6MTUxNjIzOTAyMn0.7gjuuN8uYHv3EoP2sM3v9O0B0APZBFe3pKaYnUpXIdY
         */
        // token có dạng Bearer_...
        if (header!=null && header.startsWith("Bearer ")){
            return header.substring("Bearer ".length());
        }
        return null;
    }
}
