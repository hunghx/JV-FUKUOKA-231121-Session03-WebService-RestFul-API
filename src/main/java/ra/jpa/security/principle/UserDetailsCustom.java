package ra.jpa.security.principle;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.jpa.entity.User;

import java.util.Collection;
import java.util.List;
@Getter
@Builder
public class UserDetailsCustom implements UserDetails {
    private String id;
    private String fullName;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//         danh sách quyền
        return authorities;
    }

    public static UserDetailsCustom build(User user){
         // chuyển đổi danh sách quyền
        List<? extends GrantedAuthority> authoritieList =
                user.getRoleSet()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .toList();
        // biên đổi từ User thành UserDetails
        return UserDetailsCustom.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .authorities(authoritieList)
                .build();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
