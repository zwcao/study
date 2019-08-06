package com.weston.study.boot.mongo.starter.dao;

import com.mongodb.WriteResult;
import com.weston.study.boot.mongo.starter.annotation.MongoQueryField;
import com.weston.study.boot.mongo.starter.domain.BaseMongoDO;
import com.weston.study.boot.mongo.starter.domain.BaseMongoQuery;
import com.weston.study.core.common.contract.query.PagedResult;
import com.weston.study.core.common.exception.BizException;
import com.weston.study.core.common.exception.Guard;
import com.weston.study.core.common.reflect.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.MappedClass;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class BaseMongoDao<E extends BaseMongoDO, Q extends BaseMongoQuery> extends BasicDAO<E, ObjectId> implements MongoDao<E, Q> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Map<Class<?>, List<QueryField>> queryFieldCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    protected BaseMongoDao(Datastore ds) {
        super(ds);
        this.init((Class<Q>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]));
    }

    @Override
    public String insert(E item) {
        item.setCreatedTime(new Date());
        Key<E> key = this.save(item);
        String id = key.getId().toString();
        item.setId(id);
        return id;
    }

    @Override
    public Integer updateById(E item) {
        UpdateResults ur = this.update(this.getByIdQuery(item), this.getUpdateOperations(item));
        return ur.getUpdatedCount();
    }

    @Override
    public E queryById(String id) {
        return this.get(new ObjectId(id));
    }

    @Override
    public Integer deleteById(String id) {
        WriteResult wr = this.deleteById(new ObjectId(id));
        return wr.getN();
    }

    @Override
    public List<E> queryByIds(List<String> ids) {
        return this.getByIdsQuery(this.getEntityClass(), ids).asList();
    }

    @Override
    public int count(Q query) {
        return (int) this.getQuery(this.getEntityClass(), query).count();
    }

    @Override
    public List<E> query(Q query) {
        Query<E> mongoQuery = this.getQuery(this.getEntityClass(), query);
        FindOptions options = this.getFindOptions(this.getEntityClass(), query);
        String ordBy = query.getOrderByFields();
        if (StringUtils.isNotBlank(ordBy)) {
            return mongoQuery.order(ordBy).asList(options);
        } else {
            return mongoQuery.asList(options);
        }
    }

    @Override
    public PagedResult<E> queryForPage(Q query) {
        int count = this.count(query);
        List<E> list;
        if (count > 0) {
            list = this.query(query);
        } else {
            list = Collections.emptyList();
        }
        return new PagedResult<>(query.getCurPage(), query.getPageSize(), count, list);
    }

    @Override
    public void batchInsert(List<E> items) {
        items.forEach(p -> {
            this.insert(p);
        });
    }

    @Override
    public Integer batchUpdateById(List<E> items) {
        int n = 0;
        for (E i : items) {
            n = n + this.updateById(i);
        }
        return n;
    }

    /**
     * init
     */
    private void init(Class<?> clazz) {
        if (queryFieldCache.containsKey(clazz)) {
            return;
        }
        List<Field> allFields = ReflectionUtils.getAllAccessibleFields(clazz);
        List<QueryField> queryFields = new ArrayList<>();
        for (Field field : allFields) {
            MongoQueryField mongoField = field.getAnnotation(MongoQueryField.class);
            if (mongoField == null) {
                continue;
            }
            queryFields.add(new QueryField(mongoField, field));
        }
        Guard.notNullOrEmpty(queryFields, clazz.getName() + "contains not exists any MongoField");
        queryFieldCache.put(clazz, queryFields);
    }

    protected Query<E> getByIdQuery(E item) {
        return this.createQuery().field("_id").equal(new ObjectId(item.getId()));
    }

    protected Query<E> getByIdsQuery(Class<E> clazz, List<String> ids) {
        return this.createQuery().field("_id").in(ids.stream().map(p -> new ObjectId(p)).collect(Collectors.toList()));
    }

    @SuppressWarnings("deprecation")
    protected Query<E> getQuery(Class<E> clazz, Q query) {
        List<QueryField> queryFields = queryFieldCache.get(query.getClass());
        Query<E> mgQuery = this.createQuery();
        try {
            DatastoreImpl d = (DatastoreImpl) this.getDatastore();
            MappedClass mappedClass = d.getMapper().getMappedClass(clazz);
            MappedField mappedIdField = mappedClass.getMappedIdField();

            for (QueryField queryField : queryFields) {
                Object obj = queryField.field.get(query);
                if (obj == null) {
                    continue;
                }
                if (obj instanceof Collection) {
                    if (((Collection) obj).size() == 0) {
                        continue;
                    }
                }
                if (mappedIdField.getField().getName().equalsIgnoreCase(queryField.mongoFieldName)) {
                    obj = new ObjectId(obj.toString());
                }
                switch (queryField.mongoField.op()) {
                    case eq:
                        mgQuery.field(queryField.mongoFieldName).equal(obj);
                        break;
                    case gt:
                        mgQuery.field(queryField.mongoFieldName).greaterThan(obj);
                        break;
                    case gte:
                        mgQuery.field(queryField.mongoFieldName).greaterThanOrEq(obj);
                        break;
                    case lt:
                        mgQuery.field(queryField.mongoFieldName).lessThan(obj);
                        break;
                    case lte:
                        mgQuery.field(queryField.mongoFieldName).lessThanOrEq(obj);
                        break;
                    case in:
                        mgQuery.field(queryField.mongoFieldName).in((Iterable<?>) obj);
                        break;
                    case notin:
                        mgQuery.field(queryField.mongoFieldName).notIn((Iterable<?>) obj);
                        break;
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("BaseMongoDao.fillQuery异常", e);
            throw new BizException("mongo getQuery exception");
        }
        return mgQuery;
    }

    protected FindOptions getFindOptions(Class<E> clazz, Q query) {
        FindOptions findOptions = new FindOptions();
        if (query == null || !query.isUsePage()) {
            return findOptions;
        }
        return findOptions.skip(query.getIndex()).limit(query.getPageSize());
    }

    @SuppressWarnings("deprecation")
    protected UpdateOperations<E> getUpdateOperations(E item) {
        item.setLastModifiedTime(new Date());
        UpdateOperations<E> options = this.createUpdateOperations();
        try {
            DatastoreImpl d = (DatastoreImpl) this.getDatastore();
            MappedClass mappedClass = d.getMapper().getMappedClass(item);
            MappedField mappedIdField = mappedClass.getMappedIdField();
            for (MappedField mappedField : mappedClass.getPersistenceFields()) {
                if (mappedIdField == mappedField) {
                    continue;
                }
                Object obj = mappedField.getFieldValue(item);
                if (obj == null) {
                    continue;
                }
                options.set(mappedField.getField().getName(), obj);
            }
        } catch (Exception e) {
            logger.error("mongo.getUpdateOperations exception", e);
            throw new BizException("mongo getUpdateOperations exception");
        }
        return options;
    }

    private static class QueryField {
        MongoQueryField mongoField;
        Field field;
        String mongoFieldName;

        public QueryField(MongoQueryField mongoField, Field field) {
            super();
            this.mongoField = mongoField;
            this.field = field;
            this.mongoFieldName = StringUtils.isBlank(mongoField.value()) ? field.getName() : mongoField.value();
        }
    }

}
