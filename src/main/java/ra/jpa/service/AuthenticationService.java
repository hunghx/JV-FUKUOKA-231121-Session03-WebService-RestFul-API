package ra.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.jpa.dto.request.FormLogin;
import ra.jpa.dto.request.FormRegister;
import ra.jpa.dto.response.JwtResponse;
import ra.jpa.entity.Role;
import ra.jpa.entity.RoleName;
import ra.jpa.entity.User;
import ra.jpa.exception.NotFoundException;
import ra.jpa.exception.UsernamePasswordException;
import ra.jpa.repository.RoleRepository;
import ra.jpa.repository.UserRepository;
import ra.jpa.security.jwt.JwtProvider;
import ra.jpa.security.principle.UserDetailsCustom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

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
    public void register(FormRegister request){
        // /chuyển từ dto -> entity
        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .dob(request.getDob())
                .sex(request.getSex())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .isDeleted(false)
                .isBlocked(false)
                .build();

        // xử lí phần quyền
        Set<Role> setRoles = new HashSet<>();
        if (request.getRoleIds()==null||request.getRoleIds().isEmpty()){
            // cấp quyền mặc đinh
            setRoles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NotFoundException("Role name not found")));
        }else {
            request.getRoleIds().forEach(
                    roleId->{
                        Role role = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("roleId not found"));
                        setRoles.add(role);
                    }
            );
        }
        user.setRoleSet(setRoles);
        userRepository.save(user);
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    };

}
