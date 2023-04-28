package com.xiaoyuan.common.constants.valid;

import com.xiaoyuan.common.util.StringMatch;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER}) //定义可以在字段上使用
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotChinese.isContainChinese.class)
public @interface NotChinese {

    String message() default "不能包含中文字符";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    class isContainChinese implements ConstraintValidator<NotChinese, String> {

        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
            return !StringMatch.isContainsChinese(str);
        }
    }
}
