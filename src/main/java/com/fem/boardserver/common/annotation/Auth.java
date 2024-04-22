package com.fem.boardserver.common.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
    Role role() default Role.USER;

    enum Role {
        USER,
        ADMIN
    }
}
