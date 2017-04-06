package org.throwable.mapper.support.assist;

import com.google.common.collect.Sets;
import org.throwable.mapper.common.constant.NameStyleEnum;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.support.filter.FieldFilter;
import org.throwable.mapper.support.repository.NameStyleContext;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/6 0:38
 */
public abstract class FieldFilterAssistor {

	protected static Set<EntityColumn> filter(Set<EntityColumn> entityColumns, FieldFilter fieldFilter) {
		if (null == entityColumns || entityColumns.size() == 0){
			return Sets.newHashSet();
		}
		NameStyleEnum style = entityColumns.iterator().next().getNameStyle();
		Set<String> filterFields = Sets.newHashSet();
		for (String input : fieldFilter.accept()) {
			filterFields.add(NameStyleContext.convert(style, input));
		}
		if (fieldFilter.isInculdeFilter()) {
			return entityColumns.stream()
					.filter(a -> filterFields.contains(a.getColumn()))
					.collect(Collectors.toSet());
		} else {
			return entityColumns.stream()
					.filter(a -> !filterFields.contains(a.getColumn()))
					.collect(Collectors.toSet());
		}
	}
}
