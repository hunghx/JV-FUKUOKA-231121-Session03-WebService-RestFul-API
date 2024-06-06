package ra.jpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
    private String id;
    private String fullName;
    private String accessToken;
    private Date expiredDate;
    private final String type = "Bearer Token";
    private Collection<? extends GrantedAuthority> authorities;
}
