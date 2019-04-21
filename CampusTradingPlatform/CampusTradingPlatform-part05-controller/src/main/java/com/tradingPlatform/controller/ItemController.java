package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.bean.TbUser;
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
@RequestMapping(value = {"/item"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /**
     * 查一个
     *
     * @param itemId
     * @return
     */
    @GetMapping("getItem")
    public TbItem findById(@RequestParam("itemId") Integer itemId) {
        return itemService.findByPrimaryKeyService(itemId);
    }

    /**
     * 查一个
     *
     * @return
     */
    @GetMapping("findAll")
    public ResultInfo findById() {
        ResultInfo resultInfo = new ResultInfo(true, "成功!", null);
        TbUser user = holdUser();
        List<TbItem> itemList = itemService.findAllItems(user.getUserId());
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
            return resultInfo.setMessage("请选择一张图片");
        }
        TbUser tbUser = holdUser();
        item.setTime(new Date());
        item.setUserId(tbUser.getUserId());
        itemService.addService(item);
        if (itemService.findByPrimaryKeyService(item.getItemId()) != null) {
            resultInfo.setMessage("增加成功!").setObject(item).setStatus(true);
        }
        return resultInfo;
    }

    /**
     * 删除
     *
     * @param itemId
     */
    @GetMapping("delete")
    public void delete(@RequestParam Integer itemId) {
        itemService.deleteByPrimaryKeyService(itemId);
    }

    /**
     * 更新
     *
     * @param item
     * @return
     */
    @GetMapping("update")
    public TbItem update(@RequestBody TbItem item) {
        itemService.updateService(item);
        return itemService.findByPrimaryKeyService(item.getItemId());
    }

}
