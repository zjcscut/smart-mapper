package org.throwable.mapper.common.entity.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.throwable.mapper.common.annotation.NameStyle;
import org.throwable.mapper.common.constant.NameStyleEnum;

import javax.persistence.*;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/4/14 12:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(value = NameStyleEnum.CAMELCASE_TO_UNDERLINE_UPPERCASE)
@Table(name = "USER_AUTO_INCREASE")
@Entity
public class UserLong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private Integer age;
}
