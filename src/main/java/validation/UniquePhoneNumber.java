package validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniquePhoneNumberValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNumber {
    String message() default "not a unique phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
