package com.company.project.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysUserMapper;

public class Win88Util {

    public static SysUser getUser() {
        SysUserMapper user = (SysUserMapper) SpringContextUtils.getBean("sysUserMapper");
        assert user != null;
        return user.selectById(StpUtil.getLoginIdAsString());
    }

    public static String getPlatformCode(Integer type) {
        switch (type) {
            case 1:
                return "Tiktok" ;
            case 2:
                return "Shopee" ;
            case 3:
                return "Lazada" ;
            case 4:
                return "Facebook" ;
            case 5:
                return "Other" ;
        }
        return "" ;
    }
}
