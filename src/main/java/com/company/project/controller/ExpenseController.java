package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.apache.commons.lang.StringUtils;
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
import com.company.project.entity.ExpenseEntity;
import com.company.project.service.ExpenseService;


/**
 * 支出明细表
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-10 22:20:23
 */
@Controller
@RequestMapping("/")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/expense")
    public String expense() {
        return "expense/expense" ;
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("expense/listByPage")
    @SaCheckPermission("expense:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ExpenseEntity expense) {
        LambdaQueryWrapper<ExpenseEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(expense.getCategory())){
            queryWrapper.eq(ExpenseEntity::getCategory,expense.getCategory());
        }
        if(Objects.nonNull(expense.getStatus())){
            queryWrapper.eq(ExpenseEntity::getStatus,expense.getStatus());
        }
        if(Objects.nonNull(expense.getExpenseDateBegin())){
            queryWrapper.ge(ExpenseEntity::getExpenseDate,expense.getExpenseDateBegin());
        }
        if(Objects.nonNull(expense.getExpenseDateEnd())){
            queryWrapper.le(ExpenseEntity::getExpenseDate,expense.getExpenseDateEnd());
        }
        queryWrapper.orderByDesc(ExpenseEntity::getId);
        IPage<ExpenseEntity> data = expenseService.page(expense.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("expense/add")
    @SaCheckPermission("expense:add")
    @ResponseBody
    public DataResult add(@RequestBody ExpenseEntity expense) {
        String username = Win88Util.getUser().getUsername();
        expense.setCreatedBy(username);
        expense.setUpdatedBy(username);
        expenseService.save(expense);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("expense/delete")
    @SaCheckPermission("expense:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        expenseService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("expense/update")
    @SaCheckPermission("expense:update")
    @ResponseBody
    public DataResult update(@RequestBody ExpenseEntity expense) {
        SysUser user = Win88Util.getUser();
        expense.setUpdatedBy(user.getUsername());
        expenseService.updateById(expense);
        return DataResult.success();
    }


}
