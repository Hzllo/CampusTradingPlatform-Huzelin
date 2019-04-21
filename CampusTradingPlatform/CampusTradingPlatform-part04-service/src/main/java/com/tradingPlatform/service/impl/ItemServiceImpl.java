package com.tradingPlatform.service.impl;

import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.ItemMapper;
import com.tradingPlatform.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<TbItem> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 发现我上传的商品
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<TbItem> findAllItems(Integer userId) {

        Example example = new Example(TbItem.class);
        example.orderBy("time").desc();
        Example.Criteria criteria = example.createCriteria().andEqualTo("userId", userId);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        return itemList;
    }
}
