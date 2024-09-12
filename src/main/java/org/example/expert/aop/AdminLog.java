package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.config.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
public class AdminLog {

    /* pointcut: annotation base range */
    @Pointcut("@annotation(org.example.expert.annotation.LogUse)")
    private void logUseAnnotation() {}

    /* 어드바이스: 패키지 범위 기반 */
    @Around("logUseAnnotation()")
    public Object advicePackage(ProceedingJoinPoint joinPoint) throws Throwable {


        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Capture the request attributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // Extract the Authorization header and decode the bearer token
        String bearerToken = request.getHeader("Authorization");
        String token = bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : null;

        String requestorId = JwtUtil.extractUserIdFromToken(token);
        String userType = JwtUtil.extractUserTypeFromToken(token);

        String apiUrl = request.getRequestURL().toString();
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("Requestor ID: {} ({}), Request Time: {}, API URL: {}", requestorId, userType, requestTime, apiUrl);

        try {
            return joinPoint.proceed();
        } finally {
            log.info("Request completed for {} completed", apiUrl);
        }
    }
}
