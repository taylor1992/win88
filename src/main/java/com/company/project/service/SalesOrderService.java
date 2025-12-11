package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SalesOrderEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 销售订单主表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:48:50
 */
public interface SalesOrderService extends IService<SalesOrderEntity> {

    int importOrdersFromCsv(MultipartFile file, Long shopId) throws Exception;

    Map<String, Object> getDetailsByOrderNo(String platformOrderNo);

}

