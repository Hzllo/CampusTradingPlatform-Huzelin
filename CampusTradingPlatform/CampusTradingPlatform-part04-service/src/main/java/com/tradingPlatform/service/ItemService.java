package com.tradingPlatform.service;

import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.util.BaseService;

import java.util.List;

public interface ItemService extends BaseService<TbItem> {

    /**
     * 发现我上传的商品
     *
     * @param userId 用户ID
     * @return
     */
    List<TbItem> findAllItems(Integer userId);

    /**
     * 首页发现商品
     *
     * @param type 类型
     * @return
     */
    List<TbItem> anyItems(Integer type);
}
