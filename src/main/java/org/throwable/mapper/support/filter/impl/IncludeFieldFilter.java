package org.throwable.mapper.support.filter.impl;

import com.google.common.collect.Lists;
import org.throwable.mapper.common.constant.CommonConstants;
import org.throwable.mapper.support.filter.FieldFilter;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/3/31 0:14
 */
public class IncludeFieldFilter implements FieldFilter {

    private final String fields;

    public IncludeFieldFilter(String fields) {
        this.fields = fields;
    }

    @Override
    public boolean isInculdeFilter() {
        return true;
    }

    @Override
    public List<String> accept() {
        return Lists.newArrayList(fields.split(CommonConstants.COMMA));
    }
}
