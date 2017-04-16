package org.throwable.mapper.support.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.throwable.mapper.BatchExecutorService;
import org.throwable.mapper.common.entity.EntityColumn;
import org.throwable.mapper.configuration.prop.SmartMapperProperties;
import org.throwable.mapper.support.handler.DefaultMappedStatementHander;
import org.throwable.mapper.support.plugins.condition.Condition;
import org.throwable.mapper.support.plugins.generator.identity.MultipleJdbc3KeyGenerator;
import org.throwable.mapper.support.plugins.pagination.PageModel;
import org.throwable.mapper.support.plugins.pagination.Pager;


import java.util.List;
import java.util.Map;

import static org.throwable.mapper.common.constant.CommonConstants.PARAMMAP_DEFAULT;
import static org.throwable.mapper.common.constant.CommonConstants.RESULTMAP_DEFAULT;
import static org.throwable.mapper.support.repository.AbstractMappedSqlRepository.*;


/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/10 2:30
 */
@Service(value = "batchExecutorService")
@EnableConfigurationProperties(SmartMapperProperties.class)
@Slf4j
public class DefaultBatchExecutorService extends BatchExecutor implements BatchExecutorService {

    @Autowired
    private DefaultMappedStatementHander defaultMappedStatementHander;

    @Autowired
    private SmartMapperProperties smartMapperProperties;

    @Override
    public <T> int executeBatchUpdate(List<T> list, int batchSize,boolean skipNull) {
        assertListParams(list, batchSize);
        int size = list.size();
        Class<?> clazz = list.iterator().next().getClass();
        //懒初始化classTable
        DefaultMappedClassProcessor processor = new DefaultMappedClassProcessor(clazz,
                smartMapperProperties.getPropertiesConfiguration(), DYNAMIC_BATCHUPDATE);
        String msId = processor.getMsId();
        SqlSession sqlSession = defaultMappedStatementHander.getSqlSessionFactory().openSession(ExecutorType.BATCH, NONE_AUTO_COMMIT);
        batchOperation(list, batchSize, msId, processor.getParamsMap(), new BatchProcessor() {

            @Override
            public <T> void beforeExecuteBatch(List<T> list) {
                log.debug(String.format("begin executeBatchUpdate,target:List<%s>,executeSize:%d,batchSize:%d",
                        clazz.getCanonicalName(), size, batchSize));
                //檢查主鍵是否都存在
                EntityColumn keyColumn = checkExistsPriamryKeyAndReturn(clazz);
                //懒注册MappedStatement
                if (!defaultMappedStatementHander.hasRegisterMappedStatement(msId)) {
                    defaultMappedStatementHander.addScriptSqlMappedStatement(
                            createDynamicBatchUpdateScriptSql(clazz, msId, skipNull),
                            msId,
                            SqlCommandType.UPDATE,
                            msId.concat(PARAMMAP_DEFAULT),
                            Map.class,
                            msId.concat(RESULTMAP_DEFAULT),
                            Integer.class,
                            msId,
                            keyColumn.getProperty(),
                            keyColumn.getColumn(),
                            new NoKeyGenerator());
                }
            }

            @Override
            public int executeBatchOperation(String msId, Map<String, Object> paramsMap) {
                int updateCount = sqlSession.update(msId, paramsMap);
                sqlSession.commit();
                sqlSession.clearCache();
                return updateCount;
            }

            @Override
            public void afterExecuteBatch() {
                sqlSession.close();
            }
        });
        return size;
    }

    @Override
    public <T> int executeBatchInsert(List<T> list, int batchSize) {
        assertListParams(list, batchSize);
        int size = list.size();
        Class<?> clazz = list.iterator().next().getClass();
        //懒初始化classTable
        DefaultMappedClassProcessor processor = new DefaultMappedClassProcessor(clazz,
                smartMapperProperties.getPropertiesConfiguration(), DYNAMIC_BATCHINSERT);
        String msId = processor.getMsId();
        SqlSession sqlSession = defaultMappedStatementHander.getSqlSessionFactory().openSession(ExecutorType.BATCH, NONE_AUTO_COMMIT);
        batchOperation(list, batchSize, msId, processor.getParamsMap(), new BatchProcessor() {

            @Override
            public <T> void beforeExecuteBatch(List<T> list) {
                log.debug(String.format("begin executeBatchInsert,target:List<%s>,executeSize:%d,batchSize:%d",
                        clazz.getCanonicalName(), size, batchSize));
                //檢查主鍵是否都存在
                EntityColumn keyColumn = checkExistsPriamryKeyAndReturn(clazz);
                //如果主键为自增长,设置批量主键自增长主键生成器,否则批量反射调用外部配置的Ognl主键策略写入主键
                KeyGenerator keyGenerator;
                if (keyColumn.isAutoIncrease()) {  //批量自增长
                    keyGenerator = new MultipleJdbc3KeyGenerator();
                } else if (keyColumn.isUUID()) {  //非自增长并且是UUID,需要批量写入id,使用配置的OGNL主键策略
                    String ognlStrategy = smartMapperProperties.getPropertiesConfiguration().getOgnlIdentityStrategy();
                    autoCreatePrimaryKeyByOgnlStrategy(list, keyColumn, ognlStrategy);
                    keyGenerator = new NoKeyGenerator();
                } else {
                    keyGenerator = new NoKeyGenerator();  //默认不进行主键回写
                }
                //懒注册MappedStatement
                if (!defaultMappedStatementHander.hasRegisterMappedStatement(msId)) {
                    defaultMappedStatementHander.addScriptSqlMappedStatement(
                            createDynamicBatchInsertScriptSql(clazz, msId, keyColumn.isUUID()),
                            msId,
                            SqlCommandType.INSERT,
                            msId.concat(PARAMMAP_DEFAULT),
                            Map.class,
                            msId.concat(RESULTMAP_DEFAULT),
                            Integer.class,
                            msId,
                            keyColumn.getProperty(),
                            keyColumn.getColumn(),
                            keyGenerator);
                }
            }

            @Override
            public int executeBatchOperation(String msId, Map<String, Object> paramsMap) {
                int insertCount = sqlSession.insert(msId, paramsMap);
                sqlSession.commit();
                sqlSession.clearCache();
                return insertCount;
            }

            @Override
            public void afterExecuteBatch() {
                sqlSession.close();
            }
        });
        return size;
    }

    @Override
    public <T> int update(T t, boolean skipNull) {
    	log.error("DefaultBatchExecutorService update method execute!!!!!!");
        return 1;
    }

    @Override
    public <T> int updateByCondition(T t, Condition condition, boolean skipNull) {
        return 1;
    }

    @Override
    public <T> T selectOneByCondition(Class<T> clazz, Condition condition) {
        return null;
    }

    @Override
    public <T> List<T> selectListByCondtion(Class<T> clazz, Condition condition) {
        return null;
    }

    @Override
    public <T> long countByCondtion(Class<T> clazz, Condition condition) {
        return 0;
    }

    @Override
    public <T> PageModel<T> selectListByCondtionPage(Class<T> clazz, Condition condition, Pager pager) {
        return null;
    }
}
