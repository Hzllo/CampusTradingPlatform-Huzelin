package com.tradingPlatform.util;
import com.tradingPlatform.vo.PageResult;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

    /**
     * 通过主键获取单个结果
     * @param id
     * @return 结果对象
     */
    T findByPrimaryKeyService(Serializable id);

    /**
     * 获取全部结果
     * @return 结果列表对象
     */
    List<T> findAllService();

    /**
     * 根据某个条件获取结果
     * @param t
     * @return 结果列表对象
     */
    List<T> findByConditionService(T t);

    /**
     * 分页查询对象
     * @param page
     * @param rows
     * @return 结果列表对象
     */
    PageResult findPageService(Integer page, Integer rows);

    /**
     * 添加列表
     * @param t
     */
    void addService(T t);

    /**
     * 根据ID删除对象
     * @param id
     */
    void deleteByPrimaryKeyService(Serializable id);

    /**
     * 根据条件删除对象
     * @param t
     */
    void deleteByConditionService(T t);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteByIdsService(Serializable[] ids);

    /**
     * 更新对象
     * @param t
     */
    void updateService(T t);
}
