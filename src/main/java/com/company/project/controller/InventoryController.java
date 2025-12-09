package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.common.enums.DictionaryEnums;
import com.company.project.service.InventoryLogService;
import com.company.project.vo.req.InventoryAdjustDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Objects;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.InventoryEntity;
import com.company.project.service.InventoryService;



/**
 * 当前库存表（仓库 + 内部 SKU）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-03 23:23:49
 */
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    private final InventoryLogService inventoryLogService;


    /**
    * 跳转到页面
    */
    @GetMapping("index/inventory")
    public String inventory() {
        return "inventory/list";
    }

    @GetMapping("index/inventory-log")
    public String inventoryLog(Model model, Long skuId,Long warehouseId) {
        model.addAttribute("skuId",skuId);
        model.addAttribute("warehouseId",warehouseId);
        return "inventory/inventoryLog_list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("inventory/listByPage")
    @SaCheckPermission("inventory:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody InventoryEntity inventory){
        LambdaQueryWrapper<InventoryEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(inventory.getSkuId())){
            queryWrapper.eq(InventoryEntity::getSkuId, inventory.getSkuId());
        }
        if(Objects.nonNull(inventory.getWarehouseId())){
            queryWrapper.eq(InventoryEntity::getWarehouseId, inventory.getWarehouseId());
        }
        queryWrapper.orderByDesc(InventoryEntity::getId);
        IPage<InventoryEntity> data = inventoryService.page(inventory.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "调整库存")
    @PostMapping("inventory/adjust")
    @SaCheckPermission("inventory:adjust")
    @ResponseBody
    public DataResult adjust(@RequestBody InventoryAdjustDTO dto){
        dto.setUpdatedBy(Win88Util.getUser().getUsername());
        dto.setChangeType(DictionaryEnums.InventoryChangeType.MANUAL_ADJUST.getCode());
        inventoryService.adjust(dto);
        return DataResult.success();
    }

    @ApiOperation(value = "新增")
    @PostMapping("inventory/add")
    @SaCheckPermission("inventory:add")
    @ResponseBody
    public DataResult add(@RequestBody InventoryEntity inventory){
        String username = Win88Util.getUser().getUsername();
        inventory.setCreatedBy(username);
            inventoryService.save(inventory);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("inventory/delete")
    @SaCheckPermission("inventory:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            inventoryService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("inventory/update")
    @SaCheckPermission("inventory:update")
    @ResponseBody
    public DataResult update(@RequestBody InventoryEntity inventory){
        SysUser user = Win88Util.getUser();
        inventory.setUpdatedBy(user.getUsername());
            inventoryService.updateById(inventory);
        return DataResult.success();
    }



}
