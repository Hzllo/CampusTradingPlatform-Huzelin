package com.tradingPlatform.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradingPlatform.util.BaseService;
import com.tradingPlatform.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    /**
     * 通过主键获取单个结果
     *
     * @param id
     * @return 结果对象
     */
    @Override
    public T findByPrimaryKeyService(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 获取全部结果
     *
     * @return 结果列表对象
     */
    @Override
    public List<T> findAllService() {
        return mapper.selectAll();
    }

    /**
     * 根据某个条件获取结果
     *
     * @param t
     * @return 结果列表对象
     */
    @Override
    public List<T> findByConditionService(T t) {
        return mapper.select(t);
    }

    /**
     * 分页查询对象
     *
     * @param page
     * @param rows
     * @return 结果列表对象
     */
    @Override
    public PageResult findPageService(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        PageInfo<T> pageInfo = new PageInfo<T>(mapper.selectAll());
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 添加列表
     *
     * @param t
     */
    @Override
    public void addService(T t) {
        mapper.insert(t);
    }

    /**
     * 根据ID删除对象
     *
     * @param id
     */
    @Override
    public void deleteByPrimaryKeyService(Serializable id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据条件删除对象
     *
     * @param t
     */
    @Override
    public void deleteByConditionService(T t) {
        mapper.delete(t);
    }

    /**
     * 批量删除对象
     *
     * @param ids
     */
    @Override
    public void deleteByIdsService(Serializable[] ids) {
        for (Serializable id : ids) {
            mapper.deleteByPrimaryKey(id);
        }


    }

    /**
     * 更新对象
     *
     * @param t
     */
    @Override
    public void updateService(T t) {

        mapper.updateByPrimaryKeySelective(t);

    }
}
