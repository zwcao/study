package com.weston.study.test.general.domain;

import com.weston.study.boot.mongo.starter.domain.BaseMongoDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@Entity(value = "test_user", noClassnameStored = true)
//@Indexes(@Index(fields = {@Field(value="age", type= IndexType.ASC)}))
@Indexes({ @Index("age")})

@ToString
public class UserDO extends BaseMongoDO {

    private static final long serialVersionUID = -1L;

    private String username;
    private Integer age;
    private Date birthday;
    private BigDecimal amount;
}