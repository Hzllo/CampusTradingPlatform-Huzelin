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
     * 增加一个
     *
     * @param comment
     * @return
     */
    @PostMapping("add")
    public ResultInfo save(@RequestBody TbComment comment) {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        TbUser user = holdUser();
        if (user == null) {
            return resultInfo.setStatus(false).setMessage("请登录! ");
        }
        comment.setCommentId(null).setLook(false).setUserId(user.getUserId()).setTime(new Date()).setUsername(user.getUsername());
        TbItem tbItem = itemService.findByPrimaryKeyService(comment.getItemId());
        if (tbItem.getUserId().equals(user.getUserId()) && comment.getType().equals(1)) {
            return resultInfo.setStatus(true).setMessage("自己不能给自己私信! ");
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
    private TbUser holdUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TbUser tbUser = userService.findUserByUserName(user.getUsername());
        return tbUser;
    }


    /**
     * 删除
     *
     * @param commentId
     */
    @DeleteMapping
    public void delete(@RequestParam Integer commentId) {
        commentService.deleteByPrimaryKeyService(commentId);
    }

}
