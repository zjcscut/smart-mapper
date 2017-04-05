package org.throwable.mapper.support.assist;

import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.support.filter.FieldFilter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/6 0:38
 */
public abstract class FieldFilterAssistor {

    protected Set<EntityColumn> filter(Set<EntityColumn> entityColumns, FieldFilter fieldFilter) {
        if (fieldFilter.isInculdeFilter()) {
            return entityColumns.stream().filter(a ->
                    fieldFilter.accept().contains(a.getColumn())
            ).collect(Collectors.toSet());
        } else {

            return null;
        }
    }
}
