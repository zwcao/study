package com.weston.study.boot.mongo.starter.domain;

import com.weston.study.boot.mongo.starter.annotation.MongoQueryField;
import com.weston.study.core.common.contract.query.Pageable;
import com.weston.study.core.common.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * mongo查询base
 */
@Getter
@Setter
public abstract class BaseMongoQuery extends Pageable implements Serializable {

    private static final long serialVersionUID = 1L;

    @MongoQueryField
    private String id;
    @MongoQueryField
    private Long creatorId;
    @MongoQueryField
    private Date createdTime;
    @MongoQueryField
    private Long lastModifierId;
    @MongoQueryField
    private Date lastModifiedTime;

    private boolean usePage = true;
    private Map<String, String> orderByFileds;

    public BaseMongoQuery orderBy(String field, OrderType orderType) {
        if (orderByFileds == null) {
            orderByFileds = new HashMap<>();
        }
        orderByFileds.put(field, orderType == OrderType.DESC ? "-" : "");
        return this;
    }

    public String getOrderByFields() {
        if (orderByFileds == null) {
            return null;
        }
        return orderByFileds.entrySet().stream().map(p -> p.getValue() + p.getKey()).collect(Collectors.joining(","));
    }
}
