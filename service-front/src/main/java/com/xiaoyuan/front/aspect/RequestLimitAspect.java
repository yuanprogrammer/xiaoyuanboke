package com.xiaoyuan.front.aspect;

import com.xiaoyuan.front.annotation.AddressRequestLimit;
import com.xiaoyuan.common.vo.R;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * FileName:    RequestLimitAspect
 * Author:      小袁
 * Date:        2022/4/29 22:52
 * Description: 接口请求限制切面
 */
@Aspect
@Component
public class RequestLimitAspect {

    private static ConcurrentHashMap<String, ExpiringMap<String, Integer>> book = new ConcurrentHashMap<>();

    // 定义切点
    // 让所有有@LimitRequest注解的方法都执行切面方法
    @Pointcut("@annotation(addressRequestLimit)")
    public void excludeService(AddressRequestLimit addressRequestLimit) {
    }

    @Around("excludeService(addressRequestLimit)")
    public Object doAround(ProceedingJoinPoint pjp, AddressRequestLimit addressRequestLimit) throws Throwable {

        // 获得request对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        // 获取Map对象， 如果没有则返回默认值
        // 第一个参数是key， 第二个参数是默认值
        ExpiringMap<String, Integer> uc = book.getOrDefault(request.getRequestURI(), ExpiringMap.builder().variableExpiration().build());
        Integer uCount = uc.getOrDefault(request.getRemoteAddr(), 0);


        if (uCount >= addressRequestLimit.maxCount()) { // 超过次数，不执行目标方法
            return R.fail().message("点赞过于频繁, 请过会再试");
        } else if (uCount == 0) { // 第一次请求时，设置有效时间
//            /** Expires entries based on when they were last accessed */
//            ACCESSED,
//            /** Expires entries based on when they were created */
//            CREATED;
            uc.put(request.getRemoteAddr(), uCount + 1, ExpirationPolicy.CREATED, addressRequestLimit.seconds(), TimeUnit.MILLISECONDS);
        } else { // 未超过次数， 记录加一
            uc.put(request.getRemoteAddr(), uCount + 1);
        }
        book.put(request.getRequestURI(), uc);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        return result;
    }
}
