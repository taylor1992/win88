package com.company.project.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.entity.SysDictDetailEntity;
import com.company.project.entity.SysDictEntity;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.SysDictDetailService;
import com.company.project.service.SysDictService;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Win88Util {

    private static Map<String,List<SysDictDetailEntity>> DICT_MAP = Maps.newHashMap();

    private static SysDictService getSysDictService() {
        return SpringUtil.getBean(SysDictService.class);
    }

    private static SysDictDetailService getSysDictDetailService() {
        return SpringUtil.getBean(SysDictDetailService.class);
    }

    public static SysUser getUser() {
        SysUserMapper user = (SysUserMapper) SpringContextUtils.getBean("sysUserMapper");
        assert user != null;
        return user.selectById(StpUtil.getLoginIdAsString());
    }

    public static String getDictLabel(String dictCode, String dictValue) {
        List<SysDictDetailEntity> mapList = DICT_MAP.get(dictCode);
        if(CollectionUtils.isNotEmpty(mapList)){
            for (SysDictDetailEntity d : mapList) {
                if(StringUtils.equals(d.getValue(),dictValue)){
                    return d.getLabel();
                }
            }
        }
        SysDictEntity dictObj = getSysDictService().getOne(Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, dictCode));
        List<SysDictDetailEntity> list = getSysDictDetailService().list(Wrappers.lambdaQuery(SysDictDetailEntity.class).eq(SysDictDetailEntity::getDictId, dictObj.getId()));
        DICT_MAP.put(dictCode,list);
        for (SysDictDetailEntity d : list) {
            if(StringUtils.equals(d.getValue(),dictValue)){
                return d.getLabel();
            }
        }
        return "" ;

    }

    public static String getPlatformCode(Integer type) {
        SysDictEntity dictObj = getSysDictService().getOne(Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, "PlatformCode"));
        List<SysDictDetailEntity> list = getSysDictDetailService().list(Wrappers.lambdaQuery(SysDictDetailEntity.class).eq(SysDictDetailEntity::getDictId, dictObj.getId()));
        for (SysDictDetailEntity d : list) {
            if(StringUtils.equals(d.getValue(),type.toString())){
                return d.getLabel();
            }
        }
        return "" ;
    }
}
