package ra.jpa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class FormRegister {
    private String username;
    private String email;
    private String password;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private Date dob;
    private Boolean sex;
    private String fullName;
    private List<Integer> roleIds;
}
