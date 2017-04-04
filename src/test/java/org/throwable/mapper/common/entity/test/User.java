package org.throwable.mapper.common.entity.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.throwable.mapper.common.annotation.ColumnExtend;
import org.throwable.mapper.common.annotation.NameStyle;
import org.throwable.mapper.common.constant.NameStyleEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/1 0:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(value = NameStyleEnum.CAMELCASE_TO_UNDERLINE_UPPERCASE)
@Table(name = "User")
@Entity
public class User extends Person{

	@Column(name = "NAME")
	private String name;
	@Column(name = "AGE")
	private Integer age;
	@Column(name = "BIRTH")
	@ColumnExtend(column = "BIRTH")
	private Date birth;


}
