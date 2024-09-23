package com.library.bookwave.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PrinterIpInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
            // Content-Type이 multipart/form-data인지 확인
            if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                String remoteAddr = request.getRemoteAddr();
                // 허용할 IP 리스트
                List<String> allowedIps = Arrays.asList("192.168.0.146", "127.0.0.1", "::1", "0:0:0:0:0:0:0:1");

                if (allowedIps.contains(remoteAddr)) {
                    return true;  // 요청을 계속 진행
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 접근 불가
                    return false;  // 요청 중단
                }
            }
        }
        // 그 외 요청은 필터링하지 않음
        return true;
    }
	
}
