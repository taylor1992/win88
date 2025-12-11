package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.SalesOrderEntity;
import com.company.project.service.SalesOrderService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 销售订单主表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:48:50
 */
@Controller
@RequestMapping("/")
public class SalesOrderController {
    @Autowired
    private SalesOrderService salesOrderService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/salesOrder")
    public String salesOrder() {
        return "salesorder/list" ;
    }

    @GetMapping("salesOrder/sales-order-detail")
    public String salesOrderDetail(String platformOrderNo, Model model) {
        // 将平台订单号传给前端页面
        model.addAttribute("platformOrderNo", platformOrderNo);
        return "salesorder/sales_order_detail"; // 返回 sales_order_detail.html 模板
    }

    @ApiOperation(value = "根据平台订单号查询订单主表和明细表数据")
    @GetMapping("salesOrder/detailsByOrderNo")
    @SaCheckPermission("salesOrderDetail:view") // 假设的权限
    @ResponseBody
    public DataResult getDetailsByOrderNo(@RequestParam("platformOrderNo") String platformOrderNo) {
        if (platformOrderNo == null || platformOrderNo.isEmpty()) {
            return DataResult.fail("平台订单号不能为空。");
        }

        // 调用 Service 层方法获取聚合数据
        Map<String, Object> data = salesOrderService.getDetailsByOrderNo(platformOrderNo);

        if (data == null || data.isEmpty()) {
            return DataResult.fail("未找到该订单的详细信息。");
        }
        return DataResult.success(data);
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("salesOrder/listByPage")
    @SaCheckPermission("salesOrder:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SalesOrderEntity salesOrder) {
        LambdaQueryWrapper<SalesOrderEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(salesOrder.getOrderStatus())){
            queryWrapper.eq(SalesOrderEntity::getOrderStatus,salesOrder.getOrderStatus());
        }
        if(StringUtils.isNotBlank(salesOrder.getPlatformOrderNo())){
            queryWrapper.eq(SalesOrderEntity::getPlatformOrderNo,salesOrder.getPlatformOrderNo());
        }
        if(StringUtils.isNotBlank(salesOrder.getPlatformCode())){
            queryWrapper.eq(SalesOrderEntity::getPlatformCode,salesOrder.getPlatformCode());
        }
        if(Objects.nonNull(salesOrder.getShopId())){
            queryWrapper.eq(SalesOrderEntity::getShopId,salesOrder.getShopId());
        }
        if(Objects.nonNull(salesOrder.getOrderTimeBegin())){
            queryWrapper.ge(SalesOrderEntity::getOrderTime,salesOrder.getOrderTimeBegin());
        }
        if(Objects.nonNull(salesOrder.getOrderTimeEnd())){
            queryWrapper.le(SalesOrderEntity::getOrderTime,salesOrder.getOrderTimeEnd());
        }
        queryWrapper.orderByDesc(SalesOrderEntity::getOrderTime);
        IPage<SalesOrderEntity> data = salesOrderService.page(salesOrder.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }

    @ApiOperation(value = "导入 TikTok 订单 CSV 文件")
    @PostMapping("salesOrder/importCsv")
    @SaCheckPermission("salesOrder:import")
    @ResponseBody
    public DataResult importOrders(@RequestParam("file") MultipartFile file, @RequestParam("shopId") Long shopId) {
        if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return DataResult.fail("文件为空或格式不正确，请上传 CSV 文件。");
        }
        if (shopId == null) {
            return DataResult.fail("请选择需要导入的店铺。");
        }

        try {
            // 将 shopId 传递给服务层
            int orderCount = salesOrderService.importOrdersFromCsv(file, shopId);

            // 返回 DataResult 格式，将 count 放在 data 中
            Map<String, Integer> dataMap = new HashMap<>();
            dataMap.put("count", orderCount);
            return DataResult.success(dataMap);

        } catch (IOException e) {
            return DataResult.fail("文件读取或解析失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return DataResult.fail("订单导入处理失败: " + e.getMessage());
        }
    }


    @ApiOperation(value = "新增")
    @PostMapping("salesOrder/add")
    @SaCheckPermission("salesOrder:add")
    @ResponseBody
    public DataResult add(@RequestBody SalesOrderEntity salesOrder) {
        String username = Win88Util.getUser().getUsername();
        //salesOrder.setCreatedBy(username);
        salesOrderService.save(salesOrder);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("salesOrder/delete")
    @SaCheckPermission("salesOrder:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        salesOrderService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("salesOrder/update")
    @SaCheckPermission("salesOrder:update")
    @ResponseBody
    public DataResult update(@RequestBody SalesOrderEntity salesOrder) {
        SysUser user = Win88Util.getUser();
        //salesOrder.setUpdatedBy(user.getUsername());
        salesOrderService.updateById(salesOrder);
        return DataResult.success();
    }


}
