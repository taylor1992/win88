package com.company.project.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.entity.InventoryEntity;
import com.company.project.service.InventoryService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.InventoryDailySnapshotEntity;
import com.company.project.service.InventoryDailySnapshotService;



/**
 * 每日库存快照表（EOD 日结库存，用于对账与审计）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-09 22:02:07
 */
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class InventoryDailySnapshotController {
    private final InventoryDailySnapshotService inventoryDailySnapshotService;

    private final InventoryService inventoryService;

    @ApiOperation(value = "数据快照")
    @PostMapping("inventoryDailySnapshot/sync")
    @SaCheckPermission("inventoryDailySnapshot:sync")
    @ResponseBody
    public DataResult snapshot(){
        List<InventoryEntity> records = inventoryService.list();
        for (InventoryEntity record : records) {
            InventoryDailySnapshotEntity snapshot =  new InventoryDailySnapshotEntity();
            BeanUtils.copyProperties(record,snapshot);
            snapshot.setSnapshotDate(new Date());
            LambdaQueryWrapper<InventoryDailySnapshotEntity> query = Wrappers.<InventoryDailySnapshotEntity>lambdaQuery();
            query.eq(InventoryDailySnapshotEntity::getWarehouseId, snapshot.getWarehouseId());
            query.eq(InventoryDailySnapshotEntity::getSkuId, snapshot.getSkuId());
            query.eq(InventoryDailySnapshotEntity::getSnapshotDate, DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));
            InventoryDailySnapshotEntity one = inventoryDailySnapshotService.getOne(query);
            if(Objects.nonNull(one)){
                BeanUtils.copyProperties(record,one);
                one.setCreatedTime(new Date());
                one.setUpdatedTime(new Date());
                inventoryDailySnapshotService.updateById(one);
            }else {
                snapshot.setCreatedTime(new Date());
                snapshot.setUpdatedTime(new Date());
                inventoryDailySnapshotService.save(snapshot);
            }
        }
        return DataResult.success();
    }

    /**
    * 跳转到页面
    */
    @GetMapping("index/inventoryDailySnapshot")
    public String inventoryDailySnapshot() {
        return "inventory/inventory_daily_snapshot_list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("inventoryDailySnapshot/listByPage")
    @SaCheckPermission("inventoryDailySnapshot:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody InventoryDailySnapshotEntity inventoryDailySnapshot){
        LambdaQueryWrapper<InventoryDailySnapshotEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(inventoryDailySnapshot.getSnapshotDateBegin())){
            queryWrapper.ge(InventoryDailySnapshotEntity::getSnapshotDate,inventoryDailySnapshot.getSnapshotDateBegin());
        }
        if(Objects.nonNull(inventoryDailySnapshot.getSnapshotDateEnd())){
            queryWrapper.le(InventoryDailySnapshotEntity::getSnapshotDate,inventoryDailySnapshot.getSnapshotDateEnd());
        }
        if(Objects.nonNull(inventoryDailySnapshot.getSkuId())){
            queryWrapper.eq(InventoryDailySnapshotEntity::getSkuId,inventoryDailySnapshot.getSkuId());
        }
        queryWrapper.orderByDesc(InventoryDailySnapshotEntity::getId);
        IPage<InventoryDailySnapshotEntity> data = inventoryDailySnapshotService.page(inventoryDailySnapshot.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("inventoryDailySnapshot/add")
    @SaCheckPermission("inventoryDailySnapshot:add")
    @ResponseBody
    public DataResult add(@RequestBody InventoryDailySnapshotEntity inventoryDailySnapshot){
        String username = Win88Util.getUser().getUsername();
        inventoryDailySnapshot.setCreatedBy(username);
            inventoryDailySnapshotService.save(inventoryDailySnapshot);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("inventoryDailySnapshot/delete")
    @SaCheckPermission("inventoryDailySnapshot:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            inventoryDailySnapshotService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("inventoryDailySnapshot/update")
    @SaCheckPermission("inventoryDailySnapshot:update")
    @ResponseBody
    public DataResult update(@RequestBody InventoryDailySnapshotEntity inventoryDailySnapshot){
        SysUser user = Win88Util.getUser();
        inventoryDailySnapshot.setUpdatedBy(user.getUsername());
            inventoryDailySnapshotService.updateById(inventoryDailySnapshot);
        return DataResult.success();
    }



}
