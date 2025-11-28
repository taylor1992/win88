package com.company.project.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(yyyyMMdd)
     */
    public final static String DATEPATTERN = "yyyyMMdd";

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 判断一个日期范围是否包含周六/周日，
     * 如果包含，则返回范围内所有的周末日期。
     *
     * @param start 开始日期（包含）
     * @param end   结束日期（包含）
     * @return 周六和周日的日期（yyyy-MM-dd 字符串），无则返回空列表
     */
    public static List<String> getWeekendsBetween(DateTime start, DateTime end) {
        List<String> result = new ArrayList<>();

        // 防止传参错误
        if (start == null || end == null || start.isAfter(end)) {
            return result;
        }

        // 当前系统日期（取日期零点，避免时分秒影响比较）
        DateTime today = DateUtil.beginOfDay(DateUtil.date());

        // 计算真实的循环结束时间：
        // 如果 end > today，则使用 today
        // 如果 end <= today，则使用 end
        DateTime loopEnd = end.isAfter(today) ? today : end;

        // 如果开始日期比结束日期大（例如传入未来时间），返回空
        if (start.isAfter(loopEnd)) {
            return result;
        }

        // 循环计算
        DateTime current = DateUtil.beginOfDay(start);
        while (current.isBeforeOrEquals(loopEnd)) {
            int dayOfWeek = current.dayOfWeek(); // 1~7（6=周六，7=周日）
            if (dayOfWeek == 1 || dayOfWeek == 7) {
                result.add(current.toDateStr());
            }
            current = DateUtil.offsetDay(current, 1);
        }

        return result;
    }


    public static List<String> getWeekendsByMonthWithNow(Date anyDate) {
        List<String> result = new ArrayList<>();

        // 当前系统日期（只取日期，不带时分秒的比较更干净）
        DateTime now = DateUtil.beginOfDay(DateUtil.date());

        // 传入日期所在月份的 月初 & 月末
        DateTime monthStart = DateUtil.beginOfMonth(anyDate);
        DateTime monthEnd = DateUtil.endOfMonth(anyDate);

        // 计算循环结束的边界：
        // 如果月末 < 今天：用月末
        // 如果月末 >= 今天：用今天
        DateTime loopEnd;
        if (monthEnd.isBefore(now)) {
            loopEnd = monthEnd;
        } else {
            loopEnd = now;
        }

        // 如果这个月份在“未来”，那 monthStart 可能 > loopEnd，直接返回空列表
        if (monthStart.isAfter(loopEnd)) {
            return result;
        }

        // 从月初循环到 loopEnd
        DateTime current = monthStart;
        while (current.isBeforeOrEquals(loopEnd)) {
            int dayOfWeek = current.dayOfWeek(); // 1~7（1=周一，6=周六，7=周日）
            if (dayOfWeek == 6 || dayOfWeek == 7) {
                result.add(current.toDateStr());  // 例如 2025-11-22
            }
            // 加一天
            current = DateUtil.offsetDay(current, 1);
        }

        return result;
    }
}
