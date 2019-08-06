package com.weston.study.test.general.dao;

import com.weston.study.boot.mongo.starter.dao.BaseMongoDao;
import com.weston.study.test.general.domain.UserDO;
import com.weston.study.test.general.domain.UserQuery;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseMongoDao<UserDO, UserQuery> {

    protected UserDao(Datastore ds) {
        super(ds);
    }
}
