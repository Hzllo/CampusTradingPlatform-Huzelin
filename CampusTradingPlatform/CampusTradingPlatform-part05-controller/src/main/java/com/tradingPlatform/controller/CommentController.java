package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.bean.TbUser;
import com.tradingPlatform.service.CommentService;
import com.tradingPlatform.service.ItemService;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = {"/comment"})
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;


    /**
     * 查一个
     *
     * @param commentId
     * @return
     */
    @GetMapping
    public TbComment findById(@RequestParam("commentId") Integer commentId) {
        return commentService.findByPrimaryKeyService(commentId);
    }

    /**
     * 查找未读数量
     *
     * @return
     */
    @GetMapping("count")
    public ResultInfo informationCount() {
        ResultInfo resultInfo = new ResultInfo(true, "查找成功!", null);
        TbUser user = holdUser();
        return resultInfo.setObject(commentService.informationCount(user.getUserId()));
    }

    /**
     * 查找未读消息
     *
     * @return
     */
    @GetMapping("countnolook")
    public ResultInfo informationNoLook() {
        ResultInfo resultInfo = new ResultInfo(true, "查找成功!", null);
        TbUser user = holdUser();
        return resultInfo.setObject(commentService.informationNoLook(user.getUserId()));
    }

    /**
     * 查找已读消息
     *
     * @return
     */
    @GetMapping("countlook")
    public ResultInfo informationLook() {
        ResultInfo resultInfo = new ResultInfo(true, "查找成功!", null);
        TbUser user = holdUser();
        return resultInfo.setObject(commentService.informationLook(user.getUserId()));
    }


    /**
     * 增加一个
     *
     * @param comment
     * @return
     */
    @PostMapping("add")
    public ResultInfo save(@RequestBody TbComment comment) {
        ResultInfo resultInfo = new ResultInfo(true, "评论成功!", null);
        if (StringUtils.isEmpty(comment.getContent())) {
            return resultInfo.setStatus(false).setMessage("内容为空! ");
        }
        TbUser user = holdUser();
        comment.setCommentId(null).setLook(0).setUserId(user.getUserId()).setTime(new Date()).setUsername(user.getUsername());
        TbItem tbItem = itemService.findByPrimaryKeyService(comment.getItemId());
        if (tbItem.getUserId().equals(user.getUserId()) && comment.getType().equals(1)) {
            return resultInfo.setStatus(false).setMessage("自己不能给自己私信! ");
        }
        tbItem.setNum(tbItem.getNum() + 1);
        commentService.addService(comment);
        itemService.updateService(tbItem);
        List<TbComment> commentList = commentService.findListByItemId(comment.getItemId());
        return resultInfo.setObject(commentList);
    }

    /**
     * 获取用户
     */
    public TbUser holdUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TbUser tbUser = userService.findUserByUserName(user.getUsername());
        return tbUser;
    }


    /**
     * 删除
     *
     * @param commentId
     */
    @GetMapping("delete")
    public ResultInfo delete(@RequestParam Integer commentId) {
        ResultInfo resultInfo = new ResultInfo(true, "删除成功!", null);
        try {
            commentService.deleteByPrimaryKeyService(commentId);
        } catch (Exception e) {
            e.printStackTrace();
            return resultInfo.setStatus(false);
        }
        resultInfo = informationLook();
        return resultInfo;
    }

    /**
     * 设为已读
     *
     * @param commentId
     * @return
     */
    @GetMapping("setLook")
    public ResultInfo setLook(@RequestParam Integer commentId) {
        ResultInfo resultInfo = new ResultInfo(false, "执行成功!", null);
        if (commentService.setLook(commentId)) {
            resultInfo = informationNoLook();
            return resultInfo;
        }
        return resultInfo;
    }


    @GetMapping("setAllLook")
    public ResultInfo setAllLook() {
        ResultInfo resultInfo = new ResultInfo(false, "执行成功!", null);
        TbUser user = holdUser();
        if (commentService.setAllLook(user.getUserId())) {
            resultInfo = informationNoLook();
            return resultInfo;
        }
        return resultInfo;
    }

}
