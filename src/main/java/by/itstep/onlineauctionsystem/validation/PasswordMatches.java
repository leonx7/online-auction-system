package by.itstep.onlineauctionsystem.validation;

import org.apache.tomcat.util.buf.Utf8Encoder;
import sun.nio.cs.UTF_8;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Error! The Password and Confirm password fields do not match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

