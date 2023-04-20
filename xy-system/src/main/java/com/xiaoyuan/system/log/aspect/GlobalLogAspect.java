package com.xiaoyuan.system.log.aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoyuan.common_util.match.StringMatch;
import com.xiaoyuan.system.log.model.MiscroCloudSystemLog;
import com.xiaoyuan.system.log.service.LogNotice;
import com.xiaoyuan.system.log.utils.IpUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author 袁
 * @company 微科软-MiscroCloud
 * </p>
 * @comment 全局系统日志服务
 * @entity 顶层日志实体
 */
@Slf4j
@Aspect
@Configuration
@ConfigurationProperties(prefix = "miscrocloud.system.global-log")
@ConditionalOnProperty(prefix = "miscrocloud.system.global-log", name = "disable", havingValue = "true")
public class GlobalLogAspect {

    private boolean timeCostDetectDisable = true; // 默认开启接口耗时超时检查
    private long timeCostDetect = 3000; // 默认3s超时
    private boolean serviceResponseDisable = false; // 默认关闭业务错误日志
    private String serviceResponseStatusName = "code"; // 默认返回数据状态码属性名 'code'
    private int serviceResponseStatusValue = 200; // 默认返回数据正确状态码 200
    private boolean isTimeCostNotice = false; // 是否开启超时日志通知
    private boolean isServiceNotice = false; // 是否开启业务错误日志通知
    private boolean isSystemNotice = false; // 是否开启系统异常日志通知

    private MiscroCloudSystemLog miscroCloudSystemLog;
    private long startTime;
    private LogNotice logNotice;

    @Autowired(required = false)
    public void setLogNotice(LogNotice logNotice) {
        this.logNotice = logNotice;
    }

    // 配置切点
    @Pointcut("execution(public * *.*.*.controller.*Controller.*(..))")
    public void LogPoint() {
    }

    @Before(value = "LogPoint()")
    public void RecordSystemLogInBefore(JoinPoint point) {
        // 拿到 request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();

        // 获取User-Agent
        String requestUserAgent = request.getHeader("User-Agent");
        // 获取浏览器用户标识
        UserAgent userAgent = UserAgentUtil.parse(requestUserAgent);

        // 当前线程ID
        String threadId = Long.toString(Thread.currentThread().getId());
        // 当前线程名称
        String threadName = Thread.currentThread().getName();
        // 请求IP地址
        String ip = IpUtil.getIp(request);
        // 请求URL地址
        String url = String.valueOf(request.getRequestURL());
        // 请求方法类型（GET, POST, PUT, DELETE）
        String requestMethodType = request.getMethod();
        // 类方法名称
        String classMethodName = point.getSignature().getDeclaringTypeName();
        // 请求参数
        Object requestParams = getRequestArgsNameAndValue(point);
        // 操作系统
        String os = String.valueOf(userAgent.getOs());
        // 浏览器
        String browser = String.valueOf(userAgent.getBrowser());

        miscroCloudSystemLog = new MiscroCloudSystemLog();
        miscroCloudSystemLog.setThreadId(threadId);
        miscroCloudSystemLog.setThreadName(threadName);
        miscroCloudSystemLog.setIp(ip);
        miscroCloudSystemLog.setUrl(url);
        miscroCloudSystemLog.setRequestMethodType(requestMethodType);
        miscroCloudSystemLog.setClassMethodName(classMethodName);
        miscroCloudSystemLog.setRequestParams(requestParams);
        miscroCloudSystemLog.setOs(os);
        miscroCloudSystemLog.setBrowser(browser);
        miscroCloudSystemLog.setUserAgent(requestUserAgent);

        // 开启耗时检测
        startTime = System.currentTimeMillis();
    }

    @AfterReturning(pointcut = "LogPoint()", returning = "result")
    public void RecordSystemErrorLog(Object result) throws IllegalAccessException {
        // 耗时记录
        long timeCost = System.currentTimeMillis() - startTime;

        // 拿到 response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(requestAttributes).getResponse();

        miscroCloudSystemLog.setTimeCost(timeCost);
        miscroCloudSystemLog.setResult(result);
        miscroCloudSystemLog.setStatusCode(Objects.requireNonNull(response).getStatus()); // 显式抛出空指针异常, 可读性

        boolean isTimeCost = false;
        boolean isServiceError = false;

        if (timeCostDetectDisable && timeCost >= timeCostDetect) {
            // 记录超时日志
            log.warn("===================== 接口超时日志 =========================");
            log.warn("信息: {}", JSONUtil.toJsonStr(miscroCloudSystemLog));
            log.warn("接口耗时时长（ms）：{}ms", timeCost);
            log.warn("===================== 接口超时日志 =========================");
            isTimeCost = true;

            // 是否开启接口超时日志通知
            if (isTimeCostNotice) {
                List<String> emailList = logNotice.timeCostNotice();

                if (emailList.size() > 0) {
                    emailList = emailList.stream().filter(item -> !StringMatch.isEmail(item)).collect(Collectors.toList());

                    // 发送通知
                    // threadService.sendEmailNotice(emailList);
                }
            }
        } else if (result != null && serviceResponseDisable) {
            Field[] serviceResponseFields = result.getClass().getDeclaredFields();
            for (Field serviceResponseField : serviceResponseFields) {
                if (serviceResponseStatusName.equals(serviceResponseField.getName())) {
                    // 暴力反射
                    serviceResponseField.setAccessible(true);
                    // 拿到返回结果的状态码属性值
                    Object serviceResponseResultStatusValue = serviceResponseField.get(result);
                    miscroCloudSystemLog.setServiceStatusCode(Integer.valueOf(String.valueOf(serviceResponseResultStatusValue)));
                    // 业务错误日志采集
                    if (serviceResponseField.getType() == Number.class && (Integer) serviceResponseResultStatusValue != serviceResponseStatusValue) {
                        // 数值型, 直接比较, 判断状态码
                        isServiceError = true;
                        break;
                    } else if (!String.valueOf(serviceResponseStatusValue).equals(String.valueOf(serviceResponseResultStatusValue))) {
                        // 其他类型, 通过转换为字符串比较, 判断状态码
                        isServiceError = true;
                        break;
                    }
                }
            }
            if (isServiceError) {
                log.warn("===================== 业务错误日志 =========================");
                log.warn("信息：{}", JSONUtil.toJsonStr(miscroCloudSystemLog));
                log.warn("===================== 业务错误日志 =========================");

                // 是否开启业务错误日志通知
                if (isServiceNotice) {
                    // 获取邮箱通知列表
                    List<String> emailList = logNotice.serviceError();

                    if (emailList.size() > 0) {
                        // 筛选邮箱
                        emailList = emailList.stream().filter(item -> !StringMatch.isEmail(item)).collect(Collectors.toList());

                        // 发送通知
                    }
                }
            }
        }

        // 记录普通日志
        if (!isTimeCost && !isServiceError) {
            miscroCloudSystemLog.setServiceStatusCode(serviceResponseDisable ? serviceResponseStatusValue : null);
            // 记录普通日志
            log.info("======================= 普通日志 ===========================");
            log.info("信息：{}", JSONUtil.toJsonStr(miscroCloudSystemLog));
            log.info("======================= 普通日志 ===========================");
        }
    }

    @AfterThrowing(pointcut = "LogPoint()", throwing = "e")
    public void RecordSystemExceptionLog(Exception e) {
        // 获取response对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(requestAttributes).getResponse();

        miscroCloudSystemLog.setStatusCode(Objects.requireNonNull(response).getStatus());
        log.error("======================= 异常日志 ===========================");
        log.error("信息：{}", JSONUtil.toJsonStr(miscroCloudSystemLog));
        log.error("异常信息: {}", e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error("发生异常所在类: {}", stackTrace[0].getClassName());
        log.error("发生异常所在文件: {}", stackTrace[0].getFileName());
        log.error("发生异常所在方法: {}", stackTrace[0].getMethodName());
        log.error("发生异常所在代码中的位置（行）: {}", stackTrace[0].getLineNumber());
        log.error("======================= 异常日志 ===========================");

        // 是否开启邮箱通知
        if (isSystemNotice) {
            // 获取邮箱通知列表
            List<String> emailList = logNotice.SystemErrorNotice();
            if (emailList.size() > 0) {
                // 邮箱判断
                emailList = emailList.stream().filter(item -> !StringMatch.isEmail(item)).collect(Collectors.toList());
                // 发送邮箱通知
                // threadService.noticeSystemError(emailList)
            }
        }
    }

    private Map<String, Object> getRequestArgsNameAndValue(JoinPoint joinPoint) {

        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class MiscroCloudSystemErrorLog {

        // 错误信息
        private String errorMessage;
        // 发生异常所在类名（全路径）
        private String errorClassName;
        // 发生异常所在类文件
        private String errorFileName;
        // 发生异常所在方式名
        private String errorMethodName;
        // 发生异常所在代码位置
        private Integer errorLine;
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     *
     * @param userAgent 用户标识信息
     * @return
     */
    public static String getOsAndBrowserInfo(String userAgent) {
        String user = userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        //===============Browser===========================
        if (user.contains("edge")) {
            browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }
        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) ||
                (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
                (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
            browser = "Netscape-?";
        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }
        return os + "::" + browser;
    }

    public void setTimeCostDetectDisable(boolean timeCostDetectDisable) {
        this.timeCostDetectDisable = timeCostDetectDisable;
    }

    public void setTimeCostDetect(long timeCostDetect) {
        this.timeCostDetect = timeCostDetect;
    }

    public void setServiceResponseDisable(boolean serviceResponseDisable) {
        this.serviceResponseDisable = serviceResponseDisable;
    }

    public void setServiceResponseStatusName(String serviceResponseStatusName) {
        this.serviceResponseStatusName = serviceResponseStatusName;
    }

    public void setServiceResponseStatusValue(int serviceResponseStatusValue) {
        this.serviceResponseStatusValue = serviceResponseStatusValue;
    }

    public void setTimeCostNotice(boolean timeCostNotice) {
        isTimeCostNotice = timeCostNotice;
    }

    public void setServiceNotice(boolean serviceNotice) {
        isServiceNotice = serviceNotice;
    }

    public void setSystemNotice(boolean systemNotice) {
        isSystemNotice = systemNotice;
    }
}
