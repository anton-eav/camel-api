package com.common.camel.repository.utils;

import com.google.common.base.CaseFormat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class SqlUtils {

    public static String toSearchString(String value) {
        if (value == null) return "";
        return '%' + value.replace(' ', '%') + '%';
    }

    public static <T> String getOrderBySql(Sort sort) {
        return sort.isSorted() ? String.format(" order by %s \n", toOrderByParameters(sort)) : "";
    }

    private static <T> String toOrderByParameters(Sort sort) {
        return sort.stream().map(SqlUtils::toOrderByParameter).collect(joining(", "));
    }

    private static <T> String toOrderByParameter(Sort.Order order) {
        return toColumnName(order.getProperty()) + " " + order.getDirection().name();
    }

    public static String toColumnName(String fieldName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }

    private Map<String, Object> prepareParameters(Object filter, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.putAll(getParams(filter));
        if (pageable != null) {
            params.putAll(getParams(pageable));
        }
        return params;
    }

    private Map<String, Object> getParams(Object object) {
        BeanPropertySqlParameterSource objParams = new BeanPropertySqlParameterSource(object);
        return Stream.of(Objects.requireNonNull(objParams.getParameterNames()))
                .collect(HashMap::new, (m, v) -> m.put(v, objParams.getValue(v)), HashMap::putAll);
    }

}
