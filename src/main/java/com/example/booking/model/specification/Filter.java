package com.example.booking.model.specification;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
    String key();
    FilterContext context();
}
