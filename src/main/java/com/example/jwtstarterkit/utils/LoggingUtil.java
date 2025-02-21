package com.example.jwtstarterkit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoggingUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    public static void logRequest(String action, String userId, Object body, boolean isError, String errorMessage) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ipAddress = "N/A";
        String path = "N/A";

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            ipAddress = request.getRemoteAddr();
            path = request.getRequestURI();
        }

        String bodyStr = (body != null) ? body.toString() : "N/A";

        if (isError) {
            logger.error("Action: {}, UserId: {}, IP: {}, Path: {}, Body: {}, Error: {}",
                    action, userId, ipAddress, path, bodyStr, errorMessage);
        } else {
            logger.info("Action: {}, UserId: {}, IP: {}, Path: {}, Body: {}",
                    action, userId, ipAddress, path, bodyStr);
        }
    }
}
