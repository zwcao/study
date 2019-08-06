package com.weston.study.boot.mongo.starter.dao;

import com.weston.study.core.common.contract.query.PagedResult;

import java.util.List;

public interface MongoDao<E, Q> {

    /**
     * 插入领域实体对象
     *
     * @param item
     * @return
     */
    String insert(E item);

    /**
     * 通过ID更新领域实体对象
     *
     * @param item
     * @return
     */
    Integer updateById(E item);

    /**
     * 通过ID获取领域实体对象
     *
     * @param id
     * @return
     */
    E queryById(String id);

    /**
     * 通过ID删除领域实体对象
     *
     * @param id
     * @return
     */
    Integer deleteById(String id);

    /**
     * 根据ID批量查询领域实体对象
     *
     * @param ids
     * @return
     */
    List<E> queryByIds(List<String> ids);

    /**
     * 统计满足查询条件的记录个数
     *
     * @param query
     * @return
     */
    int count(Q query);

    /**
     * 根据条件查询领域实体对象集合, 用于非分页
     * 
     * @param query
     * @return
     */
    List<E> query(Q query);

    /**
     * 用于分页查询, 根据条件查询领域实体对象数据并返回分页信息
     *
     * @param query
     * @return
     */
    PagedResult<E> queryForPage(Q query);

    /**
     * 批量插入领域实体对象
     *
     * @param items
     * @return
     */
    void batchInsert(List<E> items);

    /**
     * 批量更新领域实体对象
     *
     * @param items
     * @return
     */
    Integer batchUpdateById(List<E> items);
}
