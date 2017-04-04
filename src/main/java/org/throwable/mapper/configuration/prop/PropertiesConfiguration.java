package org.throwable.mapper.configuration.prop;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.throwable.mapper.SmartMapper;
import org.throwable.mapper.common.constant.IdentityDialectEnum;
import org.throwable.mapper.common.constant.NameStyleEnum;

import static org.throwable.mapper.common.constant.IdentityDialectEnum.MYSQL;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/4 0:01
 */

@NoArgsConstructor
@Data
public class PropertiesConfiguration {

	//默认注册SmartMapper
	private Class<?>[] mappers = new Class[]{SmartMapper.class};
	private String UUID = "@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")";
	private IdentityDialectEnum identity = MYSQL;
	private String IDENTITY = identity.getIdentityRetrievalStatement();
	private boolean BEFORE = false;
	private String seqFormat;
	private String catalog;
	private String schema;
	private boolean enableMethodAnnotation = false;
	private boolean notEmpty = false;
	private NameStyleEnum style = NameStyleEnum.NORMAL;

	//拼接查询前缀
	public String getPrefix() {
		if (StringUtils.isNotEmpty(this.catalog)) {
			return this.catalog;
		}
		if (StringUtils.isNotEmpty(this.schema)) {
			return this.schema;
		}
		return "";
	}

	public String getIDENTITY() {
		return IDENTITY;
	}
}
