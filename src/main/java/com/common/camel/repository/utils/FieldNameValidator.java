package com.common.camel.repository.utils;

import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class FieldNameValidator {

    private final Set<String> fieldNames;

    public FieldNameValidator(Class clazz) {
        fieldNames = Stream.of(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(toSet());
    }

    public boolean isValid(String fieldName) {
        return fieldNames.contains(fieldName);
    }

    public String ensureValid(String fieldName) {
        if (!isValid(fieldName)) {
            throw new IllegalArgumentException(String.format("Некорректное значение поля: %s", fieldName));
        }
        return fieldName;
    }

    public Sort ensureValid(Sort sort) {
        sort.stream().forEach(o -> ensureValid(o.getProperty()));
        return sort;
    }

}
