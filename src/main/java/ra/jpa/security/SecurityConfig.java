package ra.jpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.jpa.security.jwt.JwtAuthTokenFilter;
import ra.jpa.security.jwt.JwtProvider;
import ra.jpa.security.principle.UserDetailsServiceCustom;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public AuthenticationException authenticationException(){
        return new AuthenticationException();
    }
    @Bean
    public AccessDeniedException accessDeniedException(){
        return new AccessDeniedException();
    }
//    ;
//    @Autowired
//    private JwtProvider jwtProvider; // bo cung cap 3 chuc nang cua jwt

    @Autowired
    private UserDetailsServiceCustom userDetailsServiceCustom; // cung cap chuc nang loadByUsername

//    @Bean
//    public JwtAuthTokenFilter jwtAuthTokenFilter() { // laf 1 tang, hay 1 mang loc request gui len
//        return new JwtAuthTokenFilter(jwtProvider, userDetailsServiceCustom);
//    }
    @Autowired
    private JwtAuthTokenFilter jwtAuthTokenFilter;
    @Bean
    public PasswordEncoder passwordEncoder() { // mã hóa mật khẩu 1 chiều
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceCustom);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager(); // đối tượng dùng để xác thuc thông qua username và password
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //bộ lọc quyên
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // phi trạng thai
                .exceptionHandling(handler->{
                    handler
                            .authenticationEntryPoint(authenticationException()) // người dùng chưa xác thực
                            .accessDeniedHandler(accessDeniedException()); // ko có quyền truy cập
                })
                .authorizeHttpRequests(
                            auth -> auth.requestMatchers("/api.com/v1/auth/**").permitAll() // công khai , ko cần xác thực
                                .requestMatchers("/api.com/v1/admin/**").hasAuthority("ROLE_ADMIN") // có quyền admin
//                                .requestMatchers("/api.com/v1/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api.com/v1/user/**").hasAuthority("ROLE_USER")
                                .requestMatchers("/api.com/v1/authenticated/**").authenticated() // bắt buộc phải xác thực
                                .requestMatchers("/api.com/v1/manager/**").hasAuthority("ROLE_MANAGER")
                                .requestMatchers("/api.com/v1/user-manager/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER")
                                .anyRequest().authenticated() // bắt buộc phải xác thực
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
