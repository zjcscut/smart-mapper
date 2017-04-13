package org.throwable.mapper.support.handler;

import com.google.common.collect.Maps;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/13 15:48
 */
public abstract class AbstractMappedXmlSqlSourceBuilder {

    private final static ConcurrentMap<String, SqlSource> sqlSources = Maps.newConcurrentMap();

    protected void addSqlSourceCache(String msId,SqlSource sqlSource){
        sqlSources.putIfAbsent(msId,sqlSource);
    }

    protected abstract SqlSource createScriptSqlSource(Configuration configuration, String msId, String scriptSql,Class<?> parameterType);


}
