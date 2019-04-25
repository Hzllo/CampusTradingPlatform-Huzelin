package com.tradingPlatform.service;

import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.util.BaseService;

import java.util.List;

public interface CommentService extends BaseService<TbComment> {

    /**
     * 获取商品内容
     *
     * @param itemId 商品Id
     * @return
     */
    List<TbComment> findListByItemId(Integer itemId);

    /**
     * 查询未读消息
     *
     * @param userId
     * @return
     */
    int informationCount(Integer userId);

    /**
     * 未读消息
     *
     * @param userId
     * @return
     */
    List informationNoLook(Integer userId);

    /**
     * 已读消息
     *
     * @param userId
     * @return
     */
    List informationLook(Integer userId);

    /**
     * 设为已读
     *
     * @param commentId
     * @return
     */
    boolean setLook(Integer commentId);

    /**
     * 设全部为已读
     *
     * @param userId
     * @return
     */
    boolean setAllLook(Integer userId);
}
