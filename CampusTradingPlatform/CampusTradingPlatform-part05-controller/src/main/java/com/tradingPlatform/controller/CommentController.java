package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbComment;
import com.tradingPlatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/comment"})
public class CommentController {

    @Autowired
    private CommentService commentService;

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
    @PostMapping
    public TbComment save(@RequestBody TbComment comment) {
        comment.setCommentId(null).setLook(false);
        commentService.addService(comment);

        return commentService.findByPrimaryKeyService(comment.getCommentId());
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
