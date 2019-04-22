package com.tradingPlatform.service.impl;

import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.CommentMapper;
import com.tradingPlatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<TbComment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<TbComment> findListByItemId(Integer itemId) {

        Example example = new Example(TbComment.class);
        example.orderBy("time").desc();
        Example.Criteria criteria = example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("type", 2);
        List<TbComment> itemList = commentMapper.selectByExample(example);
        return itemList;
    }
}
