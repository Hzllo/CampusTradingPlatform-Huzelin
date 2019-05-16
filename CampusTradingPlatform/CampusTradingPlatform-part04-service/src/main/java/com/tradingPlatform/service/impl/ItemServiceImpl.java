package com.tradingPlatform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.bean.TbItemThinkVO;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.CommentMapper;
import com.tradingPlatform.mapper.ItemMapper;
import com.tradingPlatform.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<TbItem> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private CommentMapper commentMapper;

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
        example.createCriteria().andEqualTo("userId", userId);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        return itemList;
    }

    /**
     * 首页获取商品
     *
     * @param type 类型
     * @return
     */
    @Override
    public List<TbItem> anyItems(Integer type) {

        PageHelper.startPage(0, 10);

        Example example = new Example(TbItem.class);
        //智能排序
        if (type == 1) {
            example.orderBy("num").asc().orderBy("time").desc();
        }
        //根据时间排序
        if (type == 2) {
            example.orderBy("time").desc();
        }
        //根据评论数排序
        if (type == 3) {
            example.orderBy("num").desc();
        }
        Example.Criteria criteria = example.createCriteria();
        List<TbItem> itemList = itemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);

        return pageInfo.getList();
    }

    /**
     * 搜索
     *
     * @param content 搜索内容
     * @return
     */
    @Override
    public List<TbItem> search(String content) {

        Example example = new Example(TbItem.class);
        example.orderBy("time").desc();
        Example.Criteria criteria = example.createCriteria().
                andLike("title", "%" + content + "%").
                orLike("content", "%" + content + "%");
        List<TbItem> itemList = itemMapper.selectByExample(example);
        return itemList;
    }

    /**
     * 查看我想要的商品
     *
     * @param userId
     * @return
     */
    @Override
    public List<TbItemThinkVO> myThinkService(Integer userId) {
        Example example = new Example(TbComment.class);
        example.orderBy("time").desc();
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("type", 1);
        List<TbComment> commentList = commentMapper.selectByExample(example);
        List<TbItemThinkVO> tbItemThinkVOList = new ArrayList<>();
        commentList.stream().forEach(comment -> {
            TbItemThinkVO tbItemThinkVO = new TbItemThinkVO();
            TbItem item = itemMapper.selectByPrimaryKey(comment.getItemId());
            BeanUtils.copyProperties(item, tbItemThinkVO);
            tbItemThinkVO.setComment(comment);
            tbItemThinkVOList.add(tbItemThinkVO);
        });
        return tbItemThinkVOList;
    }

    /**
     * 我的预订
     *
     * @param userId
     * @return
     */
    @Override
    public List<TbItem> myMark(Integer userId) {
        Example example = new Example(TbItem.class);
        example.orderBy("time").desc();
        example.createCriteria().andEqualTo("markId", userId).andEqualTo("mark", 1);
        return itemMapper.selectByExample(example);
    }
}
