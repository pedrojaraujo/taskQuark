package org.pedrojaraujo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PriorityValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriority {
    String message() default "A prioridade deve ser 1(baixo), 2(médio) ou 3(alto).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
