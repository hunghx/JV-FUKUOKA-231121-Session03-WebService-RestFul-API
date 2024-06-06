package ra.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ra.jpa.dto.request.FormLogin;
import ra.jpa.dto.response.JwtResponse;
import ra.jpa.exception.UsernamePasswordException;
import ra.jpa.security.jwt.JwtProvider;
import ra.jpa.security.principle.UserDetailsCustom;

import java.util.Date;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtProvider jwtProvider;

    public JwtResponse doLogin(FormLogin formLogin) {

        // kiểm tra thông tin
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        } catch (AuthenticationException e) {
            throw new UsernamePasswordException("username or pass incorrect");
        }
        // đã xa thưc
        //lấy ra user details
        UserDetailsCustom detailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        // trả về JWT response
         String accessToken = jwtProvider.generateToken(detailsCustom);
         return JwtResponse.builder()
                 .id(detailsCustom.getId())
                 .accessToken(accessToken)
                 .expiredDate(new Date(new Date().getTime()+86400000))
                 .authorities(detailsCustom.getAuthorities())
                 .fullName(detailsCustom.getFullName())
                 .build();

    }
}
