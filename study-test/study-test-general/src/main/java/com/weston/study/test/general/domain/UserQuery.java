package com.weston.study.test.general.domain;

import com.weston.study.boot.mongo.starter.annotation.MongoQueryField;
import com.weston.study.boot.mongo.starter.domain.BaseMongoQuery;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class UserQuery extends BaseMongoQuery {

    @MongoQueryField
    private String username;
    @MongoQueryField
    private Integer age;
    @MongoQueryField
    private Date birthday;
    @MongoQueryField
    private BigDecimal amount;
    @MongoQueryField(value = "age", op = MongoQueryField.Op.gt)
    private Integer gtAge;
}
