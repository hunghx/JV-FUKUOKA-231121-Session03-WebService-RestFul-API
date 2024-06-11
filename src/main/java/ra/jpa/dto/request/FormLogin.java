package ra.jpa.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class FormLogin {

    private String username;
    private String password;
}
