package com.tradingPlatform.service.impl;

import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.bean.TbCommentVO;
import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.impl.BaseServiceImpl;
import com.tradingPlatform.mapper.CommentMapper;
import com.tradingPlatform.mapper.ItemMapper;
import com.tradingPlatform.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<TbComment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<TbComment> findListByItemId(Integer itemId) {

        Example example = new Example(TbComment.class);
        example.orderBy("time").desc();
        example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("type", 2);
        List<TbComment> itemList = commentMapper.selectByExample(example);
        return itemList;
    }

    /**
     * 查询未读消息数量
     *
     * @param userId
     * @return
     */
    @Override
    public int informationCount(Integer userId) {
        //找出与之相关的物品
        List<Integer> itemIds = getItemsByUserId(userId);

        //找出未读评论
        Example example1 = new Example(TbComment.class);
        example1.createCriteria().andEqualTo("look", 0).andIn("itemId", itemIds);
        return commentMapper.selectCountByExample(example1);
    }

    private List<Integer> getItemsByUserId(Integer userId) {
        Example example = new Example(TbItem.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        List<Integer> itemIds = new ArrayList<>();
        itemList.stream().forEach(entity -> itemIds.add(entity.getItemId()));
        return itemIds;
    }

    /**
     * 查询未读消息
     *
     * @param userId
     * @return
     */
    @Override
    public List<TbCommentVO> informationNoLook(Integer userId) {
        //找出与之相关的物品
        List<Integer> itemIds = getItemsByUserId(userId);
        //找出未读评论
        Example example1 = new Example(TbComment.class);
        example1.createCriteria().andEqualTo("look", 0).andIn("itemId", itemIds);
        example1.orderBy("time").desc();
        List<TbComment> commentList = commentMapper.selectByExample(example1);
        return commentToCommentVO(commentList);
    }

    /**
     * comment转commentVO
     *
     * @param commentList
     * @return
     */
    private List<TbCommentVO> commentToCommentVO(List<TbComment> commentList) {
        List<TbCommentVO> commentVOList = new ArrayList<>();
        commentList.stream().forEach(comment -> {
            TbCommentVO tbCommentVO = new TbCommentVO();
            BeanUtils.copyProperties(comment, tbCommentVO);
            TbItem item = itemMapper.selectByPrimaryKey(comment.getItemId());
            tbCommentVO.setTitle(item.getTitle());
            commentVOList.add(tbCommentVO);
        });
        return commentVOList;
    }

    /**
     * 已读消息
     *
     * @param userId
     * @return
     */
    @Override
    public List informationLook(Integer userId) {
        //找出与之相关的物品
        List<Integer> itemIds = getItemsByUserId(userId);
        //找出已读评论
        Example example1 = new Example(TbComment.class);
        example1.createCriteria().andEqualTo("look", 1).andIn("itemId", itemIds).andNotEqualTo("userId", userId);
        example1.orderBy("time").desc();
        List<TbComment> commentList = commentMapper.selectByExample(example1);
        return commentToCommentVO(commentList);
    }

    @Override
    public boolean setLook(Integer commentId) {
        TbComment tbComment;
        try {
            tbComment = this.findByPrimaryKeyService(commentId);
            tbComment.setLook(1);
            this.updateService(tbComment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 设置全部未读评论为已读
     *
     * @param userId
     * @return
     */
    @Override
    public boolean setAllLook(Integer userId) {
        List<TbCommentVO> commentVOList = informationNoLook(userId);
        List<Integer> ids = new ArrayList<>();
        commentVOList.stream().forEach(
                tbCommentVO -> {
                    ids.add(tbCommentVO.getCommentId());
                }
        );
        Example example = new Example(TbComment.class);
        example.createCriteria().andEqualTo("look", 0).andIn("commentId", ids).andNotEqualTo("userId", userId);
        int i = commentMapper.updateByExampleSelective(new TbComment().setLook(1), example);
        if (i != ids.size()) {
            return false;
        }
        return true;
    }
}
