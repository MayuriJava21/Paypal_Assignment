/**
 * 
 */
package com.paypal.bfs.test.employeeserv.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

/**
 * @author Mayuri
 *
 */
@Documented
@Constraint(validatedBy = ZipCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZipCode {
    String message() default "zip code must be five-digit and nine-digit (called ZIP + 4) formats";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}