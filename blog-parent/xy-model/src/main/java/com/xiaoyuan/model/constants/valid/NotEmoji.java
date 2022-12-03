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
@Constraint(validatedBy = NotEmoji.isContainEmoji.class)
public @interface NotEmoji {

    String message() default "不能包含表情符号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    class isContainEmoji implements ConstraintValidator<NotEmoji, String> {

        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
            return !StringMatch.isContainsEmoji(str);
        }
    }
}
