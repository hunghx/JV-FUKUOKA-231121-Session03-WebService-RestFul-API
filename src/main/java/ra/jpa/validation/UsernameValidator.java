package ra.jpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.jpa.service.AuthenticationService;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        // tra về sai tức là ko hợp lệ
        return !authenticationService.existsByUsername(value);
    }
}
