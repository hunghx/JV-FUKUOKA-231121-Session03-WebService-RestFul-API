package ra.jpa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class FormRegister {
    @NotBlank(message = "khong duoc de trong") // để nguyên thì message trả về là mặc định
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "khong duoc de trong")
    @Pattern(regexp = "^\\w{6,}$", message = "Gồm chữ , số, ít nhất 6 kí tự") // bao gồm chữ và số, ít nhất 6 kí tự
    private String password;
    @NotBlank(message = "khong duoc de trong")
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private Date dob;
    @NotNull
    private Boolean sex;
    @NotBlank(message = "khong duoc de trong")
    private String fullName;
    private List<Integer> roleIds;
}
