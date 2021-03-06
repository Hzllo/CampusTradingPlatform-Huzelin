package com.tradingPlatform.service;

import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.bean.TbItemThinkVO;
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

    /**
     * 搜索
     *
     * @param content 搜索内容
     * @return
     */
    List<TbItem> search(String content);

    /**
     * 查看我想要的商品
     *
     * @param userId
     * @return
     */
    List<TbItemThinkVO> myThinkService(Integer userId);

    /**
     * 我的预订列表
     *
     * @param userId
     * @return
     */
    List<TbItem> myMark(Integer userId);
}
