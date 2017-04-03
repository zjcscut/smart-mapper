package org.throwable.mapper.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String sex;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
}
