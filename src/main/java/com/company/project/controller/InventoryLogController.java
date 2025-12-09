package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.company.project.entity.InventoryLogEntity;
import com.company.project.service.InventoryLogService;



/**
 * 库存变更流水表（支持手工汇总调整，也预留店铺/订单维度）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-04 22:18:16
 */
@Controller
@RequestMapping("/")
public class InventoryLogController {
    @Autowired
    private InventoryLogService inventoryLogService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/inventoryLog")
    public String inventoryLog() {
        return "inventorylog/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("inventoryLog/listByPage")
    @SaCheckPermission("inventoryLog:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody InventoryLogEntity inventoryLog){
        LambdaQueryWrapper<InventoryLogEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(inventoryLog.getSkuId())){
            queryWrapper.eq(InventoryLogEntity::getSkuId, inventoryLog.getSkuId());
        }
        if(Objects.nonNull(inventoryLog.getWarehouseId())){
            queryWrapper.eq(InventoryLogEntity::getWarehouseId, inventoryLog.getWarehouseId());
        }
        if(Objects.nonNull(inventoryLog.getChangeType())){
            queryWrapper.eq(InventoryLogEntity::getChangeType, inventoryLog.getChangeType());
        }
        if(Objects.nonNull(inventoryLog.getCreatedTimeBegin())){
            queryWrapper.ge(InventoryLogEntity::getCreatedTime, inventoryLog.getCreatedTimeBegin());
        }
        if(Objects.nonNull(inventoryLog.getCreatedTimeEnd())){
            queryWrapper.le(InventoryLogEntity::getCreatedTime, inventoryLog.getCreatedTimeEnd());
        }
        queryWrapper.orderByDesc(InventoryLogEntity::getId);
        IPage<InventoryLogEntity> data = inventoryLogService.page(inventoryLog.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("inventoryLog/add")
    @SaCheckPermission("inventoryLog:add")
    @ResponseBody
    public DataResult add(@RequestBody InventoryLogEntity inventoryLog){
        String username = Win88Util.getUser().getUsername();
        inventoryLog.setCreatedBy(username);
            inventoryLogService.save(inventoryLog);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("inventoryLog/delete")
    @SaCheckPermission("inventoryLog:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            inventoryLogService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("inventoryLog/update")
    @SaCheckPermission("inventoryLog:update")
    @ResponseBody
    public DataResult update(@RequestBody InventoryLogEntity inventoryLog){
        SysUser user = Win88Util.getUser();
        inventoryLog.setUpdatedBy(user.getUsername());
            inventoryLogService.updateById(inventoryLog);
        return DataResult.success();
    }



}
