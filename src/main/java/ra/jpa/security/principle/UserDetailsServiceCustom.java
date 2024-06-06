package ra.jpa.security.principle;

import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.jpa.entity.User;
import ra.jpa.repository.UserRepository;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // đăng nhâp bằng cái gì
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("username not found"));
        return UserDetailsCustom.build(user);
    }
}
