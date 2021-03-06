package com.tradingPlatform.controller;

import com.tradingPlatform.bean.*;
import com.tradingPlatform.service.CommentService;
import com.tradingPlatform.service.ItemService;
import com.tradingPlatform.service.UserService;
import com.tradingPlatform.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = {"/item"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * 查一个
     *
     * @param itemId
     * @return
     */
    @GetMapping("getItem")
    public ResultInfo findById(@RequestParam("itemId") Integer itemId) {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        TbItemDetailVO tbItemDetailVO = new TbItemDetailVO();
        TbItem item = itemService.findByPrimaryKeyService(itemId);
        BeanUtils.copyProperties(item, tbItemDetailVO);
        TbUser tbUser = userService.findByPrimaryKeyService(item.getUserId());
        tbItemDetailVO.setUser(tbUser.setPassword(null).setPhone(null));
        List<TbComment> listByItemId = commentService.findListByItemId(itemId);
        return resultInfo.setObject(tbItemDetailVO.setComments(listByItemId));
    }

    @GetMapping("myThink")
    public ResultInfo myThink() {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        TbUser user = holdUser();
        List<TbItemThinkVO> list = itemService.myThinkService(user.getUserId());
        return resultInfo.setObject(list);
    }

    /**
     * 查多个
     *
     * @return
     */
    @GetMapping("findAll")
    public ResultInfo findById() {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        TbUser user = holdUser();
        List<TbItem> itemList = itemService.findAllItems(user.getUserId());
        List<TbItemDetailVO> list = new ArrayList<>();
        itemList.stream().forEach(
                item -> {
                    TbItemDetailVO tbItemDetailVO = new TbItemDetailVO();
                    if (item.getMark() == 1) {
                        tbItemDetailVO.setMarkName(userService.findByPrimaryKeyService(item.getMarkId()).getUsername());
                    }
                    BeanUtils.copyProperties(item, tbItemDetailVO);
                    List<TbComment> listByItemId = commentService.findListByItemId(item.getItemId());
                    tbItemDetailVO.setComments(listByItemId);
                    list.add(tbItemDetailVO);
                }
        );
        return resultInfo.setObject(list);
    }

    @GetMapping("search")
    public ResultInfo search(@RequestParam String content) {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        List<TbItem> itemList = itemService.search(content);
        return resultInfo.setObject(itemList);
    }


    /**
     * 查多个
     *
     * @return
     */
    @GetMapping("anyItems")
    public ResultInfo anyItems(@RequestParam Integer type) {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        List<TbItem> itemList = itemService.anyItems(type);
        return resultInfo.setObject(itemList);
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
     * 增加一个
     *
     * @param item
     * @return
     */
    @PostMapping("addItem")
    public ResultInfo save(@RequestBody TbItem item) {
        ResultInfo resultInfo = new ResultInfo(false, "增加失败!", null);
        if (StringUtils.isEmpty(item.getImageUrl())) {
            return resultInfo.setMessage("请选择一张图片！");
        }
        if (StringUtils.isEmpty(item.getContent())) {
            return resultInfo.setMessage("请输入内容！");
        }
        if (StringUtils.isEmpty(item.getTitle())) {
            return resultInfo.setMessage("请输入标题！");
        }
        if (item.getTitle().length() > 12) {
            return resultInfo.setMessage("标题过长！");
        }
        TbUser tbUser = holdUser();
        item.setTime(new Date());
        item.setUserId(tbUser.getUserId());
        itemService.addService(item.setNum(0));
        if (itemService.findByPrimaryKeyService(item.getItemId()) != null) {
            resultInfo.setMessage("增加成功!").setObject(item).setStatus(true);
        }
        return resultInfo;
    }

    /**
     * 更新一个
     *
     * @param item
     * @return
     */
    @PostMapping("updateItem")
    public ResultInfo update(@RequestBody TbItem item) {
        ResultInfo resultInfo = new ResultInfo(false, "更新失败!", null);
        if (StringUtils.isEmpty(item.getImageUrl())) {
            return resultInfo.setMessage("图片不存在！");
        }
        if (StringUtils.isEmpty(item.getContent())) {
            return resultInfo.setMessage("内容不存在！");
        }
        if (StringUtils.isEmpty(item.getTitle())) {
            return resultInfo.setMessage("标题不存在！");
        }
        if (item.getTitle().length() > 12) {
            return resultInfo.setMessage("标题过长！");
        }
        TbUser tbUser = holdUser();
        item.setTime(new Date());
        item.setUserId(tbUser.getUserId());
        itemService.updateService(item);
        if (itemService.findByPrimaryKeyService(item.getItemId()) != null) {
            resultInfo.setMessage("更新成功!").setObject(item).setStatus(true);
        }
        return resultInfo;
    }

    /**
     * 删除
     *
     * @param itemId
     */
    @GetMapping("delete")
    public ResultInfo delete(@RequestParam Integer itemId) {
        ResultInfo resultInfo = new ResultInfo(true, "更新成功!", null);
        TbItem tbItem = itemService.findByPrimaryKeyService(itemId);
        if (tbItem.getMark() == 1) {
            return resultInfo.setStatus(false).setMessage("该商品已被预订! ");
        }
        itemService.deleteByPrimaryKeyService(itemId);
        return resultInfo;
    }

    /**
     * 预订商品
     *
     * @param itemId
     * @return
     */
    @GetMapping("markItem")
    public ResultInfo markItem(@RequestParam Integer itemId) {
        ResultInfo resultInfo = new ResultInfo(true, "预订成功!", null);
        TbItem tbItem = itemService.findByPrimaryKeyService(itemId);
        if (tbItem.getMark() == 1) {
            return resultInfo.setStatus(false).setMessage("该商品已被预订! ");
        }
        Integer userId = holdUser().getUserId();
        if (userId == tbItem.getUserId()) {
            return resultInfo.setStatus(false).setMessage("不能预订自己的商品! ");
        }
        tbItem.setMark(1).setMarkId(userId);
        itemService.updateService(tbItem);
        return resultInfo;
    }

    /**
     * 我的预订
     *
     * @param type
     * @return
     */
    @GetMapping("myMark")
    public ResultInfo myMark() {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        List<TbItem> itemList = itemService.myMark(holdUser().getUserId());
        return resultInfo.setObject(itemList);
    }

    @GetMapping("updateMark")
    public ResultInfo updateMark(@RequestParam Integer itemId) {
        ResultInfo resultInfo = new ResultInfo(true, "已取消预订!", null);
        TbItem tbItem = itemService.findByPrimaryKeyService(itemId);
        tbItem.setMark(0).setMarkId(0);
        itemService.updateService(tbItem);
        return resultInfo;
    }

}
