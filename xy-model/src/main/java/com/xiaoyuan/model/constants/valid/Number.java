package com.xiaoyuan.model.constants.valid;

import com.xiaoyuan.common_util.match.StringMatch;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER}) //定义可以在字段上使用
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Number.checkNumber.class)
public @interface Number {

    String message() default "编号错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    class checkNumber implements ConstraintValidator<Number, String> {

        @Override
        public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
            return StringMatch.checkNumber(number);
        }
    }
}
