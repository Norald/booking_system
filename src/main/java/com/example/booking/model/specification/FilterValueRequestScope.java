package com.example.booking.model.specification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@RequestScope
@Component
public class FilterValueRequestScope implements Serializable {
    @Serial
    private static final long serialVersionUID = 7027582877460003232L;

    private transient Map<String, Object> filterValue = new HashMap<>();

    public Object getValue(String filterKey) {
        return filterValue.get(filterKey);
    }
}
