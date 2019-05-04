package com.fjdakj.esjestclient.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName: CommonUtils
 * @Auther: DZ5006
 * @Date: 19/3/5 9:45
 * @Description: 通用工具类
 */
public final class CommonUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    /**
     * 返回format
     */
    public static DateFormat getDateFormat() {
        return (DateFormat) df.get();
    }


    /**
     * @author DZ5006
     * @date 19/3/5 10:09
     * @desc 接口成功
     */
    public static Map<String, Object> success(Object object) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", object);
        responseMap.put("msg", "success");
        responseMap.put("code", 200);
        return responseMap;
    }

    public static Map<String, Object> success(String message) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("msg", message);
        responseMap.put("code", 200);
        return responseMap;
    }


    /**
     * @author DZ5025
     * @date 2019/3/13 15:42
     * @desc 调用接口成功，只返回信息及状态码
     */
    public static Map<String, Object> operateSuccess() {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("msg", "success");
        responseMap.put("code", 200);
        return responseMap;
    }

    /**
     * @author DZ5025
     * @date 19/3/5 10:16
     * @desc 接口失败
     */
    public static Map<String, Object> operateFail(String exceptionMessage, int code) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("msg", exceptionMessage);
        responseMap.put("code", code);
        return responseMap;
    }

    /**
     * @author DZ5006
     * @date 19/3/5 10:16
     * @desc 接口失败
     */
    public static Map<String, Object> fail(String exceptionMessage, int code) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", null);
        responseMap.put("msg", exceptionMessage);
        responseMap.put("code", code);
        return responseMap;
    }

    /**
     * @author DZ5025
     * @date 19/3/5 10:16
     * @desc 接口失败, 返回对象信息
     */
    public static Map<String, Object> fail(Object object, String exceptionMessage, int code) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", object);
        responseMap.put("msg", exceptionMessage);
        responseMap.put("code", code);
        return responseMap;
    }


    /**
     * @author DZ5025
     * @date 2019/3/11 14:56
     * @desc 方法出错记录日志
     */
    public static void log(Throwable e, Class c, String message) {
        Logger logger = LoggerFactory.getLogger(c);
        StackTraceElement s = e.getStackTrace()[0];
        String msg = new StringBuilder().append(message).append("报错的文件是：").append(s.getFileName())
                .append("报错方法是：").append(s.getMethodName()).append(",报错的行是：").append(s.getLineNumber())
                .append(",报错的信息是：").append(e).toString();
        logger.warn(msg);
    }

    /**
     * @author DZ5025
     * @date 2019/3/11 14:57
     * @desc 自定义记录日志
     */
    public static void myLog(Class c, String message) {
        Logger logger = LoggerFactory.getLogger(c);
        logger.info(message);
    }

    /**
     * @author DZ5025
     * @date 2019/3/11 15:08
     * @desc 生成主键
     */
    public static String getKey() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    /**
     * 获取今天的最后时刻(午夜)
     * yyyy-MM-dd
     */
    public static String getTodayFinalStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX).format(formatter);
    }

    /**
     * @author DZ5006
     * @date 19/3/21 9:52
     * @desc 获取今天日期date格式
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getNowT() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return now.format(dateTimeFormatter);
    }

    /**
     * @author DZ5025
     * @date 2019/4/15 20:38
     * @desc 解析"yyyy-MM-dd HH:mm:ss"格式日期，并返回带T格式字符串时间
     */
    public static String formatDateNowT(String dateString) throws ParseException {
        SimpleDateFormat parss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = parss.parse(dateString);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return format.format(date);
    }


    /**
     * @author DZ5006
     * @date 19/4/3 18:51
     * @desc long--> string date
     * yyyy-MM-dd HH:mm:ss
     */
    public static String long2StringDate(Long l) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(l));
    }

    /**
     * @author DZ5006
     * @date 19/3/21 9:52
     * @desc 获取今天日期string格式
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getNowString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);

    }


    /**
     * 序列号json,并去除空value值
     *
     * @param o
     * @return
     */
    public static JSONObject removeNullValue(Object o) {
        JSONObject jsobj = (JSONObject) JSONObject.toJSON(o);
        Set<Map.Entry<String, Object>> entries = jsobj.entrySet();
        entries.removeIf(next -> StringUtils.isEmpty(next.getValue()));
        return jsobj;
    }


}
