package org.throwable.mapper.common.entity.test;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/4/2 12:38
 */
@Data
@NoArgsConstructor
@Entity
public class Person {

	private static final String PREFIX = "PREFIX";
	private transient Date d;
	@Column(name = "SEX")
	protected String sex;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "UUID")
	@Column(name = "ID")
	protected String id;
}
