package ra.jpa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = UsernameValidator.class
)
@Target({ElementType.FIELD}) // đánh dẫu trên trường
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    String message() default "Username đã tồn tại !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
