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
}
