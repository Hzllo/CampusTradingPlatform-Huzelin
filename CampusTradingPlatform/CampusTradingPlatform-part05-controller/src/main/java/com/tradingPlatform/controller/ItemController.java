package com.tradingPlatform.controller;

import com.tradingPlatform.bean.TbItem;
import com.tradingPlatform.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping(value = {"/item"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 查一个
     *
     * @param itemId
     * @return
     */
    @GetMapping
    public TbItem findById(@RequestParam("itemId") Integer itemId) {
        return itemService.findByPrimaryKeyService(itemId);
    }

    /**
     * 增加一个
     *
     * @param item
     * @return
     */
    @PostMapping
    public TbItem save(@RequestBody TbItem item) {

        itemService.addService(item);

        return itemService.findByPrimaryKeyService(item.getItemId());
    }

    /**
     * 删除
     *
     * @param itemId
     */
    @DeleteMapping
    public void delete(@RequestParam Integer itemId) {
        itemService.deleteByPrimaryKeyService(itemId);
    }

    /**
     * 更新
     *
     * @param item
     * @return
     */
    @PutMapping
    public TbItem update(@RequestBody TbItem item) {
        itemService.updateService(item);
        return itemService.findByPrimaryKeyService(item.getItemId());
    }
    
}
